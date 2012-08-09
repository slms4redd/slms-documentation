.. module:: geoserver.mosaic_pyramid

.. _geoserver.mosaic_pyramid:

Managing Mosaics and Pyramids
-----------------------------

In this section will learn how to manage Image Mosaics and Image Pyramids in GeoServer.

Configuring an Image Mosaic
^^^^^^^^^^^^^^^^^^^^^^^^^^^

An Image Mosaic is composed of a set of datasets which are exposed as a single coverage. The ImageMosaic format allows to automatically build and setup a mosaic from a set of georeferenced datasets.
This section provides better instructions to configure an Image Mosaic

.. note:: Before you start, ensure that the :ref:`Maps - Raster <geoserver.mosaic>` section has been completed.

We will configure an ImageMosaic using the optimized dataset. As explained in the :ref:`Maps - Raster <geoserver.mosaic>` section, follow the steps 1 to 6. Then:

#. Specify a proper name (as an instance, :file:`boulder_bg_optimized`) in the :guilabel:`Data Source Name` field of the interface. 
#. Specify :file:`file:/home/unredd/Desktop/workshop/data/user_data/optimized` as URL of the sample data in the :guilabel:`Connections Parameter's - URL` field. 

   .. figure:: img/mosaic_addraster.jpg

#. Click :guilabel:`Save`. 

#. Publish the layer by clicking on the :guilabel:`publish` link. 

   .. figure:: img/mosaic_publish.jpg
   
#. Set :file:`boulder_bg_optimized` as name and title of the layer. 

   .. figure:: img/mosaic_setname.jpg

#. Check the Coordinate Reference Systems and the Bounding Boxes fields are properly set.

#. Customize the ImageMosaic properties if needed. For the sample mosaic, set the OutputTransparentColor to the value **000000** (Which is the Black color). click on :guilabel:`Save` when done. 

   .. figure:: img/mosaic_parameters.png

* *AllowMultithreading*: If true, enable tiles multithreading loading. This allows to perform parallelized loading of the granules that compose the mosaic.
* *BackgroundValues*: Set the value of the mosaic background. Depending on the nature of the mosaic it is wise to set a value for the no data area (usually -9999). This value is repeated on all the mosaic bands.
* *InputTransparentColor*: Set the transparent color for the granules prior to mosaicing them in order to control the superimposition process between them. When GeoServer composes the granules to satisfy the user request, some of them can overlap some others, therefore, setting this parameter with the opportune color avoids the overlap of no data areas between granules. 
* *MaxAllowedTiles*: Set the maximum number of the tiles that can be load simulatenously for a request. In case of a large mosaic this parameter should be opportunely set to not saturating the server with too many granules loaded at the same time.
* *OutputTransparentColor*: Set the transparent color for the created mosaic.
* *SUGGESTED_TILE_SIZE*: Controls the tile size of the input granules as well as the tile size of the output mosaic. It consists of two positive integers separated by a comma, like 512,512.
* *USE_JAI_IMAGEREAD*: If true, GeoServer will make use of JAI ImageRead operation and its deferred loading mechanism to load granules; if false, GeoServer will perform direct ImageIO read calls which will result in immediate loading.

At this point the ImageMosaic is being published with GeoServer. Next step is checking how the performances in accessing the datasets have been improved.

#. Click the :guilabel:`Layer Preview` link in the left GeoServer menu. 

#. Look for a *geosolutions:boulder_bg* layer (the dataset without optimization) and click the :guilabel:`OpenLayers` link beside of it. 

   .. figure:: img/mosaic_pratopreview.jpg

#. Play with the map preview by zooming and panning. When zooming, the response time isn't immediate due to the access to the underlying big datasets which haven't been optimized.

#. Return to the :guilabel:`Layer Preview` page. 

#. Look for a *geosolutions:boulder_bg_optimized* layer (the optimized dataset with tiling and overviews set) and click the :guilabel:`OpenLayers` link beside of it. 

   .. figure:: img/mosaic_retiledpreview.jpg

#. Play with the map preview by zooming and panning. Check how the performances have been improved (leveraging on both overviews and tiling). Also note the image quality of the lowest resolution views, having used an average interpolation algorithm when creating the overviews.


Configuring an Image Pyramid
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

GeoServer can efficiently deal with large TIFF with overviews, as long as the TIFF is below the 2GB size limit. Once the image size goes beyond such limit it's time to start considering an image pyramid instead. An image pyramid builds multiple mosaics of images, each one at a different zoom level, making it so that each tile is stored in a separate file. This comes with a composition overhead to bring back the tiles into a single image, but can speed up image handling as each overview is tiled, and thus a sub-set of it can be accessed efficiently (as opposed to a single GeoTIFF, where the base level can be tiled, but the overviews never are).

.. note::

   In order to build the pyramid we'll use the `gdal_retile.py <http://www.gdal.org/gdal_retile.html>`_ utility, part of the GDAL command line utilities and available for various operating systems.

#. Navigate to the workshop directory and copy the `bmpyramid` directory into the *$GEOSERVER_DATA_DIR/data* directory

#. From the command line run::

      cd $GEOSERVER_DATA_DIR/data

      gdal_retile.py -v -r bilinear -levels 4 -ps 2048 2048 -co "TILED=YES" -co "COMPRESS=JPEG" -targetDir bmpyramid bmreduced.tiff


   The `gdal_retile.py  <http://www.gdal.org/gdal_retile.html>`_ user guide provides a detailed explanation for all the possible parameters, here is a description of the ones used in the command line above:
   
     * `-v`: verbose output, allows the user to see each file creation scroll by, thus knowing progress is being made (a big pyramid construction can take hours)
     * `-r bilinear`: use bilinear interpolation when building the lower resolution levels. This is key to get good image quality without asking GeoServer to perform expensive interpolations in memory
     * `-levels 4`: the number of levels in the pyramid
     * `-ps 2048 2048`: each tile in the pyramid will be a 2048x2048 GeoTIFF
     * `-co "TILED=YES"`: each GeoTIFF tile in the pyramid will be inner tiled
     * `-co "COMPRESS=JPEG"`: each GeoTIFF tile in the pyramid will be JPEG compressed (trades small size for higher performance, try out it without this parameter too)
     * `-targetDir bmpyramid`: build the pyramid in the bmpyramid directory. The target directory must exist and be empty
     * `bmreduced.tiff`: the source file
  
   This will produce a number of TIFF files in bmpyramid along with the sub-directories `1`, `2,` `3`, and `4`.

#. Go to the **Stores** section an add a new ``Raster Data Source`` clicking ho **ImagePyramid**:

   .. figure:: 
      img/pyramid1.png

      *Adding a ImagePyramid Data Source*

   .. warning::
    
      This assumes the GeoServer image pyramid plug-in is already installed. The pyramid is normally an extension.

#. Specify a proper name (``bm_pyramid``) in the Data Source Name field of the interface and specify a proper URL with the pyramid data directory: 

   .. figure:: 
      img/pyramid2.png

      *Configuring a image pyramid store*

#. Click the **Save** button.

   .. note:: 
    
      When clicking save the store will look into the directory, recognize a ``gdal_retile`` generated structure and perform some background operations::
      	
      		- move all tiff files in the root to a newly create directory 0
                - create an image mosaic in all sub-directories (shapefile index plus property file)
                - create the root property file describing the whole pyramid structure

#. Publish the new pyramid created:

   .. figure:: 
      img/pyramid3.png

      *Choosing the coverage for publishing*

#. Setup the layer parameter USE_JAI_IMAGEREAD to false  to get better scalability:

   .. figure:: 
      img/pyramid4.png

      *Tuning the pyramid parameters*

#. Click **Submit** button and go to the GeoServer **Map Preview** to see the pyramid:

   .. figure:: 
      img/pyramid5.png

      *Previewing the pyramid*
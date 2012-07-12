===============
Sample datasets
===============

Following is the description of the sample data provided for the training exercices.

This dataset is a simplified version from a real use case, the `DRC portal <http://www.rdc-snsf.org>`_, and is a minimal set which will cover all the cases that the system administrator may face when configuring the portal:

 * Publication of base layers on GeoServer for visualization,
 * Configuration of GeoStore for time varying layers,
 * Calculation of statistics based on regions (polygons).

This dataset can be found in :file:`/var/geobatch/test_data_training.tgz`.


DRC boundaries
-----------------------

Shapefile: :file:`country_boundaries/DRCboundaryFromAU.*`

DRC boundaries are used for visualization. This vector layer will be published directly on GeoServer, and the portal will be configured to use it as a base layer, or as an overlay.


DRC provinces
-----------------------

Shapefile: :file:`rdc_limite_administrative/RDC_provinces_fixed_4326.*`
Rasterized: :file:`rdc_limite_administrative/rdc_admin1.tif`

DRC provinces layer will be used to calculate statistics and charts on province areas based on Forest classification and Forest change data.


Forest classification
-----------------------

Raster: :file:`forest_classification_tiled.tif`

UCL Forest Classification, year 2010. It has 20 classes and it will be used to generate charts comparing the areas of different forest types by province.


Forest mask
-----------------------

Raster series: :file:`forest_mask/forest_mask_????_tiled.tif`

Forest masks, years 2000, 2005 and 2010. This data will be used to generate time series charts showing deforestation.

In an advanced scenario, it could be combined with UCL classification to plot changes for different forest types.


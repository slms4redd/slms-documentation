===============
Sample datasets
===============

Following is the description of the sample data provided for the training exercices.

This dataset is a simplified version from a real use case, the `DRC portal <http://www.rdc-snsf.org>`_, and is a minimal set which will cover all the cases that the system administrator may face when configuring the portal:

 * Publication of base layers on GeoServer for visualization,
 * Configuration of GeoStore for time varying layers,
 * Calculation of statistics based on regions (polygons).


DRC boundaries
-----------------------

DRC boundaries are used for visualization. This vector layer will be published directly on GeoServer, and the portal will be configured to use it as a base layer, or as an overlay.

Shapefile can be found in: :file:`/var/geoserver/shared/shapefiles`


DRC provinces
-----------------------

DRC provinces layer will be used to calculate statistics and charts on province areas based on Forest classification and Forest change data.

A rasterized version for statistics calculation can be found here: :file:`/var/geoserver/stg_geoserver/extdata/stats`


Forest classification
-----------------------

UCL Forest Classification, year 2010. It has 20 classes and it will be used to generate charts comparing the areas of different forest types by province.

File can be found here: :file:`/var/geoserver/shared/geotiff`


Forest mask
-----------------------

Forest masks, years 2000, 2005 and 2010. This data will be used to generate time series charts showing deforestation.

In an advanced scenario, it could be combined with UCL classification to plot changes for different forest types.

Files can be found here: :file:`/var/geoserver/stg_geoserver/extdata/forest_mask_mosaic`


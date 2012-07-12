=========================
UNREDD training test data
=========================

DRC data will be used for the exercises during the training as they provide the minimum set needed to cover all the cases that the system administrator may face when implementing the portal (publication of base layers on GeoServer, configuration of GeoStore for time dependent layers, calculation statistics based on polygons on a shapefile).

DRC boundaries
-----------------------

DRC boundaries can be used to show how to publish a layer directly on GeoServer and how to configure the portal to use it as a base layer or as an overlay.


DRC provinces
-----------------------

DRC provinces layer will be used to calculate statistics and charts on province areas based on Forest classification and Forest change data.


Landsat *(optional)*
-----------------------

Landsat data is only used as a base layer. It’s time dependent in case of DRC (2000 and 2005) so, if needed, it can also be used to show how to publish a mosaic on GeoServer and how to manually add a time dependent layer on the portal configuration file. Otherwise the blue marble published on the server can be used as a base layer.


Forest classification
-----------------------

UCL Forest Classification 2010. It has 20 classes and it can be used to build a chart with area by forest type by province.


Forest mask
-----------------------

Forest mask, years (2000, 2005 and 2010) binary data (+ no data) is derived from the FACET dataset. The following charts can be produced from this data:
 * Forest change by area
 * Forest change by area by UCL forest type. In this case data, already pivoted by the statistics batch procedure, needs to be pivoted once again by the Groovy script, so maybe too complex for an exercise.
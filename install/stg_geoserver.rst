.. module:: unredd.install.stg_geoserver

Deploy and configure staging GeoServer
======================================

:file:`/var/tomcat/stg_geoserver`

::

  GEOSERVER_DATA_DIR=/var/geoserver/staging

::

  GEOSERVER_LOG_LOCATION=/var/geoserver/stg_geoserver/logs

::

  JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m
  -XX:PermSize=64m -XX:+UseConcMarkSweepGC -XX:NewSize=48m
  -Dorg.geotools.shapefile.datetime=true
  -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR
  -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION"

**TODO: Geoserver dirs have to be restruct’ed:**
All the data file used by a geoserver instance should be placed inside a single
root dir:

Root dir: :file:`/var/tomcat/stg_geoserver`

SubDirs:

* **data**: geoserver data dir: configuration files go here
* **logs**: geoserver log location, outside of the datadir, since it needs no backup
* **extdata**: raster and shp files go here.

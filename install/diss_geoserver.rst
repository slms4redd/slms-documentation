.. module:: unredd.install.diss_geoserver

Deploy and configure dissemination GeoServer
============================================

:file:`/var/tomcat/diss_geoserver`

::

  GEOSERVER_DATA_DIR=/var/geoserver/diss
  
::

  GEOSERVER_LOG_LOCATION=/var/geoserver/diss_geoserver/logs

::

  JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m -XX:PermSize=64m
  -XX:+UseConcMarkSweepGC -XX:NewSize=48m 
  -Dorg.geotools.shapefile.datetime=true
  -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR
  -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION"

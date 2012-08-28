.. module:: unredd.install.stg_geoserver

Deploy and configure staging GeoServer
======================================

GeoServer directory structure
-----------------------------

We are going to put geoserver related data inside the directory ``/var/geoserver``.

Each geoserver instance will have its own subdir within that one: e.g.

:``/var/geoserver/stg_geoserver``: for the staging geoserver instance
:``/var/geoserver/diss_geoserver``: for the dissemination geoserver instance

*Please note that if you are installing staging and dissemination area on different servers, 
the above two directories will be installed each inside its own server.*

When dealing with static data used on more than one GeoServer instance, we may have as well the directory  ``/var/geoserver/shared``.


The directory structure inside ``/var/geoserver/<geoserver_instance_name>`` is as follows:

:**data**: geoserver data dir configuration files
:**logs**: geoserver log location, outside of the datadir, since it needs no backup
:**extdata**: the real data, i.e. raster and shp files.


Configuration files
-------------------

This is the content of the ``bin/setenv.sh`` file for the staging GeoServer:

:file:`/var/tomcat/stg_geoserver/bin/setenv.sh`

::

  INSTANCE_NAME=stg_geoserver
  GEOSERVER_DATA_DIR=/var/geoserver/$INSTANCE_NAME
  GEOSERVER_LOG_LOCATION=$GEOSERVER_DATA_DIR/logs
  JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m
     -XX:PermSize=64m -XX:+UseConcMarkSweepGC -XX:NewSize=48m
     -Dorg.geotools.shapefile.datetime=true
     -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR
     -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION"


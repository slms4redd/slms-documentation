.. _unredd-install-stg_geoserver:

Deploy and configure staging and dissemination GeoServer
========================================================

Simply copy the application file ``geoserver.war`` to the tomcat webapps directory. For example::

  sudo cp geoserver.war /var/tomcat/stg_geoserver/webapps/stg_geoserver.war

Or::

  sudo cp geoserver.war /var/tomcat/diss_geoserver/webapps/diss_geoserver.war

This will install and run geoserver instances, accessible in:

  http://localhost/stg_geoserver/

Or:

  http://localhost/diss_geoserver/


GeoServer data directory
------------------------

We are going to put geoserver related data inside the directory ``/var/geoserver``.

Each geoserver instance will have its own subdir within that one:

* ``/var/stg_geoserver``: for the staging geoserver instance
* ``/var/diss_geoserver``: for the dissemination geoserver instance

*Please note that if you are installing staging and dissemination area on different servers, 
the above two directories will be installed each inside its own server.*

The directory structure inside ``/var/geoserver/<geoserver_instance_name>`` is as follows:

* **data**: geoserver configuration files
* **logs**: geoserver logs, outside of the datadir, since it needs no backup
* **extdata**: the actual data to be published, i.e. raster and shp files.

Further details on customization are found in :ref:`geoserver`.


Environment settings
--------------------

Check the content of the ``setenv.sh`` files:

.. code-block:: sh

  # Application specific environment
  GEOSERVER_DATA_DIR=/var/$SERVICE/data
  GEOSERVER_LOG_LOCATION=/var/$SERVICE/logs/geoserver.log

  # Java options
  JAVA_OPTS=-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m -XX:PermSize=64m -XX:+UseConcMarkSweepGC -XX:NewSize=48m -Dorg.geotools.shapefile.datetime=true -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION -Duser.timezone=GMT

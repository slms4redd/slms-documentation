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

We are going to put geoserver related data inside the directory ``/var/???_geoserver``.

Each geoserver instance will have its own dir within that one:

* ``/var/stg_geoserver``: for the staging geoserver instance
* ``/var/diss_geoserver``: for the dissemination geoserver instance

*Please note that if you are installing staging and dissemination area on different servers, 
the above two directories will be installed each inside its own server.*

The directory structure inside is as follows:

* **data**: geoserver configuration files
* **logs**: geoserver logs, outside of the datadir, since it needs no backup.

Further details on customization are found in :ref:`geoserver`.


Onlinestats (WPS)
-----------------

Needed in dissemination area to run custom statistics.

1. Install GeoServer's official WPS Extension.
2. Clone the utils project from `https://github.com/nfms4redd/nfms-utils/`::

    cd onlinestats
    mvn install

3. Get its dependencies, running::

    mvn dependency:copy-dependencies

4. Copy from ``target/dependency`` to GeoServer's ``WEB-INF/lib`` the jars that aren't already there::

    commons-cli-1.2.jar
    gt-sample-data-8.0.jar
    hamcrest-core-1.1.jar
    junit-4.10.jar
    jt-classifiedstats-1.2-GAEZM15092011.jar
    jt-utils-1.2-GAEZM15092011.jar
    teststats-1.1-SNAPSHOT.jar

.. note:: This dependency list can change if onlinestats project evolves; ``diff`` command will help sorting out the needed ones.

5. Copy also the onlinestats code from ``target`` to ``WEB-INF/lib``::

   onlinestats-1.1-SNAPSHOT.jar

.. warning:: The following files could conflict. Delete the original one from GeoServer, if needed:

   * GeoServer: jt-utils-1.2.0.jar
   * AppStats: jt-utils-1.2-GAEZM15092011.jar

.. note:: Custom chart rendering will need an ``htmlChart`` function in the groovy ChartScript. This function receives a Map
    whose keys are dates (in string format), and values are a matrix of ``double`` values with stats data. The return value must
    be the report contents. See an example in: https://gist.github.com/4389382#file-deforestation_stats-groovy-L45


Environment settings
--------------------

Check the content of the ``setenv.sh`` files:

.. code-block:: sh

  SERVICE=stg_geoserver # OR diss_geoserver

  # Application specific environment
  GEOSERVER_DATA_DIR=/var/$SERVICE/data
  GEOSERVER_LOG_LOCATION=/var/$SERVICE/logs/geoserver.log

  # Java options
  JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m -XX:PermSize=64m -XX:+UseConcMarkSweepGC -XX:NewSize=48m -Dorg.geotools.shapefile.datetime=true -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION -Duser.timezone=GMT"

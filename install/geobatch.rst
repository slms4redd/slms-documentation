.. _unredd-install-stg_geobatch:

Deploy and configure staging GeoBatch
=====================================

GeoBatch only needs to be installed in the staging area. 
No batch processing is going to be run in the dissemination system.

GeoBatch configuration files
----------------------------

Geobatch uses some directories for its processing needs.

We'll create all of them under ``/var/stg_geobatch/``:

* ``config``: GeoBatch main configuration directory

* ``input``: Root input directory

  * ``input/ingest``: Ingestion flow input directory
  * ``input/reprocess``: Reprocess flow input directory
  * ``input/publish``: Publish flow input directory

* ``temp``: Root temp directory

  * ``temp/ingestionFlow``: Ingestion flow temp dir, used during flow execution
  * ``temp/reprocessFlow``: Reprocess flow temp dir, used during flow execution
  * ``temp/publishFlow``: Publish flow temp dir, used during flow execution

* ``orig``: The place where original data from ingested flows will be copied


The ``config`` directory is the most important. It contains the flow definitions and
the files needed to generate charts from statistics.

Copy the content of ``WEB-INF/data`` (included inside of ``geobatch.war``)
to ``/var/stg_geobatch/config/``. This is a sample configuration. Edit
the ``ingestionFlow.xml``, ``reprocessFlow.xml``, ``publishFlow.xml``,
and the groovy scripts under ``chartscripts``  to adjust the different paths,
users and passwords as needed. You may also change the flow definitions to your
needs.

.. note::

   See :ref:`unredd-geobatch` chapter for details on how to configure the flows.


Remember to change the file permisions as follows::

  cd /var/stg_geobatch/
  chown -R tomcat6:tomcat6 .

Groovy scripts need to be exeutable::

  chmod ug+x config/chartscripts/*.groovy


Environment settings
--------------------
  
This is the ``bin/setenv.sh`` file:

.. code-block:: sh

  SERVICE=stg_geobatch

  # Application specific environment
  GEOBATCH_CONFIG_DIR=/var/$SERVICE/config
  GEOBATCH_TEMP_DIR=/var/$SERVICE/temp
  UNREDD_OVR=$GEOBATCH_CONFIG_DIR/unredd-ovr.properties

  # Java options
  JAVA_OPTS="-server -Xms2048m -Xmx2048m -DGEOBATCH_CONFIG_DIR=$GEOBATCH_CONFIG_DIR -DGEOBATCH_TEMP_DIR=$GEOBATCH_TEMP_DIR -Dunredd-ovr=$UNREDD_OVR -Duser.timezone=GMT"

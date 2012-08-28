.. module:: unredd.install.stg_geobatch

Deploy and configure GeoBatch (S only)
======================================

GeoBatch only needs to be installed in the staging area. 
No batch processing is going to be run in the dissemination system.

:file:`/var/tomcat/stg_geobatch`

Directory structure
-------------------

Geobatch needs some directories for its needs. We'll root all of them under ``/var/geobatch/``
::

  /var/geobatch/config             GeoBatch main configuration directory
  /var/geobatch/input              Root input directory
  /var/geobatch/input/ingest       Ingestion flow input directory
  /var/geobatch/input/reprocess    Reprocess flow input directory
  /var/geobatch/input/publish      Publish flow input directory
  /var/geobatch/temp               Root temp directory
  /var/geobatch/temp/ingest        Ingestion flow temp dir 
  /var/geobatch/temp/reprocess     Reprocess flow temp dir 
  /var/geobatch/temp/publish       Publish flow temp dir 


Configuration files
-------------------
  
This is the ``bin/setenv.sh`` file::

  GEOBATCH_CONFIG_DIR=/var/geobatch/config
  GEOBATCH_TEMP_DIR=/var/geobatch/temp
  
  JAVA_OPTS="-server -Xms512m -Xmx2048m
     -DGEOBATCH_CONFIG_DIR=$GEOBATCH_CONFIG_DIR
     -DGEOBATCH_TEMP_DIR=$GEOBATCH_TEMP_DIR -Dunredd-ovr=$UNREDD_OVR"

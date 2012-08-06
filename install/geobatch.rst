.. module:: unredd.install.stg_geobatch

Deploy and configure GeoBatch (S only)
======================================

:file:`/var/tomcat/stg_geobatch`

Geobatch own dirs::

  /var/geobatch/config             GeoBatch config dir
  /var/geobatch/temp               GeoBatch temp dir
  /var/geobatch/input              all flows input dir:
  /var/geobatch/input/ingest       ingestion flow input di
  /var/geobatch/input/reprocess    reprocessflow input dir 
  /var/geobatch/temp               all flows temp dir:
  /var/geobatch/temp/ingestFlow    ingestion flow input dir 
  /var/geobatch/temp/reprocessFlow reprocess flow input dir 

**TODO** :file:`/var/geobatch` is a bit different, as soon as possible
synch docum/deploy

::

  JAVA_OPTS="-server -Xms512m -Xmx2048m
  -DGEOBATCH_CONFIG_DIR=$GEOBATCH_CONFIG_DIR
  -DGEOBATCH_TEMP_DIR=$GEOBATCH_TEMP_DIR -Dunredd-ovr=$UNREDD_OVR"

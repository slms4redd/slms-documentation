.. module:: unredd.install.requirements

Hardware requirements
=====================

System requirements depend very much on the load of the system and the amount of data being managed.


Staging area
------------

The staging area will run at least the following applications:

 * GeoBatch, requiring ~2 GB RAM.
 * GeoServer, requiring ~1 GB RAM.
 * GeoStore, requiring ~0.5 GB RAM.
 * Admin, with default memory requirements (128 MB).

Plus the memory needed by the Operating System and other running services. A minimum of 8 GB of RAM is recommended.

Ingestion and reprocessing flow execution where predefined statistics are calculated are specially demanding in terms of computational power.

Disk requirements depend mainly on the amount of raster data being ingested and managed.


Dissemination area
------------------

The dissemination area will run at least the following applications:

 * GeoServer, requiring ~1.5 GB RAM.
 * GeoStore, requiring ~0.5 GB RAM.
 * Portal, with default memory requirements (128 MB).

Plus the memory needed by the Operating System and other running services. A minimum of 6 GB of RAM is recommended.

Dynamic or real-time statistics will determine the processing demands. Depending on the complexity of real-time calculations, the dissemination area may need a cluster of machines working in parallel.

Disk requirements depend mainly on the amount of raster data being published in geoserver, and on the size of the tile cache.

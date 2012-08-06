.. module:: unredd.install

=================================================================
NFMS in Production - Installation guide for system administrators
=================================================================

This module describes the step by step manual installation and configuration of the nfms platform, needed in a production environment.

In this module you will see:

.. toctree::
   :maxdepth: 1 

   Staging (S) and dissemination (D) purposes <staging_vs_dissemination>
   System requirements (S+D) <requirements>
   Installing needed system packages (S+D) <system_packages>
   Installing GDAL (S+D) <gdal>
   Setup shared filesystem (S+D) <shared_filesystem>
   Setup PostGIS (S+D) <postgis>
   Preparing tomcat instances (S+D) <tomcat_instances>
   Deploy and configure staging GeoServer <stg_geoserver>
   Deploy and configure staging GeoStore <stg_geostore>
   Deploy and configure GeoBatch (S only) <geobatch>
   Deploy and configure admin portal (S only) <admin>
   Deploy and configure dissemination GeoServer <diss_geoserver>
   Deploy and configure dissemination GeoStore <diss_geostore>
   Deploy and configure user portal (D only) <portal>
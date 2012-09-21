.. module:: unredd.install

==========================================================
Deploying NFMS in Production: System Administrator's Guide
==========================================================

This module describes the step by step installation and configuration of the nfms platform, as needed in production environments.

.. note:

   This step by step guide is based on a `Ubuntu 12.04 <http://www.ubuntu.com/>`_ Linux distribution.
   The exact steps may vary depending on your Operating System, distribution or version.
   
   The NFMS can be run on different operating systems, but we recommend to use a Ubuntu or CentOS Linux distributions.


In this module you will see:

.. toctree::
   :maxdepth: 2 

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

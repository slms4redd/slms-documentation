.. module:: geoserver.structure
   :synopsis: Learn the structure of the GeoServer Data Directory.

.. _geoserver.structure:

Structure of the GeoServer Data Directory
=========================================

The following is the GEOSERVER_DATA_DIR structure::

	data_directory/
   		global.xml
   		logging.xml
   		wms.xml
   		wfs.xml
   		wcs.xml
   		data/
   		demo/
		gwc/   		
   		layergroups/
   		logs/
		palettes/
   		security/
   		styles/
   		user_projections/
   		workspaces/
   		www/

.. list-table::
   :widths: 20 80

   * - **File**
     - **Description**
   * - ``global.xml``
     - Contains settings that go across services, including contact information, JAI settings, character sets and verbosity.
   * - ``logging.xml``
     - Specifies the logging level, location, and whether it should log to std out.  
   * - ``wcs.xml`` 
     - Contains the service metadata and various settings for the WCS service.
   * - ``wfs.xml`` 
     - Contains the service metadata and various settings for the WFS service.
   * - ``wms.xml`` 
     - Contains the service metadata and various settings for the WMS service.
   * - ``data``
     - Not to be confused with the ``GeoServer data directory`` itself, the data directory is a location where actual data can be stored. This directory is commonly used to store shapefiles and raster files but can be used for any data that is file based. The main benefit of storing data files inside of the data directory is portability.
   * - ``demo``
     - The demo directory contains files which define the sample requests available in the Sample Request Tool.
   * - ``gwc``
     - This directory holds the cache created by the embedded GeoWebCache service.
   * - ``layergroups``
     - Contains information on the layer groups configurations.
   * - ``logs``
     - This directory contains the GeoServer logging files (log file and logging properties files).
   * - ``palettes``
     - The palettes directory is used to store pre-computed Image Palettes. Image palettes are used by the GeoServer WMS as way to reduce the size of produced images while maintaining image quality.
   * - ``security``
     - The security directory contains all the files used to configure the GeoServer security subsystem. This includes a set of property files which define access roles, along with the services and data each role is authorized to access.
   * - ``styles``
     - The styles directory contains a number of Styled Layer Descriptor (SLD) files which contain styling information used by the GeoServer WMS. 
   * - ``user_projections``
     - The user_projections  directory contains extra spatial reference system definitions. The epsg.properties can be used to add new spatial reference systems, whilst the epsg_override.properties file can be used to override an official definition with a custom one.
   * - ``workspaces``
     - The various workspaces directories contain metadata about ``stores`` and ``layers`` which are published by GeoServer. Each layer will have a layer.xml file associated with it, as well as either a coverage.xml or a featuretype.xml file depending on whether it's a raster or vector.
   * - ``www``
     - The www directory is used to allow GeoServer to act like a regular web server and serve regular files. While not a replacement for a full blown web server the www  directory can be useful to easily serve OpenLayers map applications (this avoids the need to setup a proxy in order to respect the `same origin policy <http://en.wikipedia.org/wiki/Same_origin_policy>`_).


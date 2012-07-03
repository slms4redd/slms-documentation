.. module:: geoserver.services

.. _geoserver.services:


Services
--------

In this section will be described the services furnished by GeoServer and accessibles from the Web Administrator Interface.


OGC Services
^^^^^^^^^^^^

In this section we will see how to customize the OGC Web services settings. You have to be logged in as *Administrator* in order to access the advanced configuration options.


#. Navigate to the GeoServer `Welcome Page <http://localhost:8080/geoserver/>`_.
 
#. From the :guilabel:`Welcome` page navigate to the :guilabel:`WCS` by clicking the link on the :guilabel:`Services` section.

   .. figure:: img/ogc_services1.jpg
      :width: 600

      WCS Metadata

   * Customize the WCS metadata by modifying the *Maintainer*, *Online Resource*, *Title* and *Abstract* sections.
   
   * Add the keywords *Prato* and *Comune* to the Keywords list

#. From the :guilabel:`Welcome` page navigate to the :guilabel:`WFS` by clicking the link on the :guilabel:`Services` section.

   .. figure:: img/ogc_services2.jpg
      :width: 650

      WFS Metadata
      
   * Customize the WFS metadata by modifying the *Maintainer*, *Online Resource*, *Title* and *Abstract* sections.
   
   * Add the keywords *Prato* and *Comune* to the Keywords list
	 
   * Make sure the *Return bounding box with every feature* check is disabled if you want better performance from WFS *GetFeature* operation

#. From the :guilabel:`Welcome` page navigate to the :guilabel:`WMS` by clicking the link on the :guilabel:`Services` section.

   .. figure:: img/ogc_services3.jpg
      :width: 650

      WMS Metadata
      
   * Customize the WFS metadata by modifying the *Maintainer*, *Online Resource*, *Title* and *Abstract* sections.
   
   * Add the keywords *Prato* and *Comune* to the Keywords list

   * Make sure the *Nearest Neighbor* is selected as the default interpolation for better performances. Select the other for better image quality.

   * Enable *Watermark* and insert ``file:data/prato.gif`` into the *Watermark URL* text box and ``75`` into the *Watermark Transparency* text box.

	   .. figure:: img/ogc_services4.jpg
	
	      WMS Watermarks
   
At this point the OGC services are configured for a Production environment.

GWC Service
^^^^^^^^^^^

GeoWebCache (GWC) is a WMS tiling client integrated into GeoServer.


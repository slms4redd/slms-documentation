.. module:: geoserver.layer_preview

.. _geoserver.layer_preview:


Layer Preview
-------------

The Map Preview tool is used to quickly preview a layer that has been configured in GeoServer. It is based on the `OpenLayers <http://openlayers.org>`_ library which is a great tool for visualizing layers via WMS. This section covers the use of the Map Preview.

#. Navigate to the GeoServer `Welcome Page <http://localhost:8080/geoserver/>`_.
 
#. From the :guilabel:`Welcome` page navigate to the :guilabel:`Map Preview` by clicking the :guilabel:`Layer Preview` link.

   .. figure:: img/map_preview1.jpg
      :width: 600
      
      Viewing the demo page

#. From the :guilabel:`Map Preview` page, preview the :guilabel:`states` layer by clicking the :guilabel:`geosolutions:states` link.

   .. figure:: img/map_preview2.jpg
      :width: 650

      Viewing the OpenLayers map preview

   .. note:: The **Map Preview** contains a list of all layers currently published by GeoServer. A layer can be previewed in a variety of formats like KML and GeoRSS. The default is an OpenLayers map.

   .. figure:: img/map_preview3.jpg
 
      Previewing the states layer

At this point we finished the basic tour of GeoServer and we're going to data configuration.

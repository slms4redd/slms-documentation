.. _geoserver.filtering:


Filtering Maps
--------------


This section shows the GeoServer WMS fitering capabilities.

#. Navigate to the GeoServer `Welcome Page <http://localhost:8080/geoserver/web/>`_.

#. Go to the :guilabel:`Layer Preview` link at the bottom of the lefthand menu and show the :guilabel:`geosolutions:WorldCountries` layer with OpenLayers 'Common Format'.

   .. figure:: img/filtering1.png
      
	  Showing the GeoServer layer prewiew

   .. figure:: img/filtering2.png

      Show the layer with OpenLayers

#. From the :guilabel:`Filter` combo box select 'CQL' and enter the following command in the text field::

	POP_EST <= 5000000 AND POP_EST >100000

#. Click 'Apply Filter' botton on the right.

   .. figure:: img/filtering3.png

      Result of the CQL filter
 
   .. note:: The corresponding WMS request is: 
   
	     http://localhost:8080/geoserver/geosolutions/wms?service=WMS&version=1.1.0&request=GetMap&layers=geosolutions:WorldCountries&styles=&bbox=-180.0,-89.99889902136009,180.00000000000003,83.59960032829278&width=684&height=330&srs=EPSG:4326&format=image/png&CQL_FILTER=POP_EST%20%3C=%205000000%20AND%20POP_EST%20%3E100000

#. Now enter the following command in the text field::

	DISJOINT(the_geom, POLYGON((-90 40, -90 45, -60 45, -60 40, -90 40))) AND strToLowerCase(NAME) LIKE '%on%'
	
#. Click 'Apply Filter' botton on the right.

   .. figure:: img/filtering6.png

      Result of the CQL filter
 
   .. note:: The corresponding WMS request is: 
   
         http://localhost:8080/geoserver/geosolutions/wms?service=WMS&version=1.1.0&request=GetMap&layers=geosolutions:WorldCountries&styles=&bbox=-180.0,-89.99889902136009,180.00000000000003,83.59960032829278&width=684&height=330&srs=EPSG:4326&format=image/png&CQL_FILTER=DISJOINT%28the_geom%2C%20POLYGON%28%28-90%2040%2C%20-90%2045%2C%20-60%2045%2C%20-60%2040%2C%20-90%2040%29%29%29%20AND%20strToLowerCase%28NAME%29%20LIKE%20%27%25on%25%27		 

#. From the :guilabel:`Filter` combo box select 'OGC' and enter the following filter in the text field:

   .. code-block:: xml

	<Filter><PropertyIsEqualTo><PropertyName>TYPE</PropertyName><Literal>Sovereign country</Literal></PropertyIsEqualTo></Filter>

#. Click 'Apply Filter' botton on the right.

   .. figure:: img/filtering4.png

      Result of the OGC filter

   .. note:: The corresponding WMS request is: 
	
	     http://localhost:8080/geoserver/wms?LAYERS=geosolutions%3AComuni&STYLES=&HEIGHT=600&WIDTH=950&SRS=EPSG%3A3003&FORMAT=image%2Fpng&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&EXCEPTIONS=application%2Fvnd.ogc.se_inimage&FILTER=%3CFilter%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3EPROVINCIA%3C%2FPropertyName%3E%3CLiteral%3Eprato%3C%2FLiteral%3E%3C%2FPropertyIsEqualTo%3E%3C%2FFilter%3E&BBOX=1617269.5547063,4832131.9509527,1715607.0958195,4894239.8716559
	
#. From the :guilabel:`Filter` combo box select 'FeatureID' and enter the following features ids in the text field separated by comma::

	WorldCountries.227,WorldCountries.184,WorldCountries.33

#. Click 'Apply Filter' botton on the right.

   .. figure:: img/filtering5.png

      Result of the FeatureID filter

   .. note:: The corresponding WMS request is: 

         http://localhost:8080/geoserver/geosolutions/wms?service=WMS&version=1.1.0&request=GetMap&layers=geosolutions:WorldCountries&styles=&bbox=-180.0,-89.99889902136009,180.00000000000003,83.59960032829278&width=684&height=330&srs=EPSG:4326&format=image/png&FEATUREID=WorldCountries.227,WorldCountries.184,WorldCountries.33
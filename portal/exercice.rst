UNREDD Custom Portal Exercise
=============================

Deploying the portal
--------------------

Copy ``custom-portal.war`` to ``/var/stg_geostore/webapps``::

 cp custom-portal.war /var/stg_geostore/webapps

Edit /etc/apache2/mods-enabled/proxy_ajp.conf and add these 3 lines::

 sudo gedit /etc/apache2/mods-enabled/proxy_ajp.conf

::

 ProxyPass        /custom-portal    ajp://localhost:8100/custom-portal
 ProxyPassReverse /custom-portal    ajp://localhost:8100/custom-portal
 ProxyPassReverse /custom-portal/   ajp://localhost:8100/custom-portal/

Restart Apache::

 sudo /etc/init.d/apache2 restart

You should see a basic portal in:

 http://localhost/custom-portal/

Customizing look & feel
-----------------------

First, we will add some logos to the header. Open ``/var/tomcat/stg_geostore/webapps/custom-portal/WEB-INF/jsp/banner.jsp`` and add the following element next to the "flag" element.

.. code-block:: html

  <div id="logos">
  </div>

Then, open ``/var/tomcat/stg_geostore/webapps/custom-portal/css/unredd.css``. Add the following style rules to customize the logos' size, position and contents:

.. code-block:: css

  #logos
  {
    position: absolute;
    top: 19px;
    right: 20px;
    height: 55px;
    width: 130px;
    z-index: 1100;
    background:url(../images/logos.png) no-repeat 0 0;
 }


Adding a layer
--------------

Next, we are adding a layer with the province boundaries. Go to::

 /var/tomcat/stg_geostore/webapps/custom-portal/WEB-INF/jsp

And open ``layers.jsp`` with a text editor. Search for the “layers” element. You will see forestClassification, forestMask and countryBoundaries layers. We will add the “provinces” one:

.. code-block:: json

 "provinces": {
     "baseUrl": "/stg_geoserver/wms",
     "wmsName": "unredd:drc_provinces",
     "imageFormat": "image/png",
     "visible": true,
     "sourceLink":   "http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo",
     "sourceLabel": "WRI",
     "queryable": true
    }

Next, we will add a “province” context:

.. code-block:: json

 "provinces": {
     "active": true,
     "infoFile": "provinces_def.html",
     "label": "<spring:message code="provinces" />",
     "layers": ["provinces"],
     "inlineLegendUrl": "/stg_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:drc_provinces&TRANSPARENT=true"
    }

Finally, we will add the context into a context group. Search for the group under “contextGroups” which has the “countryBoundaries” in it, and add the provinces context there:

.. code-block:: json

  { "context": "provinces" }

Linking statistics to a layer
-----------------------------

Edit ``/var/tomcat/stg_geostore/webapps/custom-portal/js/custom.js``, search for the layerInfo element, and add this contents:

.. code-block:: js

    drc_provinces: function(feature) {
       var that = {};
       that.title = function() {
           return UNREDD.langData.province + ": " + feature.attributes.PROVINCE;
       };
       that.statsLink = function() {
           //return 'data/charts/' + languageCode + '/admin1/admin1_' + feature.attributes.OBJECTID + '.html?name=' + feature.attributes.PROVINCE;
           return '/stg_geostore/rest/misc/category/name/ChartData/resource/name/deforestation_script_' +  feature.attributes.OBJECTID + '_' + languageCode + '/data?name=' + feature.attributes.PROVINCE;
       };
     
       return that;
    }


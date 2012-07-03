.. module:: geoserver.service_level

.. _geoserver.service_level:


Service Level Security
----------------------


GeoServer allows access to be determined on a service level (WFS, WMS). Access to services is linked to roles. Services and roles are linked in a file called ``services.properties``, which is located in the ``security`` directory in your $GEOSERVER_DATA_DIR.

   .. note::
   
      The syntax for setting security is as follows.  (Parameters in brackets are optional.)::

          service[.method]=role[,role2,...]

      where:

          * **service** can be ``wfs``, ``wms``, or ``wcs``
          * **method** can be any method supported by the service. (Ex: GetFeature for WFS, GetMap for WMS)
          * **role[,role2,...]** is the name(s) of predefined roles.

      Service-level security and :ref:`geoserver.layer_level` cannot be combined.  For example, it is not possible to specify access to a specific OGC service on one specific layer.

#. From the :guilabel:`Welcome` page click the :guilabel:`Service security` link on the :guilabel:`Menu` Security section.

   .. note:: You have to be logged in as Administrator in order to activate this function.

#. Click :guilabel:`Add new rule` in the top menu and enter the following configuration:

	- Select ``wms`` from ``Service`` combo box.
	- Select ``GetMap`` from ``method`` combo box.
	- Select the :guilabel:`ROLE_WS` created in previous section and pressing the right arrow on center of the window.

   .. figure:: img/service1.png
   
      The new role form 

#. Click the :guilabel:`Save` button.   

   .. figure:: img/service2.png
   
      The Service access rules list  

#. Navigate to the `Map Preview <http://localhost:8080/geoserver/web/?wicket:bookmarkablePage=:org.geoserver.web.demo.MapPreviewPage>`_ and trying to show a layer and Try visualizing with OpenLayers a layer. You'll find that it is inaccessible.

#. Logout as ``admin`` and login as ``wsuser``.

#. Navigate to the `Map Preview <http://localhost:8080/geoserver/web/?wicket:bookmarkablePage=:org.geoserver.web.demo.MapPreviewPage>`_ and trying to show a layer and Try visualizing with OpenLayers a layer. Now the layers are accessible.

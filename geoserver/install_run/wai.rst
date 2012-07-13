.. module:: geoserver.wai

.. _geoserver.wai:


Web Administrator Interface
---------------------------

In this moudule we will get acquainted with the GeoServer Web Administrator Interface.

#. Navigate to the GeoServer `Welcome Page <http://localhost:8080/geoserver/>`_.
 
   .. figure:: img/basic_tour1.jpg
      :width: 600

      GeoServer welcome page
   
   
   Here you can easily distinguish several sections:
   
   * The login section; it is used to access the administrator functions. The menu will show more functions when logged in (see below)

      .. figure:: img/basic_tour2.jpg
         :width: 650

      GeoServer welcome page

   * The menu section on the left side; this section shows the available functions

      .. figure:: img/basic_tour3.jpg
         :width: 650

      GeoServer welcome page
   
   * The info section on the right; on the welcome page some standard info requests to the enabled OGC services are shown.

      .. figure:: img/basic_tour4.jpg
         :width: 650

      GeoServer welcome page
   
   * By clicking over one of the links in this section you can easily obtain the GetCapabilities document for each enabled Service.

      .. figure:: img/basic_tour5.jpg
         :width: 650

      WCS GetCapabilities document
      
#. From the :guilabel:`Welcome` page, insert in the :guilabel:`Login section` the credentials user *admin* and password *Geos*.

      .. note::  The GeoServer default credentials are user *admin* and password *geoserver*. You can add/modify users and passwords from the security section (see below).


      .. figure:: img/basic_tour6.jpg
         :width: 650

      Administrator functions


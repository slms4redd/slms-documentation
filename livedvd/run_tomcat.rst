Starting and stopping the applications
======================================

All of the UNREDD NFMS applications run on Apache Tomcat. They are split into multiple Tomcat instances, so they can be run separately. In the LiveDVD, you will find four Tomcat instances relevant to this Workshop:

===============  =========================  ===============================================
Tomcat instance  Contained applications     Access URL
===============  =========================  ===============================================
stg_geoserver    GeoServer 2.2.x            http://localhost/stg_geoserver/
stg_geobatch     GeoBatch                   http://localhost/stg_geobatch/
stg_geostore     | GeoStore                 | http://localhost/stg_geostore/rest/categories
                 | Administation Interface  | http://localhost/admin/
                 | Portal Interface         | http://localhost/portal/
===============  =========================  ===============================================

To **start** a tomcat instance, open a Terminal window and type::

  sudo /etc/init.d/[instance_name] start

For example, to use `geoserver`, type::

  sudo /etc/init.d/stg_geoserver start
  
After a few seconds, the contained applications will be accessible through their corresponding URLs (see table above).

To **stop** a tomcat instance, type::

  sudo /etc/init.d/[instance_name] stop

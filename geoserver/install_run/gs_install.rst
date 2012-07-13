.. module:: geoserver.gs_install

.. _geoserver.gs_install:


Installing GeoServer
--------------------

GeoServer is packaged as a standalone servlet for use with existing servlet container applications such as `Apache Tomcat <http://tomcat.apache.org/>`_.

.. note:: GeoServer has been mostly tested using Tomcat, and therefore these instructions may not work with other container applications.


Installation
^^^^^^^^^^^^

#. Navigate to the `GeoServer Download page <http://geoserver.org/display/GEOS/Download>`_ and pick the Latest stable version to download.

#. Select :guilabel:`Web archive` on the download page.

#. Download and unpack the archive.  Copy the file :file:`geoserver.war` to the directory that contains your container application's webapps.

#. Your container application should unpack the web archive and automatically set up and run GeoServer.

   .. note:: A restart of your container application may be necessary.


Running
^^^^^^^

Use your container application's method of starting and stopping webapps to run GeoServer.

#. Access Tomcat opening a browser and navigate to :file:`http://{host}:{}/geoserver`.  For example, with Tomcat running locally on port 8080 like in this WorkShop, the URL would be ``http://localhost:8080/geoserver``.


Uninstallation
^^^^^^^^^^^^^^

In case you need to remove GeoServer (but don't do it now) you can use the following steps:

#. Stop the container application.

#. Remove the GeoServer webapp from the container application's webapps directory.

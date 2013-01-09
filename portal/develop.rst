=================
Developer's guide
=================

Getting the code
================

The nfms4redd portal code is hosted on github. To get a local copy using git, run::

  git clone https://github.com/nfms4redd/nfms-portal.git


Building the portal
===================

Project build process is managed by Maven 2. Use this command to build, test and create the deployment web archive (war file)::

  mvn install
  
Then get the file in :file:`target/unredd-portal.war`.

Maven can be used also to run the portal. Use::

  mvn jetty:run
  
The portal will be accessible at:

  http://localhost:8181/portal/

To use a custom configuration, edit the :file:`pom.xml` file, and change the ``<portal_config_dir>`` property before running jetty.

.. _debugging_portal_eclipse:

Debugging in eclipse
=====================

In order to start the application in a J2EE container from eclipse, and therefore being able to debug, it is just necessary to
specify the -Dwtpversion=2.0 in the mvn eclipse:eclipse command::

	$ mvn eclipse:eclipse -Dwtpversion=2.0 

After it, on an eclipse with support for J2EE applications it is possible to right-click the project and
select "Debug as" > "Debug on server".

I've experienced some ClassNotFoundExceptions about classes existing on projects referenced by this one. Similar issues has been
reported here:

* http://www.eclipse.org/forums/index.php/t/248873/

With no solution yet: https://bugs.eclipse.org/bugs/show_bug.cgi?id=370839

Removing and adding again the dependent project in the "Deployment Assembly" tab of the project properties fixes the issue. I wanted
to compare the files in .settings to see the differences before and after, but no luck, the bug is no longer reproducible
even after a "mvn eclipse:clean eclipse:eclipse" or after removing the .settings folder.

Running tests
==============

Two profiles are configured in the pom.xml, one is "unit" and is active by default, excludes the tests
marked with the category "org.fao.test.FunctionalTestSuite". This is an interface
defined in the nfms-utils/common-tests with the only purpose of marking tests as "functional", this is, as
using the system from a functional point of view and probably requiring interaction with other components of
the system (geoserver, geobatch, etc.).

Therefore, by issuing::

	$ mvn test
	
Only the tests that does not require external applications will be run.

The other profile is "all-tests" and does not exclude any test from the build. In order to activate this profile, it
is possible to issue the command::

	$ mvn test -P all-tests
 

Background technologies
=======================

The following technologies are used in the portal software:

* HTML
* JavaScript (Ajax)
* CSS
* Java
* Spring MVC

In this section we mainly will focus on the first three in the list, needed to customize the look and feel, the layer list and, to a certain extent, the features of the portal.

Java and Spring framework
-------------------------

Server side Java is currently very limited and only used for localizing the text and to add time information to the layers JSON description.

The portal has a server-side back end that manages text localization and the creation of the ``layers.json`` file that defines the connection between elements in the layer list in the User Interface, and the actual WMS layers managed by GeoServer.

It makes use of the Spring MVC framework. The logic is implemented in ApplicationController.java. It manages the connection to the underlying GeoStore server and builds the localized ``layers.json`` page, through the use of the ``WEB-INF/jsp/layers.json`` file.

Spring MVC also manages the localization of the index page (``index.do``, internally redirected from the default page ``index.jsp``).


JavaScript libraries
--------------------

The main User Interface functionality is implemented in the `unredd.js` JavaScript file. There is also a `custom.js` used for customization of the statistics chart popup on the client side.

The portal makes use of the following open-source libraries:

* JQuery
* OpenLayers
* JQuery UI
* Highcharts JS
* Fancybox

Let’s see in details each of them and explain how they are used.

OpenLayers
..........

**Version: 2.12**

To keep JavaScript overload to a minimum, a custom profile is used to build the OpenLayers javascript file.

The build config file, where the selected OL classes are listed, can be found on nfms-portal github project,
under :file:`lib/ol-unredd.cfg`. The OpenLayers source files are linked as a GitHub Submodule pointing to
the official OpenLayers GitHub repository.

.. note::

   If new OpenLayers classes are needed, add them manually to the build config file, and rebuild.
   The :file:`lib/ol-build.sh` helper script will build and copy the file to the final location. 

The javascript file is built uncompressed (not *minified*), so it can be used for debugging.
The *minification* process and other load time optimizations will be performed automatically at runtime,
via `packtag <http://sourceforge.net/projects/packtag/>`_.

OpenLayers is used to implement the following mapping capabilities:

* Show map layers
* Pan and zoom map
* Query - get statistics about a selected polygon
* Draw and retrieve polygons on feedback
* Real-time statistics
* Set layer transparency

JQuery
......

**Version: 1.8.16**

JQuery is used as the infrastructure to build the dynamic content of the site (mainly to create and manage the map layers - we’ll see this later in the layers.jsp section), and indirectly by JQuery UI.

JQuery UI
.........

**Version: 1.8.16**

JQuery UI is used for some of the UI widget (Buttons, layers accordion menu, legend dialog) of the portal.

Highcharts
..........

**Version: 2.1.5**

Highcharts is used to create statistics (charts) related to the selected polygon. The Highcharts library is only loaded when a chart is shown, thus reducing the amount of data that needs to be loaded by the browser before the rendering of the main page starts.

Fancybox
........

**Version: 2.0.5**

Used for modal dialogs (show charts and layer info).

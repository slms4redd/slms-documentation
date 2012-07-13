====================
Portal customization
====================

In this section we will learn how to customize the elements on the user interface.

The NFMS Portal interface is composed of a HTML page that encloses all the functionalities, and some other pages that are loaded as iframes (i.e. statistics). All functions (mapping, querying, statistics,...) are implemented through Ajax calls, through the use of open-source libraries (see below).

To deploy the portal for a new country, you will need to modify some jsp files, a css file, and create the html and pdf content.

Technical background
--------------------

The following technologies are used in the portal software:

* HTML
* JavaScript (Ajax)
* CSS
* Java
* Spring MVC

In this section we mainly will focus on the first three in the list, needed to customize the look and feel, the layer list and, to a certain extent, the features of the portal.

JavaScript libraries
....................

The main User Interface functionality is implemented in the `unredd.js` JavaScript file. There is also a `custom.js` used for customization of the statistics chart popup on the client side.

The portal makes use of the following open-source libraries:

* JQuery
* OpenLayers
* JQuery UI
* Highcharts JS
* Fancybox

Let’s see in details each of them and explain how they are used.

JQuery
^^^^^^

**Version: 1.8.16**

JQuery is used as the infrastructure to build the dynamic content of the site (mainly to create and manage the map layers - we’ll see this later in the layers.jsp section), and indirectly by JQuery UI.

OpenLayers
^^^^^^^^^^

**Version: 2.11**

.. hint::

   Replaced Handlers/Click.js with the one from version 2.10 to correct a bug. Future versions of OpenLayers might not need this hack.

The following build config file has been used to customize OpenLayers.js thus making it lighter to download by browsers

::

  OpenLayers/Map.js
  OpenLayers/Layer/WMS.js
  OpenLayers/Projection.js
  OpenLayers/Control/Navigation.js
  OpenLayers/Control/DrawFeature.js
  OpenLayers/Request/XMLHttpRequest.js
  OpenLayers/Control/Scale.js
  OpenLayers/Control/WMSGetFeatureInfo.js
  OpenLayers/Format/WMSGetFeatureInfo.js
  OpenLayers/Format/GML.js
  OpenLayers/Layer/Markers.js
  OpenLayers/Layer/Wikimapia.js
  OpenLayers/Layer/Vector.js
  OpenLayers/Renderer/Canvas.js
  OpenLayers/Renderer/VML.js
  OpenLayers/Handler/Polygon.js
  OpenLayers/Icon.js
  OpenLayers/Marker.js
  OpenLayers/Strategy/Fixed.js
  OpenLayers/Protocol/HTTP.js
  OpenLayers/Format/KML.js

The library has been built using the Google Clojure Compiler. To do so (from the OpenLayers *README* file):

* Copy `clojure-compiler.jar` into the tools directory
* Copy the `unredd.cfg` file (content above) into the build directory
* From inside the build dir, run python ``build.py -c closure unredd OpenLayers.js``

OpenLayers is used to implement the following mapping capabilities:

* Show map layers
* Pan and zoom map
* Query - get statistics about a selected polygon
* Feedback
* Real-time statistics
* Set layers transparency

JQuery UI
^^^^^^^^^

**Version: 1.8.16**

JQuery UI is used for some of the UI widget (Buttons, layers accordion menu, legend dialog) of the portal.

Highcharts
^^^^^^^^^^

**Version: 2.1.5**

Highcharts is used to create statistics (charts) related to the selected polygon. The Highcharts library is only loaded when a chart is shown, thus reducing the amount of data that needs to be loaded by the browser before the rendering of the main page starts.

Fancybox
^^^^^^^^

**Version: 2.0.5**

Used for modal dialogs (show charts and layer info).

Java and Spring MVC
...................

Server side Java is currently very limited and only used for localizing the text and to add time information to the layers JSON description.

The portal has a server-side back end that manages text localization and the creation of the ``layers.json`` file that defines the connection between elements in the layer list in the User Interface, and the actual WMS layers managed by GeoServer.

It makes use of the Spring MVC framework. The logic is implemented in ApplicationController.java. It manages the connection to the underlying GeoStore server and builds the localized ``layers.json`` page, through the use of the ``WEB-INF/jsp/layers.json`` file.

Spring MVC also manages the localization of the index page (``index.do``, internally redirected from the default page ``index.jsp``).

Deploying a new country portal
------------------------------

The development directory structure is organized in a way that keeps files that are common to all the installations separated from country specific data. We’ll see now how to configure the portal for a country deployment, starting with the identification of the UI elements in the page, and then explaining how to change them depending on country needs.

Overview
........

To deploy to a new country:

* Add a new country directory in the ``webResources`` dir (i.e. ``png/``), with the following structure:

::

    webResources
    |--drc
       |--unredd.css
       `--loc
          |--en
          |  |--documents
          |  |--html
          |  `--images
          `--es
             |--documents
             |--html
             `--images

* Run ``mvn -Dcountry=<country_dir> clean install`` i.e. ``mvn -Dcountry=png clean install``
* Copy ``unredd-portal.war`` file into the servlet container webapps directory

In detail
.........

The portal development directory structure, outlined below, is composed of the following two main directories:

* ``src``, that contains the java source code for the controller, that connects to GeoStore (more on this later) and do some back-end processing to manage layers with time dimension, and the webapp directory, with the files that are common to every implementation (country)
* ``webReources``, that contains the a directory with custom files for each portal instance.

::

    |--src
    |  `--main
    |     |--java
    |     `--webapp
    |          |--css
    |          |--images
    |          |--js
    |          |  `--unredd.js
    |          |--META-INF
    |          `--WEB-INF
    |             |--jsp
    |             |  `--index.jsp
    |             |--unredd-portal-applicationContext.xml
    |             `--web.xml
    `--webResources
       |--drc
       |  |--banner.jsp
       |  |--layers.jsp
       |  |--messages.properties
       |  |--messages_en.properties
       |  |--unredd.css
       |  |--images
       |  |  |--flag.png
       |  |  |--logos.jpg
       |  |  `--ui-elements.png
       |  `--loc
       |     |--en
       |     |  |--documents
       |     |  |  |--Information note.pdf
       |     |  |--html
       |     |  |  |--deforestation_def.html
       |     |  |  |--ecoregions_def.html
       |     |  `--images
       |     |     |--deforestation.png
       |     |     `--ecoregions.png
       |     `--fr
       |        |--documents
       |        |  |--Information note.pdf
       |        |--html
       |        |  |--deforestation_def.html
       |        |  `--ecoregions_def.html
       |        |--images
       |           |--deforestation.png
       |           `--ecoregions.png
       |--pry
          |...

The webResources directory
^^^^^^^^^^^^^^^^^^^^^^^^^^

To customize the portal for a new country, you need to add a new directory with the country code as name, and with the same structure as the ones below, in the ``webResources`` directory. For example, for DRC it would be (please note that the locale codes inside ``loc/`` may differ):

::

    webResources
    |--drc
       |--unredd.css
       `--loc
          |--en
          |  |--documents
          |  |--html
          |  `--images
          `--es
             |--documents
             |--html
             `--images

The ``webResources`` directory contains custom html, jsp, pdf, image, and message bundle files that are specific for each country instance of the portal, as they are also used for the translation of the layer labels. Let's see in detail each file and directory that needs to be modified.

How to customize the layout
^^^^^^^^^^^^^^^^^^^^^^^^^^^

**banner.jsp**

The ``banner.jsp`` file is included by ``index.jsp`` (line ~33).

As an example, the content of banner.jsp for the Paraguay portal is:

::

  <div id="banner">
    <div id="flag"></div>
        <div id="logos">
            <a href="http://www.infona.gov.py/" id="infona_logo" target="_blank"></a>
            <a href="http://www.seam.gov.py/" id="seam_logo" target="_blank"></a>
            <div id="fapi_logo"></div>
        </div>
    <span id="title"><spring:message code="title" /></span>
  </div>

It contains custom logos and links to the partner institutions. The line ``<span id="title"><spring:message code="title" /></span>`` gets the localized title string from Spring MVC and It shouldn't need to be modified.

Additional HTML elements can be added according to specific needs, taking into account that the overall height must be 92px.

Additional refinements can be obtained by customizing the ``unredd.css`` file (see below).

**images directory**

The ``webResources/images`` directory contains files that will overwrite those inside ``main/webapps/images``. Please note that they should have the same resolution as the original ones, to preserve the look and feel of the page.

Tipical usage of this customization is to add the flag of the country at the left side of the banner, and set the logos of the partner institutions (right side of the banner).

**unredd.css**

This file defines the layout of the portal. It should be used to customize colors, banner, and adjusting sized of objects, for example to adjust the size and offset of the flag and logos images in the banner.

How to setup the layers
^^^^^^^^^^^^^^^^^^^^^^^

**The layers.jsp file**

The file ``layers.jsp`` is in the root of the country specific directory inside webResources. It is used by the ``ApplicationController`` class to generate the ``layers.json`` file.

It contains the information to associate user interface elements (layer list pane on the left side of the page) to the WMS layers provided by GeoServer, and customize legends, online legends thumbnails, identify which layers can be queried. It also allows to group layers into a three levels tree.

This is a sample ``layers.jsp`` file:

::

    <%@ page session="true"
    %><%@taglib uri="http://www.springframework.org/tags" prefix="spring"
    %><%@ page contentType="application/json" pageEncoding="UTF-8"
    %>{
      "layers": {
        "reddPlusProjects": {
          "label": "<spring:message code="redd_plus_projects" />",
          "baseUrl": "/geoserver_drc/WMS",
          "WMSName": "unredd:redd_plus_projects",
          "imageFormat": "image/png",
          "visible": "true",
          "legend": "redd_plus_projects.png",
          "sourceLink": "http://www.observatoire-comifac.net/",
          "sourceLabel": "OFAC"
        },
        "reddPlusProjects_simp": {
          "baseUrl": "/geoserver_drc/WMS",
          "WMSName": "unredd:redd_plus_projects_simp",
          "imageFormat": "image/png",
          "visible": "false",
          "queryable": "true"
        },
        "landsatMosaic": {
          "baseUrl": "/geoserver_png/wms",
          "wmsTime": "${landsat_mosaic}",
          "wmsName": "unredd:pry_landsat_mosaic",
          "imageFormat": "image/png",
          "visible": "true"
        },
        "pryTrainingLandsatSceneTime": {
          "baseUrl": "/geoserver_png/wms",
          "wmsTime":  "2000-01-01T00:00:00.000Z,2005-01-01T00:00:00.000Z",
          "wmsName": "unredd:pry_training_landsat_scene_time",
          "imageFormat": "image/png",
          "visible": "true"
        }
      },
      "contexts": {
        "deforestation": {
          "infoFile": "deforestation_def.html",
          "label": "<spring:message code="deforestation" />",
          "layers": ["deforestation"]
        },
        "reddPlusInitiatives": {
          "active": "true",
          "infoFile": "redd_plus_initiatives_def.html",
          "label": "<spring:message code="redd_plus_initiatives" />",
          "layers": ["reddPlusInitiatives", "reddPlusInitiatives_simp"],
          "inlineLegendUrl": "/geoserver_drc/WMS?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:redd_plus_projects&STYLE=redd_plus_initiatives&TRANSPARENT=true"
        }
      },
      "contextGroups": 
      {
        "items": [
          {
            "group": {
              "label": "<spring:message code="base_layers" />",
              "items": [
                { "context": "blueMarble" },
                { "context": "facetForestClassification" },
                { "context": "uclForestClassification" },
                { "context": "landsat" },
                { "context": "hillshade" }
              ]
            }
          },
          {
            "group": {
              "label": "<spring:message code="forest_area_and_forest_area_change" />",
              "infoFile" : "forest_area_and_forest_area_changes_def.html",
              "items": [
                {
                  "group": {
                    "label": "<spring:message code="forest_land_remaining_forest_land" />",
                    "items": [
                      { "context": "degradation" },
                      { "context": "regrowth" },
                      { "context": "conservation" }
                    ]
                  }
                },
                {
                  "group": {
                    "label": "<spring:message code="forest_land_converted_to_non_forest" />",
                    "items": [
                      { "context": "deforestation" },
                      { "context": "trainingData" },
                      { "context": "intactForest" }
                    ]
                  }
                }
              }
            }
          }
        ]
      }

The ``layers.jsp`` file is divided into three sections:

* layers (`layers` object in the generated JSON file)
* contexts (`contexts` object in the generated JSON file)
* context groups (`contextGroups` object in the generated JSON file)

Let’s see each of the three sections above in detail.

*layers object*

Each object in the layers section of the JSON document has a correspondence with the layers defined in GeoServer. The correspondence is many to one, meaning that more than one object in layers can be associated with the same GeoServer layer.

Here is a sample section of the `layers` object:

::

    "reddPlusProjects": {
      "label": "<spring:message code="redd_plus_projects" />",
      "baseUrl": "/geoserver_drc/WMS",
      "WMSName": "unredd:redd_plus_projects",
      "imageFormat": "image/png",
      "visible": "true", *
      "legend": "redd_plus_projects.png", *
      "sourceLink": "http://www.observatoire-comifac.net/", *
      "sourceLabel": "OFAC" *
    },
    "reddPlusProjects_simp": {
      "baseUrl": "/geoserver_drc/WMS",
      "WMSName": "unredd:redd_plus_projects_simp",
      "imageFormat": "image/png",
      "visible": "false",
      "queryable": "true"
    }

Optional elements are identified with a * sign.

Follows a description of each element in the `layers` object

* `label`: label to be shown on the user interface - its value points to an element in the bundle properties file (see below) through spring custom tags
* `baseUrl`: the base url of the associated GeoServer layer
* `WMSName`: the name of the associated GeoServer WMS layer
* `imageForma`t the format of the image (usually `image/jpeg`, `image/png`, `image/png8`, or `image/gif`)
* `visible` [`"true"` or `"false"`], including quotes: whether the layer is visible or not (if not, it’s used only for queries. When a layer is queryable, a WMS ``getFeatureInfo`` request is sent to the server when clicking on it. In the sample above the `reddPlusProjects_simp` layer is a simplified version of reddPlusProject, used to highlight the contour of polygons.
* `legend`: the file name of the layer legend. It is resolved to the full path /loc/<language_code>/images/<legend> by the JavaScript engine
* `sourceLink`: the link to the external (or internal) data source
* `sourceLabel`: the label to be used for the source link
* `queryable`: whether the layer can be queried or not
* `wmsTime`: it can be either

  * a list of time instances (i.e. `"2000-01-01T00:00:00.000Z,2005-01-01T00:00:00.000Z"`)
  * GeoStore layer (i.e. `"landsat_mosaic"`) In this case the value must match the layer name in GeoStore, and the content is automatically generated according to the GeoStore layerUpdate resources available for the given layer.

`legend`, `sourceLink` and `sourceLabel` are used to show the layer legends in the Legend pane:

.. figure:: img/legend_pane.png
   :align: center

   Legend

*contexts object*

`contexts` puts in relation layer objects with real elements in the User Interface - see image below. A context can cointaint one or more layers.

.. figure:: img/two_levels_menu.png
   :align: center

   Two levels layer pane

Here is an example of the context section in the JSON file:

::

    ...
    "deforestation": {
      "infoFile": "deforestation_def.html", *
      "label": "<spring:message code="deforestation" />",
      "layers": ["deforestation"]
    },
    "reddPlusInitiatives": {
      "active": "true", *
      "infoFile": "redd_plus_initiatives_def.html", *
      "label": "<spring:message code="redd_plus_initiatives" />",
      "layers": ["reddPlusInitiatives", "reddPlusInitiatives_simp"],
      "inlineLegendUrl": "/geoserver_drc/WMS?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:redd_plus_projects&STYLE=redd_plus_initiatives&TRANSPARENT=true" *
    },
    ...

Optional elements are identified with a * sign.

* `label`: label to be shown on the user interface - its value points to an element in the bundle properties file (see below) through spring custom tags
* `layers`: array with references to objects in the layers section. Array values and layer object names must match.
* `infoFile`: html file with the info related to the context. It is resolved to the url ``/loc/<language_code>/html/<infoFile>``. It is loaded when clicking on the |infobutton| on an iframe
* `inlineLegendUrl`: url of the legend image to be shown at the left of the layer name, if available (see image below). It’s only usable if the layers has a legend that fits a 20x20 pixel image

.. |infobutton| image:: img/info_button.png

.. figure:: img/redd_registry_menu.png
   :align: center

   REDD registry menu

*contextGroups object*

The ``contextGroups`` defines the three structure (up to three levels) of the layers pane.

::

    "contextGroups": 
    {
      "items": [
        {
          "group": {
            "label": "<spring:message code="base_layers" />",
            "items": [
              { "context": "blueMarble" },
              { "context": "facetForestClassification" },
              { "context": "uclForestClassification" },
              { "context": "landsat" },
              { "context": "hillshade" }
            ]
          }
        },
        {
          "group": {
            "label": "<spring:message code="forest_area_and_forest_area_change" />",
            "infoFile" : "forest_area_and_forest_area_changes_def.html",
            "items": [
              {
                "group": {
                  "label": "<spring:message code="forest_land_remaining_forest_land" />",
                  "items": [
                    { "context": "degradation" },
                    { "context": "regrowth" },
                    { "context": "conservation" }
                  ]
                }
              },
              {
                "group": {
                  "label": "<spring:message code="forest_land_converted_to_non_forest" />",
                  "items": [
                    { "context": "deforestation" },
                    { "context": "trainingData" },
                    { "context": "intactForest" }
                  ]
                }
              }
            }
          }
        }
      ]
    }

It’s a recursive structure, but the parser only renders up to the second level (''Deforestation'' and ''National Training Data'' in the image above)

* `group`

  * At the first level it defines the different expandable elements in the “accordion” layers pane (REDD+ Registry in the image above)
  * At the second level (optional) it defines a grouping for the contexts
  * At the third (or second) level it defines the context contained in the group. Each `context` string in the `items` array must match one of the contexts defined earlier

* `label`: label to be shown on the user interface - its value points to an element in the bundle properties file (see below) through spring custom tags

**Message properties files**

File names must be of the form `messages_<language_code>.properties`. They are located in the ``webResources/`` directory. They contain localized string that are used by dynamically generated pages, through Spring MVC JSP tags, such as `<spring:message code="title" />`, to get the localized text depending on the user’s selected language.

They contain key = value pairs, where key is the unique identifier of the localized text, and value is the actual translation (see example below)

::

    title = Système National de Surveillance des Forêts de la RDC
    subtitle = Ministère de l’Environnement, Conservation de la Nature et Tourisme
    layers = Couches
    redd_plus_activities = Activités REDD+
    redd_plus_activity = Activité REDD+
    deforestation = Déforestation (perte brute)
    degradation = Dégradation de la forêt
    enhancement = Accroissement des stocks de carbone
    conservation = Conservation
    sustainable_management = Gestion durable

The files that make use of the message files are:

* ``index.jsp`` - the portal main page
* ``layers.jsp`` - used to generate the layer definition JSON file

Of the two files above, only ``layers.jsp`` needs to be modified for country specific implementations of the portal.

To refer to the localized string from a jsp page, add a Spring MVC tag, like in the example below

::

  <div id="banner">
    <div id="flag"></div>
        <div id="logos">
            <a href="http://www.infona.gov.py/" id="infona_logo" target="_blank"></a>
            <a href="http://www.seam.gov.py/" id="seam_logo" target="_blank"></a>
            <div id="fapi_logo"></div>
        </div>
    <span id="title"><spring:message code="title" /></span>
  </div>

**loc directory**

The ``webResources/loc/<locale_code>`` directory contains the following three subdirectories:

* documents
* html
* images

The ``documents`` and html directory contains documents and html pages that must be translated for each language. There is currently no standard way provided to handle localization of custom links, so this kind of customization needs the intervention of a programmer of some background in JavaScript. This is a transitory problem that will be addressed in future versions of the portal.

The ``images`` directory contains all the localized legend images. As GeoServer doesn’t handle localization by itself, some image editing needs to be done to generate a translated legend, for example starting from the one dynamically generated by GeoServer.


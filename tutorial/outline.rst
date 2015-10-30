Introduction to the training
============================


Installation of the system
--------------------------

We'll install chef-solo and we'll execute a chef script to install the portal


Introduction to Linux
---------------------

We'll start from a virtual machine with the Ubuntu operating system installed.

We'll learn about the file structure, how to browse the file system and some commands


Introduction to web services
----------------------------

We'll introduce the HTTP protocol on which the OGC services are built

OGC (Open Geospatial Consortium) web services
.............................................

Introduction to the OGC services.

Benefits for standards: interoperability.


WMS and WFS standards.

Download of the specifications.


System architecture
-------------------

While running chef cookbook we can comment on the following topics:

The system is under a process of simplification to facilitate installation and adoption by the countries.

The important thing here is to know the roles that are needed to make the portal work:

- Client-server applicaiton

- Standard Services: publication of data via standard interfaces

- Specific services

- Application javascript consuming these resources

Show overall architecture diagram (architecture0)

Show the use of services over HTTP to illustrate the concept of architecture, of the calls from the client.


Introduction to GeoServer
-------------------------

Installing and running GeoServer

WMS services

Styling maps with SLD (Styled Layer Descriptor)

WMS queries

Styling vector and raster maps

Advanced raster preparation and configuration for GeoServer
	GDAL tools

Sharing data in KML and KMZ formats

WFS protocol


Portal configuration
--------------------

We'll see again the architecture diagam, and explain the benefit we obtained publishing our data using standard protocols. We'll then see how our portal benefits from them and how to customize his parts

Location and structure of the configuration directory

List the relevant files in the configuration directory and describe their structure and the information they contain.

Custom graphics.

Multilanguage support.

Explain the properties files

Setting up a new vector layer

Configuring a new layer group

Prerequisites
.............

* css, json syntax, usage of the wms protocol



Statistics service data model
-----------------------------

How do we add statistics? GeoServer doesn't provide this functionality so we need to have some additional services.

In order to build charts, these sercices need to know what calculations to apply to which layer, and how to present this to the user.
This information is modeled in a database called GeoStore.

We'll see how:

* Create a layer
* Add a layer time instance
* Create statistics
* Create charts
* How to add statistics to the portal

Web services
============

There are many definitions of Web service, as it is a very general concept. In our case, with *web services* we
refer to services available on the network that can be accessed using the HTTP protocol, specifying some parameters
and getting an output as a result. These services are exposed components that perform a specific task and
can be combined to build more complex services.

Unlike monolithic applications in which the components are strongly coupled, the systems based on
web services promote the indipendence of the different elements forming the application. Thus, the components are
services that expose an API for collaborating in the context of the application and can be exchanged
easily with other services that offer the same API.


HTTP
-----

The services that will be consumed are built on the HyperText Transfer Protocol (HTTP).

HTTP interactions occur when a client sends requests to a server. These requests include a header with
descriptive information about the request and optional message body. Among the headers there is the
required method: GET, PUT, PUSH, etc.. It is beyond the scope of this documentation to explain the semantics of the different HTTP methods.
We just note as an example that the web bowsers make use of he GET method to download the resource specified by the web URL.

Continuing with the same example, with the address bar we can download different types of content: HTML pages, files
plain text, XML files, images, videos, etc.. Some of the contents are directly interpreted by the browser while
for others it provides to download of the resource. How does the browser make these decision?

Each response from the server also has a header which specifies the *Content-Type* of the data. The *Content-Type* can be for example:

* text/html

* text/plain

* text/xml

* image/gif

* video/mpeg

* application/zip


Finally, the response includes a code with additional information about what happened on the server when processing the request from the browser. For example the 200 means that the request was succesfully processed. Another very common code also is 404, which
indicates that the resource being requested does not exist on the server.

.. note:: Sample HTTP Exchange (taken from http://www.jmarshall.com/easy/http/)

	 To retrieve the file at the URL

	 ``http://www.somehost.com/path/file.html``

	 first open a socket to the host www.somehost.com, port 80 (use the default port of 80 because none is specified in the URL). Then, send something  like the following through the socket::

	   GET /path/file.html HTTP/1.0
	   From: someuser@jmarshall.com
	   User-Agent: HTTPTool/1.0
	   [blank line here]

	 The server should respond with something like the following, sent back through the same socket::

	   HTTP/1.0 200 OK
	   Date: Fri, 31 Dec 1999 23:59:59 GMT
	   Content-Type: text/html
	   Content-Length: 1354

	   <html>
	   <body>
	   <h1>Happy New Millennium!</h1>
	   (more file contents)
	     .
	     .
	     .
	   </body>
	   </html>


Open Geospatial Consortium (OGC) services
-----------------------------------------

The Open Geospatial Consirtium is a international industrial consortium that joins hundreds of companies, governamental agencies and universities who participate in the creation of public standards.

In the context of the web services, OGC defines the OGC Web Services (OWS), which are standards built over the HTTP protocol that define the content of the various requests and responses, and the relationship between them.

Among the advantage of the use of these standards we can include:

* You can consume the services offered by other providers, independently on how they are implemented.

* There are OpenSource projects that implement these standards thus easily allowing the publication of data with standard interfaces.

* There are many OpenSource software that allow to consume data published using these interface.

Two of the most representative OWS services are Web Map Service (WMS) and Web Feature Services (WFS), that we'll see later.

The WMS standard defines basically three types of requests: *GetCapabilities*, *GetMap*, and *GetFeatureInfo*. The first one is common to all the OWS and returns an XML document that contains information on the layers published by the server, the coordinate system (CRS, Coordinate Reference System) supported, etc.

Example::

	http://www.cartociudad.es/wms/CARTOCIUDAD/CARTOCIUDAD?REQUEST=GetCapabilities

The *GetMap* request obtains a graphical representation of the spatial data server::

	http://www.cartociudad.es/wms/CARTOCIUDAD/CARTOCIUDAD?REQUEST=GetMap&SERVICE=WMS&VERSION=1.3.0&LAYERS=DivisionTerritorial&
		CRS=EPSG:25830&BBOX=718563.200906236,4363954.866694199,735300.5071689372,4377201.249079251&WIDTH=701&
		HEIGHT=555&FORMAT=image/png&STYLES=municipio&TRANSPARENT=TRUE

And finally, with the *GetFeatureInfo* request you can obtain information on a particular location (point) on a map::

	http://www.cartociudad.es/wms/CARTOCIUDAD/CARTOCIUDAD?REQUEST=GetFeatureInfo&SERVICE=WMS&QUERY_LAYERS=CodigoPostal&
		VERSION=1.3.0&INFO_FORMAT=application/vnd.ogc.gml&LAYERS=CodigoPostal&CRS=EPSG:25830&
		BBOX=665656.9561496238,4410190.54853407,690496.231896245,4427113.624503085&WIDTH=706&HEIGHT=481&
		FORMAT=image/png&STYLES=codigo_postal&TRANSPARENT=TRUE&I=475&J=204&FEATURE_COUNT=10000&EXCEPTIONS=XML


The WFS standard instead, doesn't serve graphical representation of the data, but the data itself. It defines the following requests:

* *GetCapabilities*: Like all OWS services, WFS accepts the GetCapabilities request to obtain a list of the layers and other features offered by the service.

* *DescribeFeatureType*: Returns a document with the data structure.

* *GetFeature*: Allows to query the system for data that meet a given criteria.

As an example, we can see which layers are in a WFS service::

	http://www.cartociudad.es/wfs-comunidad/services?request=GetCapabilities&service=WFS

Query the structure of one of them::

	http://www.cartociudad.es/wfs-comunidad/services?request=DescribeFeatureType&service=WFS&VERSION=1.0.0&
		TypeName=app:entidadLocal_&outputformat=text/xml;%20subtype=gml/3.1.1

And actually download some data::

	http://www.cartociudad.es/wfs-comunidad/services?REQUEST=GetFeature&SERVICE=WFS&TYPENAME=app:entidadLocal_&
		NAMESPACE=xmlns%28app=http://www.deegree.org/app%29&VERSION=1.1.0&EXCEPTIONS=XML&MAXFEATURES=10

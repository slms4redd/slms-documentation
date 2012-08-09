.. geoserver.logging:

Logging all requests on the file system
=======================================

The history mode logs all requests into a database. This can put a very significant strain
on the database and can lead to insertion issues as the request table begins to host
millions of records.

As an alternative to the history mode it's possible to enable the auditing logger, which will log 
the details of each request in a file, which is periodically rolled. Secondary applications can
then process these log files and built ad-hoc summaries off line.

Configuration
-------------

The ``monitor.properties`` file can contain the following items to enable and configure file auditing:

#. Go to the ${GEOSERVER_DATA_DIR}/monitoring and open the ``monitor.properties`` then append the following configuration::

     audit.enabled=true
     audit.path=/home/unredd
     audit.roll_limit=20

#. Go to the Map `Map Preview <http://localhost:8080/geoserver/web/?wicket:bookmarkablePage=:org.geoserver.web.demo.MapPreviewPage>`_ and open the `geosolutions:states` layer clicking on the ``OpenLayers`` link.

#. Perform a few times zoom the map.

#. Open the new created log file (named like ``geoserver_audit_yyyymmdd_nn.log``) located at /home/unredd. 

   .. note::

      - **audit.enable**: is used to turn on the logger (it is off by default).
      - **audit.path**: is the directory where the log files will be created.
      - **audit.roll_limit**: is the number of requests logged into a file before rolling happens. 
     
   .. note:: The files are also automatically rolled at the beginning of each day.

Outputs and contents
--------------------

The log directory will contain a number of log files following the ``geoserver_audit_yyyymmdd_nn.log`` 
pattern. The ``nn`` is increased at each roll of the file. The contents of the log directory will look like::

      geoserver_audit_20110811_2.log
      geoserver_audit_20110811_3.log
      geoserver_audit_20110811_4.log
      geoserver_audit_20110811_5.log
      geoserver_audit_20110811_6.log
      geoserver_audit_20110811_7.log
      geoserver_audit_20110811_8.log
	
By default each log file contents will be a xml document looking like the following::
  
	<?xml version="1.0" encoding="UTF-8" ?>
	<Requests>
		<Request id="168">
		   <Service>WMS</Service> 
		   <Version>1.1.1</Version>
		   <Operation>GetMap</Operation> 
		   <SubOperation></SubOperation>
		   <Resources>GeoSolutions:elba-deparea</Resources>
		   <Path>/GeoSolutions/wms</Path>
		   <QueryString>LAYERS=GeoSolutions:elba-deparea&amp;STYLES=&amp;FORMAT=image/png&amp;TILED=true&amp;TILESORIGIN=9.916,42.312&amp;SERVICE=WMS&amp;VERSION=1.1.1&amp;REQUEST=GetMap&amp;EXCEPTIONS=application/vnd.ogc.se_inimage&amp;SRS=EPSG:4326&amp;BBOX=9.58375,42.64425,9.916,42.9765&amp;WIDTH=256&amp;HEIGHT=256</QueryString>
		   <HttpMethod>GET</HttpMethod>
		   <StartTime>2011-08-11T20:19:28.277Z</StartTime> 
		   <EndTime>2011-08-11T20:19:28.29Z</EndTime>
		   <TotalTime>13</TotalTime> 
		   <RemoteAddr>192.168.1.5</RemoteAddr>
		   <RemoteHost>192.168.1.5</RemoteHost>
		   <Host>demo1.geo-solutions.it</Host> 
		   <RemoteUser>admin</RemoteUser>
		   <ResponseStatus>200</ResponseStatus>
		   <ResponseLength>1670</ResponseLength>
		   <ResponseContentType>image/png</ResponseContentType>
		   <Failed>false</Failed>
		</Request>
		...
	</Requests>

Customizing the log contents
----------------------------

The log contents are driven by three FreeMarker templates. We can customize them to have the log file be a csv file for example.

#. On the file system navigate to the GeoServer data directory located at $GEOSERVER_DATA_DIR.

#. In the ``monitoring``apa directory create a new file named ``header.ftl`` (is used once when a new log file is created to form the first few lines of the file). 

#. Open ``header.ftl`` in the text editor of your choice and enter the following content::

	# start time,services,version,operation,url,response content type,total time,response length,error flag
	
#. Create another file named ``content.ftl``.

#. Open ``content.ftl`` in the text editor of your choice and enter the following content::

	${startTime?datetime?iso_utc_ms},${service!""},${owsVersion!""},${operation!""},"${path!""}${queryString!""}",${responseContentType!""},${totalTime},${responseLength?c},<#if error??>failed<#else>success</#if>
	</#escape>
    
#. Create a last file named ``footer.ftl``, and leave its contents empty

#. Run again a few requests, the log files should contain something like the following now::

    # start time,services,version,operation,url,response content type,total time,response lenght,error flag
    2012-06-07T10:37:09.725Z,WMS,1.1.1,GetMap,"/geosolutions/wmsLAYERS=geosolutions:ccounties&STYLES=&FORMAT=image/png&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&SRS=EPSG:4269&BBOX=-106.17254516602,39.489453002927,-105.18378466798,40.054948608395&WIDTH=577&HEIGHT=330",image/png,59,30420,success
    2012-06-07T10:37:10.075Z,WMS,1.1.1,GetMap,"/geosolutions/wmsLAYERS=geosolutions:ccounties&STYLES=&FORMAT=image/png&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&SRS=EPSG:4269&BBOX=-105.84010229493,39.543136352537,-105.34572204591,39.825884155271&WIDTH=577&HEIGHT=330",image/png,45,18692,success
	


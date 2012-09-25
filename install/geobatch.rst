.. module:: unredd.install.stg_geobatch

Deploy and configure staging GeoBatch
=====================================

GeoBatch only needs to be installed in the staging area. 
No batch processing is going to be run in the dissemination system.

GeoBatch configuration files
----------------------------

Geobatch uses some directories for its processing needs. We'll place all of them under ``/var/stg_geobatch/``:

* ``config``: GeoBatch main configuration directory

  * ``config/chartscripts``: Chart script container

* ``input``: Root input directory

  * ``input/ingest``: Ingestion flow input directory
  * ``input/reprocess``: Reprocess flow input directory
  * ``input/publish``: Publish flow input directory

* ``temp``: Root temp directory

  * ``temp/ingest``: Ingestion flow temp dir 
  * ``temp/reprocess``: Reprocess flow temp dir 
  * ``temp/publish``: Publish flow temp dir 


Chart scripts
-------------

The chart scripts need three files:

* ``deforestation_stats.groovy``, the main groovy script to build the chart. An example::

    import groovy.text.SimpleTemplateEngine
    import java.util.Map
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.apache.commons.io.IOUtils
    import it.geosolutions.unredd.geostore.model.UNREDDStatsData
    import it.geosolutions.unredd.geostore.model.UNREDDChartData
    import it.geosolutions.unredd.geostore.UNREDDGeostoreManager
    import it.geosolutions.geostore.services.rest.model.RESTResource
    import it.geosolutions.geostore.services.rest.GeoStoreClient
    import it.geosolutions.geostore.services.rest.model.RESTStoredData
    import it.geosolutions.geostore.core.model.Resource
    import it.geosolutions.geostore.core.model.Attribute

    log = LoggerFactory.getLogger("deforestation_stats.groovy");

    println "_TEST_"
    log.info "_TEST_"

    htmlTemplateFilePath = '/home/unredd/Desktop/pry_workshop_data/groovy_script/deforestation_chart_template.html' // DEBUG
    langFilePath         = '/home/unredd/Desktop/pry_workshop_data/groovy_script/lang.csv'

    csvSeparator = ';'

    // The following lines are used to test the script
    // Comment out them for deploying with GeoBatch
    class Conf {
        def geostore_url
        def geostore_username
        def geostore_password
        def chartscript_name
    }
    def argsMap = [
        configuration: new Conf(
            geostore_url:      "http://127.0.0.1:9191/geostore/rest",
            geostore_username: "admin",
            geostore_password: "admin",
            chartscript_name:  "deforestation_script"
        )
    ];
    //execute(argsMap)
    // Comment out up to here

    def execute(Map argsMap)
    {
        log.info "Starting chart script"

        GeoStoreClient client = createGeoStore(argsMap);
        UNREDDGeostoreManager manager = new UNREDDGeostoreManager(client)
        
        chartScriptName = argsMap.get("configuration").getProperties().get("chartscript_name");
        
        // Load forest change forest data
        forestData = importData(manager, 'forest_mask_stats', true)
        nonForestData = importData(manager, 'forest_mask_stats', false)
        
        // Load localized data
        loc = loadLangData(langFilePath)
        
        def engine = new SimpleTemplateEngine()
        def htmlTemplate = engine.createTemplate(new File(htmlTemplateFilePath));
        //def csvTemplate  = engine.createTemplate(new File(csvTemplateFilePath));
            
        def lastYear  = null;
        def firstYear = null;
        
        deletePreviousChartData(client, chartScriptName);
        
        // Iterate through the languages
        loc.each { langKey, loc ->
            // Create one HTML per row in the forest change stats data
            forestData.each() { featureId, row ->
                try {
                    def years = row.keySet();
                    if (lastYear  == null) lastYear  = years.max()
                    if (firstYear == null) firstYear = years.min()

                    def binding = ['loc': loc, 'forest': row, 'nonForest': nonForestData[featureId], 'lastYear': lastYear, 'firstYear': firstYear]
                    html = htmlTemplate.make(binding)

                    // DEBUG: Save as file
                    //polygonId = deforestationValues[0] as int;
                    //outputFilePath = outputBaseDir + '/' + langKey + '/' + prefix + '/' + prefix + '_' + featureId + '.html'
                    //saveAsFile(html, outputFilePath)
                    //saveAsFile(html, '/Users/sgiaccio/stats/' + featureId + '.html') // DEBUG
                    
                    // Save in GeoStore
                    def resourceName = chartScriptName + "_" + featureId + "_" + langKey
                    id = saveOnGeoStore(client, featureId, resourceName, html.toString(), "deforestation_script", false, langKey, "html")
                    log.info "GeoStore resource saved: Resource Name = " + resourceName + " - ID = " + id
                } catch (Exception e) {
                    log.info("Problem encountered in creating chart for featureId = " + featureId)
                    e.printStackTrace();
                }
            }
            
            // Create csv
            //def csvBinding = ['loc': loc, 'forestChange': forestChangeStatsData]
            //def csv = csvTemplate.make(csvBinding)
            //println csv; // DEBUG
        }
        
        return ["return": []]
    }

    def loadLangData(langFilePath)
    {
        langFile = new File(langFilePath)
        lines = langFile.readLines()
        headerLine = lines.head().split("\t")
        languages = headerLine.tail() // remove first column from first line (it's not a language code nor a label id)
        
        // fill the loc hash map
        loc = [:]
        lines = lines.tail()
        lines.each { row ->
            splitRow = row.split("\t")
            key = splitRow.head()
            splitRow = splitRow.tail()
            splitRow.eachWithIndex { column, i ->
                values = loc[languages[i]]
                if (values == null) {
                    loc[languages[i]] = [:]
                    loc[languages[i]][key] = column
                } else {
                    values[key] = column //.strip()
                }
            }
        }
        
        return loc
    }

    def deletePreviousChartData(client, chartScriptName) {
        UNREDDGeostoreManager manager = new UNREDDGeostoreManager(client)

        // Search all chart data for the given chart script
        List data = manager.searchChartDataByChartScript(chartScriptName)

        // Delete the chart data found
        for (chartData in data)
        {
            def id = chartData.getId();
            log.info("Resource deleted - id = " + id)
            
            client.deleteResource(id);
        }
    }

    def saveOnGeoStore(client, featureId, name, html, chartScriptName, published, language, format)
    {
        // Create the UNREDDChartData object and fill the atributes
        unreddChartData = new UNREDDChartData()
        unreddChartData.setAttribute(UNREDDChartData.Attributes.CHARTSCRIPT, chartScriptName)
        unreddChartData.setAttribute(UNREDDChartData.Attributes.PUBLISHED, published ? "true" : "false")
        unreddChartData.setAttribute(UNREDDChartData.Attributes.FEATUREID, featureId + "")

        // These attributes are not used yet
        //unreddChartData.setAttribute(UNREDDChartData.Attributes.LANGUAGE, language)
        //unreddChartData.setAttribute(UNREDDChartData.Attributes.FORMAT, format)

        // Create the RESTResource and set the name
        RESTResource chartDataRestResource = unreddChartData.createRESTResource()
        chartDataRestResource.setName(name)

        // Set the data to be stored
        RESTStoredData rsd = new RESTStoredData()
        rsd.setData(html)
        chartDataRestResource.setStore(rsd)
        
        // Insert in GeoStore
        int id = client.insert(chartDataRestResource)
        return id
    }

    // Loads the stats data from GeoStore
    def importData(manager, dataId, forest)
    {
        List resources = manager.searchStatsDataByStatsDef2(dataId); // dataId)
        
        Map output = new HashMap()
        
        for (Resource resource : resources)
        {
            data = resource.getData()
            
            int year  = (Float.parseFloat(getAttribute(resource.getAttribute(), UNREDDStatsData.Attributes.YEAR).getValue())).trunc()
            def lines = parseTable(data.getData(), forest)
            lines.each { id, line ->
                temp = output[id]
                if (temp == null) {
                    output[id] = new TreeMap()
                    output[id][year] = line
                } else {
                    temp[year] = line
                }
            }
        }
        
        return output
    }


    def fillNullRows(map) {
        print " ----- " + map.get(map.keySet().min());
        return null;
    }

    // Parses a CSV table - returns a HashMap where the key is the polygon ID (first column in the CSV)
    // and the value is the full parsed row (stored as an array)
    def parseTable(table, forest)
    {
        HashMap lines = new HashMap()
        
        table.eachLine { line ->
            parsedArr = []
            arr = line.tokenize(csvSeparator)
            
            if ("0".equals(arr[1]) && forest || "1".equals(arr[1]) && !forest)
                return false // skip loop
            
            polygonId = Integer.parseInt(arr[0])
            
            // Values for each administrative regions are split in two rows (forest and non-forest) - join them together again
            for (i in 2..<arr.size) // first element in array is the polygon id, don't need it
            {
                //println 'i = ' + i
                //println 'Double.parseDouble(arr[i]) = ' + Double.parseDouble(arr[i])
                //println 'parsedArr[i - 2] = ' + parsedArr[i - 2]
                parsedArr[i - 2] = Double.parseDouble(arr[i])
            }
            
            //println 'parsedArr = ' + parsedArr
            lines.put(polygonId, parsedArr)
        }
        
        //print lines
        return lines
    }

    def getAttribute(List attributeList, attribute)
    {
        for (Attribute attr : attributeList) {
            if (attr.getName().equals(attribute.getName())) {
                return attr
            }
        }
        
        return null
    }

    def saveAsFile(html, outputFilePath)
    {
        log.info("Saving output to file: " + outputFilePath);
        out = new File(outputFilePath)
        out.write(html.toString(), "UTF-8")
    }

    GeoStoreClient createGeoStore(Map argsMap) {
        Map props = argsMap.get("configuration").getProperties();

        log.info("geostore url: " + props.get("geostore_url"));

        String gurl  = props.get("geostore_url");
        String guser = props.get("geostore_username");
        String gpw   = props.get("geostore_password");

        GeoStoreClient client = new GeoStoreClient();
        client.setGeostoreRestUrl(gurl);
        client.setUsername(guser);
        client.setPassword(gpw);
        return client;
    }

* ``deforestation_chart_template.html``, the html template used by the groovy script. For example:

  .. code-block:: html

    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
    <html>
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Chart</title>
        
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="/portal/js/highcharts/highcharts.js"></script>

        <script type="text/javascript">
          var gup = function (name) {
            name = name.replace(/[\\[]/,"\\\\\\[").replace(/[\\]]/,"\\\\\\]");
            var regexS = "[\\?&]"+name+"=([^&#]*)";
            var regex = new RegExp( regexS );
            var results = regex.exec( window.location.href );
            if (results == null)
              return "";
            else
              return decodeURIComponent(results[1]);
          }

          \$(document).ready(function() {
            var colors = Highcharts.getOptions().colors;

            var fccColors = {
              atlanticForest: "#005700",
              chacoWoodlands: "#01E038",
              nonForest: "#FFFF9C",
              water: "#3938FE"
            };

            \$('#title').text(gup('name').toLowerCase());

            chart1 = new Highcharts.Chart({
              chart: {
                renderTo: 'container1',
                defaultSeriesType: 'line'
              },
              title: {
                text: '<%=  loc["deforestation"] %>',
                x: -20 //center
              },
              subtitle: {
                text: '<%= firstYear + "-" + lastYear %>',
                x: -20
              },
              xAxis: {
                categories: [
                  <% forest.keySet().eachWithIndex { year, i -> %>
                  '<%= year  %>'
                  <%   if (i + 1 < forest.size()) print ',' %>
                  <% } %>
                ],
                labels: {
                  rotation: -45,
                  align: 'right',
                  style: {
                    font: 'normal 10px Verdana, sans-serif'
                  }
                }
              },
              yAxis: {
                title: {
                  text: '<%= loc["area"] %> (Km<sup>2</sup>)'
                },
                plotLines: [{
                  value: 0,
                  width: 1,
                  color: '#808080'
                }]
              },
              tooltip: {
                formatter: function() {
                  return this.x +': '+ this.y.toFixed(0) +' Km<sup>2</sup>';
                }
              },
              legend: {
                enabled: false,
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
              },
              series: [{
                name: '<%= loc["deforestation"] %>',
                data: [
                  <% forest.eachWithIndex() { year, values, i -> %>
                  <%=  (values[0]) / 1E6 %>
                  <%   if (i + 1 < forest.size()) print ',' %>
                  <% } %>
                ],
                color: fccColors.atlanticForest
              }],
              credits: {
                enabled: false
              }
            });

          });
        </script>
        
        <style type="text/css">
          body
          {
            font: 100% "Trebuchet MS", sans-serif;
            margin: 0;
          }
          
          #top
          {
            position: relative;
            width: 800px;
          }
          
          #title
          {
            font-size: 120%;
            width: 700px;
            top: 10px;
            text-align: center;
            position: relative;
            margin: auto;
            text-transform:capitalize;
          }
                
          #container1
          {
            /*border: 1px dashed grey;*/
            width: 800px;
            height: 400px;
            margin: 0 auto;
            position: absolute;
            left: 0;
            top: 50px;
          }
                
          .print {
            display:block;
            width: 32px;
            height: 32px;
            float: left;
            /*padding:5px 0 0px 20px;
            color:#8e8e8e;*/
          }
          
          a img {
            border: none;
          }
          
          #hover_text,#disclaimer
          {
            width: 800px;
            height: 20px;
            font-size: 80%;
            margin: 0 auto;
            position: absolute;
            text-align: center;
          }
          
          #hover_text
          {
            top: 480px;
          }
          
          #disclaimer
          {
            top: 495px;
          }
          
          @media print {
            #print_link
            {
              display: none;
            }
            
            #hover_text
            {
              display: none;
            }
          }
        </style>
        
      </head>
      <body>
        <div id="top">
          <div id="print_link">
            <a href="#print" title="<%= loc.print_this_chart %>" class="print" onClick="window.print();return false;"><img src="http://www.rdc-snsf.org/images/Printer.png"></a>
          </div>
          <div id="title"></div>
        </div>
        
        <div id="container1"></div>
        
        <div id="hover_text"><%= loc.hover_text %></div>
      </body>
    </html>


* ``lang.csv``, contains interface strings translated to the needed languages. Note this is a `TAB` separated value file. For example::

    key              es                                                      en
    deforestation    Deforestacion                                           Deforestation
    Primary          Primaria                                                Primary
    area             Superficie                                              Area
    print_this_chart Imprimir esta grafica                                   Print this chart
    hover_text       Situar el raton sobre las graficas para ver los valores Please place the mouse pointer over the charts to see values


Place all these files in ``/var/stg_geobatch/config/chartscripts``, and change their permisions as follows::

  cd /var/stg_geobatch/config/chartscripts
  chown -R tomcat6:tomcat6 .
  chmod ug+x deforestation_stats.groovy


Environment settings
--------------------
  
This is the ``bin/setenv.sh`` file:

.. code-block:: sh

  # Application specific environment
  GEOBATCH_CONFIG_DIR=/var/$SERVICE/config
  GEOBATCH_TEMP_DIR=/var/$SERVICE/temp
  UNREDD_OVR=$GEOBATCH_CONFIG_DIR/unredd-ovr.properties

  # Java options
  JAVA_OPTS=-server -Xms2048m -Xmx2048m -DGEOBATCH_CONFIG_DIR=$GEOBATCH_CONFIG_DIR -DGEOBATCH_TEMP_DIR=$GEOBATCH_TEMP_DIR -Dunredd-ovr=$UNREDD_OVR -Duser.timezone=GMT

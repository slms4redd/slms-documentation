============================
Reprocess flow configuration
============================

Reprocess flow
==============

Here's a semple flow definition for the data reprocessing:
::

   <FlowConfiguration>
       <id>reprocessFlow</id>
       <name>UNREDD reprocessing flow</name>
       <description>UNREDD - Reprocess layers, stats and charts on the staging system.</description>

Editable part: flow temp dir; refer to geobatch doc::

       <overrideTempDir>/var/geobatch/drc/temp</overrideTempDir>

::

       <autorun>true</autorun>
       <EventGeneratorConfiguration>
               <id>reprocessEGC</id>
               <serviceID>fsEventGeneratorService</serviceID>

Editable part: this is the directory that GeoBatch monitors for new files::

               <watchDirectory>/var/geobatch/drc/input/reprocess</watchDirectory>

::

               <wildCard>*.xml</wildCard>

               <osType>OS_UNDEFINED</osType>
               <eventType>FILE_ADDED</eventType>
       </EventGeneratorConfiguration>

       <EventConsumerConfiguration>
               <id>reprocessECC</id>

               <listenerId>ReprocessConsumerLogger0</listenerId>
               <listenerId>ReprocessCumulator0</listenerId>
               <listenerId>ReprocessStatusActionLogger0</listenerId>

               <performBackup>false</performBackup>

               <!-- First and only Action configuration  -->
               <ReprocessConfiguration>
                       <serviceID>ReprocessGeneratorService</serviceID>                

                       <id>ReprocessActionConfiguration</id>
                       <name>Reprocess Action</name>
                       <description>Single-step action for reprocessing layers, stats, charts</description>

                       <listenerId>ReprocessConsumerLogger0</listenerId>
                       <listenerId>ReprocessCumulator0</listenerId>
                       <listenerId>ReprocessStatusActionLogger0</listenerId>

                       <failIgnored>false</failIgnored>
                       <!--<overrideConfigDir>reprocess/</overrideConfigDir>-->

Editable part: action config dir; refer to geobatch doc::

                       <overrideConfigDir>/var/geobatch/drc/config/reprocessFlow</overrideConfigDir>

Editable part: GeoStore config::

                      <geoStoreConfig>
                          <url>http://localhost:9094/geostore/rest</url>
                          <username>admin</username>
                          <password>secret!</password>
                       </geoStoreConfig>

Editable part: PostGis config for vectorial layer features::

                       <postGisConfig>
                           <host>localhost</host>
                           <port>5432</port>
                           <database>drc</database>
                           <schema>public</schema>
                           <username>dummy</username>
                           <password>dummy</password>
                       </postGisConfig>

The final flow configuration lines can be left as they are ::
                       
                 <rasterizeConfig>
                      <executable>gdal_rasterize</executable>
                      <taskExecutorXslFileName>taskexec.xsl</taskExecutorXslFileName>
                      <freeMarkerTemplate>freemarkertemplate.xml</freeMarkerTemplate>
                 </rasterizeConfig>


                 <overviewsEmbedderConfiguration>
                      <dirty>false</dirty>
                      <listenerConfigurations/>
                      <failIgnored>false</failIgnored>
                      <JAICapacity>0</JAICapacity>
                      <compressionRatio>0.75</compressionRatio>
                      <compressionScheme>LZW</compressionScheme>
                      <downsampleStep>0</downsampleStep>
                      <numSteps>42</numSteps>
                      <tileH>512</tileH>
                      <tileW>512</tileW>
                      <wildcardString>*.*</wildcardString>
                      <logNotification>true</logNotification>
                      <interp>0</interp>
                 </overviewsEmbedderConfiguration>
            </ReprocessConfiguration>


       </EventConsumerConfiguration>

    <ListenerConfigurations>
       <StatusProgressListener>
           <serviceID>statusListenerService</serviceID>
           <id>ReprocessStatusActionLogger0</id>
       </StatusProgressListener>
       
       <LoggingProgressListener>
           <serviceID>loggingListenerService</serviceID>
           <id>ReprocessActionLogger0</id>

           <loggerName>ReprocessActionLogger0</loggerName>
       </LoggingProgressListener>

       <LoggingProgressListener>
           <serviceID>loggingListenerService</serviceID>
           <id>ReprocessConsumerLogger0</id>

           <loggerName>ReprocessConsumerLogger0</loggerName>
       </LoggingProgressListener>

       <CumulatingProgressListener>
           <serviceID>cumulatingListenerService</serviceID>
           <id>ReprocessCumulator0</id>
       </CumulatingProgressListener>
    </ListenerConfigurations>
   
</FlowConfiguration>

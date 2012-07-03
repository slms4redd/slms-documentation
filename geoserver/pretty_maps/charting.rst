.. _geoserver.dynamic_symb:

Charting    
--------


This section shows one GeoServer advanced styling characteristic. 

#.  To print dynamic charts over the map add a new style called :guilabel:`statespiepss`.

   .. figure:: img/dyn_symb1.jpg
      :width: 600
 		  
      Creating a new Dynamic Style

#. In the :guilabel:`SLD Editor` enter the following XML:

   .. code-block:: xml
   
			<?xml version="1.0" encoding="ISO-8859-1"?>
			<StyledLayerDescriptor version="1.0.0"
			  xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"
			  xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
			  xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			  <NamedLayer>
			    <Name></Name>
			    <UserStyle>
			      <Name>Pie charts</Name>
			      <FeatureTypeStyle>
			        <Rule>
			          <PolygonSymbolizer>
			            <Fill>
			              <CssParameter name="fill">#AAAAAA</CssParameter>
			            </Fill>
			            <Stroke />
			          </PolygonSymbolizer>
			        </Rule>
			      </FeatureTypeStyle>
			      <FeatureTypeStyle>
			        <Rule>
			          <PointSymbolizer>
			            <Graphic>
			              <ExternalGraphic>
			                <OnlineResource
			                  xlink:href="http://chart?cht=p&amp;chd=t:${100 * MALE / PERSONS},${100 * FEMALE / PERSONS}&amp;chf=bg,s,FFFFFF00" />
			                <Format>application/chart</Format>
			              </ExternalGraphic>
			              <Size>
			                <ogc:Add>
			                  <ogc:Literal>20</ogc:Literal>
			                  <ogc:Mul>
			                    <ogc:Div>
			                      <ogc:PropertyName>PERSONS</ogc:PropertyName>
			                      <ogc:Literal>20000000.0</ogc:Literal>
			                    </ogc:Div>
			                    <ogc:Literal>60</ogc:Literal>
			                  </ogc:Mul>
			                </ogc:Add>
			              </Size>
			            </Graphic>
			          </PointSymbolizer>
			        </Rule>
			      </FeatureTypeStyle>
			    </UserStyle>
			  </NamedLayer>
			</StyledLayerDescriptor>

   .. note:: The thing to note is the ``<ExternalGraphic>`` element. We have an expression using the featureType attributes to draw the Pie Chart dynamically. The URL is following the Google Chart API syntax, but the chart is generated internally in GeoServer.

#. Modify the default style of the :guilabel:`states` layer to the newly created :guilabel:`statespiepss`.

   .. figure:: img/dyn_symb2.jpg
      :width: 800
 		  
      Changing the default style of the states layer

#. Use the **Layer Preview** to preview the new style.
   
   .. figure:: img/dyn_symb3.jpg

      Previewing the states layer with the statespiepss applied


#. Finally please restore the previous style `states`.
.. module:: unredd.install.admin

Deploy and configure Administration interface
=============================================

Simply copy the application file ``unredd-admin.war`` to the tomcat webapps directory. For example::

  sudo cp unredd-admin.war /var/tomcat/admin/webapps/admin.war

This will install and run the admin interface, accessible in:

  http://localhost/admin/


Application context
-------------------

Edit the file ``/var/tomcat/admin/webapps/admin/WEB-INF/unredd_admin_applicationContext.xml`` and set the GeoStore connection parameters (``geostoreRestUrl``, ``geostoreUsername`` and ``geostorePassword``).

This is an excerpt of application context file:

.. code-block:: xml

    <bean id="geostoreRestUrl" class="java.lang.String">
        <constructor-arg type="String" value="http://localhost/stg_geostore/rest"/>
    </bean>
    <bean id="geostoreUsername" class="java.lang.String">
        <constructor-arg type="String" value="admin"/>
    </bean>
    <bean id="geostorePassword" class="java.lang.String">
        <constructor-arg type="String" value="admin"/>
    </bean>

Use the same password set in GeoStore configuration.

In the same file, set the path to the staging GeoBatch input directory. For example:

.. code-block:: xml

    <bean id="configure" class="org.fao.unredd.Configure">
        <property name="geobatchFlowSaveDir" value="/var/stg_geobatch/input" />
    </bean>


Environment settings
--------------------
  
This is the ``bin/setenv.sh`` file:

.. code-block:: sh

   SERVICE=admin
   
   # Java options
   JAVA_OPTS="-server -Duser.timezone=GMT"

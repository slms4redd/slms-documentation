.. module:: unredd.install.portal

Deploy and configure dissemination Portal
=========================================

Simply copy the application file ``unredd-portal.war`` to the tomcat webapps directory. For example::

  sudo cp unredd-portal.war /var/tomcat/portal/webapps/portal.war

This will install and run the portal with the default (sample) configuration, accessible in:

  http://localhost/portal/


Portal configuration directory
------------------------------

To customize the portal for a new country, you need to create a new ``PORTAL_CONFIG_DIR``. An example directory is shipped with the portal application, under ``WEB-INF/default_config``. Use it as an example to build your own configuration directory.

Further details on customization are found in :ref:`unredd-portal-customize`.


Portal properties file
----------------------

The file ``$PORTAL_CONFIG_DIR/portal.properties`` contains some important parameters, such as the geostore location, or the WPS custom stats service.
Please review its contents so the parameters match the server setup. More details in :ref:`unredd-portal-customize`.


Environment settings
--------------------

To inform the application about the country specific data location,
set a Java System Property called :file:`PORTAL_CONFIG_DIR`.

This property can be placed in the ``JAVA_OPTS`` environment variable,
using the ``-D`` option. For example, in tomcat's :file:`setenv.sh`, add:

.. code-block:: sh

   SERVICE=portal

   # Application specific environment
   PORTAL_CONFIG_DIR=/var/$SERVICE/

   # Java options
   JAVA_OPTS="-server -DMINIFIED_JS=true -DPORTAL_CONFIG_DIR=$PORTAL_CONFIG_DIR -Duser.timezone=GMT"

   
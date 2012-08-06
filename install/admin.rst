.. module:: unredd.install.admin

Deploy and configure admin portal (S only)
==========================================

:file:`/var/tomcat/stg_frontend`

* Rename the war from ``unredd-admin.war`` to ``stg_frontend.war``.
* Upload ``stg_frontend.war`` into ``/var/tomcat/stg_frontend/webapps``.
* Edit the file ``unredd_admin_applicationContext.xml``:

  * Set ``geostoreRestUrl`` constructor-arg value to
     ``http://localhost:8200/stg_geostore/rest``
  * Set ``geobatchFlowSaveDir`` property value in the configure bean to
    ``/var/geobatch/input``

Chart scripts
-------------

* Create the directory ``/var/geobatch/config/chartscripts``.
* Upload the sample script file ``deforestation_stats.groovy``, the
  ``lang.csv`` file and the html template file
  ``deforestation_chart_template.html`` into
  ``/var/geobatch/config/chartscripts``.

::

  chown -R tomcat:tomcat chartscripts/
  chmod ug+x deforestation_stats.groovy

.. module:: unredd.install.stg_geostore

Deploy and configure staging and dissemination GeoStore
=======================================================

Simply copy the application file ``geobatch.war`` to the tomcat webapps directory. For example::

  sudo cp geostore.war /var/tomcat/stg_geostore/webapps/stg_geostore.war

Or::

  sudo cp geostore.war /var/tomcat/diss_geostore/webapps/diss_geostore.war

This will install and run geoserver instances, accessible in:

  http://localhost/stg_geostore/

Or:

  http://localhost/diss_geostore/

Initialize PostgreSQL database
------------------------------

Download the schema creation sql:

  wget https://raw.github.com/geosolutions-it/geostore/1.0.1/doc/sql/002_create_schema_postgres.sql

And run it::

  psql -U stg_geostore -f 002_create_schema_postgres.sql stg_geostore

Or::

  psql -U diss_geostore -f 002_create_schema_postgres.sql diss_geostore



GeoStore configuration files
----------------------------

Three configuration files are needed:

* The *datasource-ovr* file, with the database connection parameters and the path for the following files,
* The *init_users* file, indicating a "user" and an "admin", and their corresponding passwords,
* The *init_categories* file, used to initialize GeoStore with the UNREDD categories.

The following samples show the contents of these files for the staging area (*stg_geostore*).
File contents for the dissemination area (*diss_geostore*) are almost the same, just replacing the instance name from ``stg_geostore`` to ``diss_geostore``.


*datasource-ovr* file
.....................

Create the file ``/var/stg_geostore/geostore-datasource-ovr.properties`` with this content::

  ### DB SETUP
  geostoreDataSource.driverClassName=org.postgresql.Driver
  geostoreDataSource.url=jdbc:postgresql://localhost:5432/stg_geostore
  geostoreDataSource.username=stg_geostore
  geostoreDataSource.password=stg_geostore
  geostoreVendorAdapter.databasePlatform=org.hibernate.dialect.PostgreSQLDialect
  geostoreEntityManagerFactory.jpaPropertyMap[hibernate.hbm2ddl.auto]=validate
  geostoreEntityManagerFactory.jpaPropertyMap[hibernate.default_schema]=public
  geostoreVendorAdapter.generateDdl=true
  geostoreVendorAdapter.showSql=false

  ### USERS INITIALIZAZITION  
  geostoreInitializer.userListInitFile=file://var/stg_geostore/init_users.xml

  ### CATEGORIES INITIALIZATION
  geostoreInitializer.categoryListInitFile=file://var/stg_geostore/init_categories.xml


*init_users* file
.................

This configuration will be used only at initialization time, when the GeoStore database is empty.

Create the file ``/var/stg_geostore/init_users.xml`` file:

.. code-block:: xml

  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
  <InitUserList>
    <User>
        <name>admin</name>
        <newPassword>admin</newPassword>
        <role>ADMIN</role>
    </User>
    <User>
        <name>user</name>
        <newPassword>user</newPassword>
        <role>USER</role>
    </User>
  </InitUserList>

Change the passwords at will.


*init_categories* file
......................

This configuration will be used only at initialization time, when the GeoStore database is empty.

Create the file ``/var/stg_geostore/init_categories.xml``:

.. code-block:: xml

  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
  <CategoryList>
    <Category>
        <name>Layer</name>
    </Category>
    <Category>
        <name>LayerUpdate</name>
    </Category>
    <Category>
        <name>StatsDef</name>
    </Category>
    <Category>
        <name>StatsData</name>
    </Category>
    <Category>
        <name>ChartScript</name>
    </Category>
    <Category>
        <name>ChartData</name>
    </Category>
    <Category>
        <name>Feedback</name>
    </Category>
    <Category>
        <name>Report</name>
    </Category>
  </CategoryList>

More details on the GeoStore data model for UNREDD: :ref:`unredd-geostore`.


Environment settings
--------------------

Check the content of the ``setenv.sh`` files:

.. code-block:: sh

  SERVICE=stg_geostore # OR diss_geostore

  # Application specific environment
  GEOSTORE_OVR_FILE=file:/var/$SERVICE/geostore-datasource-ovr.properties

  # Java options
  JAVA_OPTS="-server -Xms512m -Xmx512m -Dgeostore-ovr=$GEOSTORE_OVR_FILE -Duser.timezone=GMT"

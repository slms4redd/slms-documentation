.. module:: unredd.install.stg_geostore

Deploy and configure staging GeoStore
=====================================

:file:`/var/tomcat/stg_geostore`

Use GeoStore v1.0.1.

Create a catalina base as described in the "installing tomcat" section.

Create the file bin/setenv.sh ::

  INSTANCE_NAME=stg_geostore
  CATALINA_BASE=/var/tomcat/$INSTANCE_NAME

  GEOSTORE_OVR_FILE=file:$CATALINA_BASE/conf/geostore-ovr.properties
  JAVA_OPTS="-server -Xms512m -Xmx512m -Dgeostore-ovr=$GEOSTORE_OVR_FILE -Duser.timezone=GMT"

Create the file /conf/geostore-ovr.properties ::

   
  ### DB SETUP
  
  geostoreDataSource.driverClassName=org.postgresql.Driver
  geostoreDataSource.url=jdbc:postgresql://localhost:5432/geostore
  geostoreDataSource.username=geostore
  geostoreDataSource.password=geostore
  geostoreVendorAdapter.databasePlatform=org.hibernate.dialect.PostgreSQLDialect
  geostoreEntityManagerFactory.jpaPropertyMap[hibernate.hbm2ddl.auto]=validate
  geostoreEntityManagerFactory.jpaPropertyMap[hibernate.default_schema]=geostore
  geostoreVendorAdapter.generateDdl=true
  geostoreVendorAdapter.showSql=false

  ### USERS INITIALIZAZITION
  
  geostoreInitializer.userListInitFile=file://var/tomcat/stg_geostore/conf/geostore_init_files.xml

  ### CATEGORIES INITIALIZATION

  geostoreInitializer.categoryListInitFile=file://var/tomcat/stg_geostore/conf/geostore_init_categories.xml
  

Create the files for users and categories initialization.
These files will be used only if no related entities already exist in the DB.

This is the conf/geostore_init_categories.xml file ::

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
  </CategoryList>


This is the conf/geostore_init_users.xml file ::

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

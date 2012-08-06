.. module:: unredd.install.postgis

Setup PostGIS (S+D)
===================

* stg_geostore (stg_geostore / )
* stg_geoserver (stg_geoserver / )
* diss_geostore (diss_geostore / )
* diss_geoserver (diss_geoserver / )


Create users
------------

stg_geostore::

  postgres=# CREATE USER stg_geostore LOGIN PASSWORD '------' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
  CREATE ROLE
  ALTER USER stg_geostore WITH PASSWORD 'Unr3dd';

diss_geostore::

  postgres=# CREATE USER diss_geostore LOGIN PASSWORD '------' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
  CREATE ROLE
  ALTER USER diss_geostore WITH PASSWORD 'Unr3dd';
  
stg_geoserver::

  postgres=# CREATE USER stg_geoserver LOGIN PASSWORD '------' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
  CREATE ROLE
  ALTER USER stg_geoserver WITH PASSWORD 'Unr3dd';

diss_geoserver::

  postgres=# CREATE USER diss_geoserver LOGIN PASSWORD '------' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
  CREATE ROLE
  ALTER USER diss_geoserver WITH PASSWORD 'Unr3dd';


Create databases
----------------

stg_geostore::

  createdb -O stg_geostore stg_geostore

diss_geostore::

  createdb -O diss_geostore diss_geostore

stg_geoserver::

  createdb -O stg_geoserver -T template_postgis stg_geoserver
  psql stg_geoserver
    stg_geoserver=# GRANT ALL ON geometry_columns TO stg_geoserver;

diss_geoserver::

  createdb -O diss_geoserver -T template_postgis diss_geoserver
  psql diss_geoserver
    diss_geoserver=# GRANT ALL ON geometry_columns TO diss_geoserver;


Create GeoStore DB schemas
--------------------------

**TODO** Review this.

::

  psql -U drc -f geostore.ddl drc_geostore_staging
  psql -U drc -f geostore.ddl drc_geostore_diss


Configure PostgreSQL access
---------------------------

Configuration file is in ``/var/lib/pgsql/data/pg_hba.conf``::

   # TYPE  DATABASE  USER     CIDR-ADDRESS         METHOD
   # "local" is for Unix domain socket connections only
   local all      drc                              md5
   local all      all                              ident sameuser
   # IPv4 local connections:
   host  all      drc      127.0.0.1/32         md5
   host  all      all      127.0.0.1/32         ident sameuser
   # IPv6 local connections:
   host  all      all      ::1/128              ident sameuser

Autostart
---------

Postgres does not start automatically by default. Edit the file
``/etc/init.d/postgresql`` and change the line::

  # chkconfig: - 64 36

to::

  # chkconfig: 345 64 36

Then issue the command::

  chkconfig postgresql reset

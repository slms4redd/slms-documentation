.. module:: unredd.install.system_packages

Software prerequisites
======================

The system needs some base applications: Oracle JDK 6, Tomcat 6, Apache 2 and PostGIS 1.5 on PostgreSQL 9.1.


JDK 6
-----

Download the latest Java SE 6 JDK from Oracle site:
http://www.oracle.com/technetwork/java/javase/downloads/index.html

The file will be named, for example, ``jdk-6u35-linux-i586.bin`` for 32 bit Linux systems.

Copy the file to ``/usr/lib/jvm``, make it executable, and run it as root::

  chmod +x jdk-6u35-linux-i586.bin  
  sudo mv jdk-6u35-linux-i586.bin /usr/lib/jvm
  cd /usr/lib/jvm
  sudo ./jdk-6u35-linux-i586.bin

The JDK will be installed under ``jdk1.6.0_xx`` directory.

Make a symbolic link to this installation. From ``/usr/lib/jvm``::

  sudo ln -s jdk1.6.0_35 default-java

Make it the default java alternative:

  sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.6.0_35/bin/java" 1
  sudo update-alternatives --config java

Check the java version::

  java -version

Should read::

  java version "1.6.0_35"
  Java(TM) SE Runtime Environment (build 1.6.0_35-b10)
  Java HotSpot(TM) Client VM (build 20.10-b01, mixed mode, sharing)


.. _unredd-install-tomcat6:

Tomcat 6
--------

Download latest tomcat 6 from:

  http://tomcat.apache.org/download-60.cgi

Select the core binary distribution. The file will be named, for example, ``apache-tomcat-6.0.35.tar.gz``.

As superuser, move the file to ``/opt/`` and uncompress it. Make a simpler ``tomcat`` link, so updates are easier in the future:
  
  sudo mv apache-tomcat-6.0.35.tar.gz /opt
  cd /opt
  sudo tar -xvf apache-tomcat-6.0.35.tar.gz
  sudo ln -s apache-tomcat-6.0.35 tomcat

From this binaries, we will be configuring and running several tomcat instances. This setup is explained in :ref:`unredd-install-tomcat_instances`.


Apache 2
--------

Apache HTTP server will be used to redirect the different tomcat applications to accessible URLs under the standard HTTP port (80). This mapping will use the proxy_ajp Apache extension.

In Ubuntu systems, we can use the package managed Apache. Install it with apt-get command::

  sudo apt-get install apache2

Enable the proxy and proxy_ajp modules:

  sudo a2enmod proxy proxy_ajp

Restart the server:

  sudo service apache2 restart

Accessing http://localhost should display an **It works!** message.


PostGIS
-------

In Ubuntu, use the package manager to install PostgreSQL 9.1 and other prerequisites needed for PostGIS building::

  sudo apt-get install build-essential postgresql-9.1 postgresql-server-dev-9.1 libxml2-dev libgeos-dev proj

But, *don't use apt-get to install PostGIS*, as it will install 2.0 version, and we want to stick to the earlier 1.5 branch. We will build PostGIS from sources.

Download the latest PostGIS 1.5 stable source code from:

  http://postgis.refractions.net/download/

Then, uncompress, build and install::

  tar xfvz postgis-1.5.5.tar.gz
  cd postgis-1.5.5
  ./configure
  make
  sudo make install
  cd doc/
  sudo make comments-install
  cd loader/
  make shp2pgsql
  sudo make install
  sudo ln /usr/lib/postgresql/9.1/bin/shp2pgsql /usr/bin/shp2pgsql

Finally, create a postgis template, useful to create spatially enabled databases from it::

  sudo -u postgres createdb template_postgis
  sudo -u postgres psql -d template_postgis -c "UPDATE pg_database SET datistemplate=true WHERE datname='template_postgis'"
  sudo -u postgres psql -d template_postgis -f /usr/share/postgresql/9.1/contrib/postgis-1.5/postgis.sql
  sudo -u postgres psql -d template_postgis -f /usr/share/postgresql/9.1/contrib/postgis-1.5/spatial_ref_sys.sql
  sudo -u postgres psql -d template_postgis -f /usr/share/postgresql/9.1/contrib/postgis-1.5/postgis_comments.sql

.. note:: References:

   http://trac.osgeo.org/postgis/wiki/UsersWikiPostGIS15Ubuntu1110src

   http://postgis.refractions.net/documentation/manual-1.5/ch02.html

You will need these PostGIS databases:

stg_geostore
   DB for GeoStore webapp on the staging area.
stg_geoserver 
   DB for GeoServer vector layers on the staging area.
diss_geostore 
   DB for GeoStore webapp on the dissemination system.
diss_geoserver
   DB for GeoServer vector layers on the dissemination system.


Create users
............

Different users will be used for the various databases.

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
................

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
..........................

**TODO** Review this.

::

  psql -U drc -f geostore.ddl drc_geostore_staging
  psql -U drc -f geostore.ddl drc_geostore_diss


Configure PostgreSQL access
...........................

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
.........

Postgres does not start automatically by default. Edit the file
``/etc/init.d/postgresql`` and change the line::

  # chkconfig: - 64 36

to::

  # chkconfig: 345 64 36

Then issue the command::

  chkconfig postgresql reset

.. module:: unredd.install.gdal


GDAL
----

There are two alternatives to install GDAL. The first one, for Ubuntu based systems, uses the UbuntuGIS packages. This method manages all the needed dependencies, and provides installers for other Open Source GIS applications such as Grass, Mapserver, PostGIS, or Quantum GIS.

The second alternative is to manually buildg and install from the GDAL sources, which allows more control over the optional modules, the GDAL version, and the binaries location.


A. Using UbuntuGIS repository
.............................

Add the ubuntugis-unstable repository, and update packages::

  sudo add-apt-repository ppa:ubuntugis/ubuntugis-unstable
  sudo apt-get update

Install gdal binaries and python utilities::

  sudo apt-get install gdal-bin python-gdal

Check the version::

  gdalinfo --version


B. Building from source
.......................

We’ll build and install it from the sources::

  wget http://download.osgeo.org/gdal/gdal-1.8.1.tar.gz

Using an unprivileged account, untar the tar.gz
and enter into the created ``gdal`` dir. Then, build::

  ./autogen.sh
  ./configure  --with-python
  make

Get root privs and then::

  make install
  
In order to use python-gdal libs, you have to issue::

  export PYTHONPATH=/usr/local/lib64/python2.4/site-packages/
  export LD_LIBRARY_PATH=/usr/local/lib/ 

before running python scripts (e.g. ``gdal_merge.py``).
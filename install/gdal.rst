.. module:: unredd.install.gdal

Installing GDAL (S+D)
=====================

There are two alternatives to install GDAL. The first one, for Ubuntu based systems, uses the UbuntuGIS packages. This method manages all the needed dependencies, and provides installers for other Open Source GIS applications such as Grass, Mapserver, PostGIS, or Quantum GIS.

The second alternative is to manually buildg and install from the GDAL sources, which allows more control over the optional modules, the GDAL version, and the binaries location.


Using UbuntuGIS repository
--------------------------

Add the ubuntugis-unstable repository, and update packages::

  sudo add-apt-repository ppa:ubuntugis/ubuntugis-unstable
  sudo apt-get update

Install gdal binaries and python utilities::

  sudo apt-get install gdal-bin python-gdal

Check the version::

  gdalinfo --version


Building from source
--------------------

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


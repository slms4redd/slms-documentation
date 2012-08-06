.. module:: unredd.install.gdal

Installing GDAL (S+D)
=====================

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

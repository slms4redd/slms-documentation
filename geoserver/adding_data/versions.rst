.. module:: geoserver.versions
   :synopsis: Learn how to migrating a GeoServer Data Directory between different versions.

.. _geoserver.versions:

Migrating a Data Directory between different versions
=====================================================


Generally ther should no be problem upgrading data directories to a newer version of GeoServer (i.e. from 2.0.0 to 2.0.2 and vice versa, or from 1.6.x to 1.7.x and vice versa).


Migrating between GeoServer 2.1.x and 2.2.x
-------------------------------------------

The security changes that ship with GeoServer 2.2 require modifications to the ``security`` directory of the 
GeoServer data directory.

Files and directories added
```````````````````````````

::

  security/*.xml
  security/masterpw.*
  security/geoserver.jceks
  security/auth/*
  security/filter/*
  security/masterpw/*
  security/pwpolicy/*
  security/role/*
  security/usergroup/*
  
Files renamed
`````````````

  * ``security/users.properties`` renamed to ``security/users.properties.old``


Reverting from GeoServer 2.2.x to 2.1.x
----------------------------------------

In order to restore the GeoServer 2.1 configuration:

#. Stop GeoServer.

#. Rename ``users.properties.old`` to ``users.properties``.

#. Additionally (although not mandatory) delete the following files and directories::

     security/
       config.xml
       geoserver.jceks
       masterpw.xml
       masterpw.digest
       auth/
       filter/
       masterpw/
       pwpolicy/
       role/
       usergroup/


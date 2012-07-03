.. module:: geoserver.users_roles

.. _geoserver.users_roles:


Maganing Users and Roles
------------------------

Security in GeoServer is a role-based system. Roles are created to serve particular functions (Examples: access WFS, administer UI, read certain layers), and users are linked to those roles.

#. From the :guilabel:`Welcome` page click the :guilabel:`Users` link on the :guilabel:`Menu` Security section.

      .. figure:: img/users1.png
         :width: 550

      The users list  

#. From the users manager menu click ``Add new user`` and enter the following user configuration:

	- :guilabel:`user=wsuser`
	- :guilabel:`password=wsuser` 
	- Create a new role entering :guilabel:`ROLE_WS` in the ``New role`` field and clicking ``Add`` button.

      .. figure:: img/users2.png
         :width: 650

      The users list  

#. Click :guilabel:`Save` to create the new user.

.. note:: Linking users and roles is done via the file :guilabel:`users.properties` (located in $GEOSERVER_DATA_DIR/security directory). By default, this file contains one line: :guilabel:`admin=geoserver,ROLE_ADMINISTRATOR` (user=admin and password=geoserver).
          The ROLE_ADMINISTRATOR is the predefined role and provides full access to all systems inside GeoServer. If you are using GeoServer in a production environment, the password (and possibly the user name as well) must be immediately changed.

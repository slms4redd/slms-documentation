.. module:: geoserver.layer_level

.. _geoserver.layer_level:


Layer level security
^^^^^^^^^^^^^^^^^^^^

GeoServer allows access to be determined on a per-layer basis. Access to layers are linked to roles. Layers and roles are linked in a file called layers.properties, which is located in the security directory in your $GEOSERVER_DATA_DIR.

   .. note::
      
      The syntax for setting security is as follows.  (Parameters in brackets [] are optional)::

          namespace.layer.permission=role[,role2,...]

      where:

          * **namespace** is the name of the namespace. The wildcard * is used to indicate all namespaces.
	  * **layer** is the name of a featuretype or coverage. The wildcard * is used to indicate all layers.
	  * **permission** is the type of access permission (**r** for read access, **w** for write access).
	  * **role[,role2,...]** is the name(s) of predefined roles. The wildcard * is used to indicate the permission is applied to all users, including anonymous users.

      Layer-level security and :ref:`geoserver.service_level` cannot be combined.  For example, it is not possible to specify access to a specific OGC service on one specific layer.

#. From the :guilabel:`Welcome` page click the :guilabel:`Data security` link on the :guilabel:`Menu` Security section.

   .. figure:: img/data1.png
   
      The Data access rules list


   .. note:: You have to be logged in as Administrator in order to activate this function.

#. Click :guilabel:`Add new rule` in the top menu and enter the following configuration:

	- Select ``geosolutions`` from ``Workspace`` combo box.
	- Select ``ccounties`` from ``Layer`` combo box.
	- Select ``Read`` from ``Access mode`` combo box.
	- Select the :guilabel:`ROLE_WS` created in :ref:`geoserver.users_roles` section and pressing the right arrow on center of the window.

   .. figure:: img/data2.png
   
      The new data access read rule for ROLE_WS  

#. Click the :guilabel:`Save` button.   

   .. figure:: img/data3.png
   
      The new Data access rules list 


#. Click :guilabel:`Add new rule` in the top menu and enter the following configuration:

	- Select ``geosolutions`` from ``Workspace`` combo box.
	- Select ``ccounties`` from ``Layer`` combo box.
	- Select ``Write`` from ``Access mode`` combo box.
	- Select the :guilabel:`ROLE_WS` created in :ref:`geoserver.users_roles` section and pressing the right arrow on center of the window.

   .. figure:: img/data4.png
   
      The new data access write rule for ROLE_WS  

#. Click the :guilabel:`Save` button.   

   .. figure:: img/data5.png
   
      The new Data access rules list 
      
With this setup most of the layers are generally accessible read/write from all users, but the ``ccounties`` one can now be
accessed only by users having the ROLE_WS, or the administrator (which is all powerful): the system works pretty much 
like CSS selectors in HTML, the most specific rule wins.

Layer level security
--------------------


The ``layers.properties`` file may contain a further directive that specifies the way in which GeoServer will advertise secured layers and behave when a secured layer is accessed without the necessary privileges. The line is::

   mode=option

where **option** can be one of three values:

.. list-table:: 
   :widths: 20 80
   :header-rows: 1

   * - **Option**
     - **Description**
   * - ``hide`` *(default)*
     - Hides layers that the user does not have read access to, and behaves as if a layer is read only if the user does not have write permissions. The capabilities documents will not contain the layers the current user cannot access. This is the highest security mode.  Because of this, it can sometimes not work very well with clients such as uDig or Google Earth.           
   * - ``challenge``
     - Allows free access to metadata, but any attempt at accessing actual data is met by a HTTP 401 code (which forces most client to show an authentication dialog). The capabilities documents contain the full list of layers.  DescribeFeatureType and DescribeCoverage work fine.  This mode works fine with clients such as uDig or Google Earth.
   * - ``mixed``
     - Hides the layers the user cannot read from the capabilities documents, but triggers authentication for any other attempt to access the data or the metadata. This option is useful if you don't want the world to see the existence of some of your data, but you still want selected people to whom you give direct data access links to get the data after authentication.

The catalog level security can be set using the ``Catalog security`` page:

  .. figure:: img/data6.png
   
     Catalog level security

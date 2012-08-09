.. module:: geoserver.live_dvd_install

.. _geoserver.live_dvd_install:


Installing the LiveDVD on the system
------------------------------------

In order to increase the system performaces and, in case of live DVD, to have your work persisted for a later session, it is worth to *install* the LiveDVD on the system. It can be done either by installing the operating system on your local machine or on a Virtual Machine (VM) such as VMWare or VirtualBox.

   .. note::  In order to work smoothly and without issues with GeoServer it is **recommended** to make the ``GEOSERVER_DATA_DIR`` point to a folder on the persistent storage (see next section).


#. Follow the instruction in the :ref:`previous <geoserver.live_dvd_running>` section in order to start the LiveDVD.

   .. note::  Configuring a brand new VM compatible with Ubuntu, and attaching the LiveDVD to it, also allows you to install the workshop on a virtual environment.

#. Once you get the LiveDVD boot screen as shown below, type **install** and press ENTER

   .. figure:: img/dvd_install0.jpg
      :width: 600
	  
      LiveDVD boot screen

#. Choose your language and click :guilabel:`Continue` on the :guilabel:`Welcome` screen.

   .. figure:: img/dvd_install1.jpg
      :width: 600

      LiveDVD install: language selection

#. Choose your time-zone and click :guilabel:`Continue` on the :guilabel:`Where are you?` screen.

   .. figure:: img/dvd_install2.jpg
      :width: 600

      LiveDVD install: time-zone setting

#. Choose your keyboard layout and click :guilabel:`Continue` on the :guilabel:`Keyboard` screen.

   .. figure:: img/dvd_install3.jpg
      :width: 600

      LiveDVD install: keyboard layout

#. Select the partition where to install the system and click :guilabel:`Forward` on the :guilabel:`Prepare disk space` screen.

   .. note::  We suggest to not modify the configuration proposed here unless you are an advanced user and know exactly what you are doing.

   .. figure:: img/dvd_install4.jpg
      :width: 600

      LiveDVD install: Installation Type

#. Select the username and password and the hostname, then click on :guilabel:`Forward`.

   .. warning:: This course envisages the use of a ``username``:**unredd** with a ``password``: **unredd**. You can choose any ``hostname``.
   
   .. figure:: img/dvd_install5.jpg
      :width: 600

      LiveDVD install: ``Who are you?`` selection

#. Check the summary and click :guilabel:`Install`.

   .. figure:: img/dvd_install6.jpg
      :width: 600

      LiveDVD install: Ready to install summary.

#. Wait for installation to finish and then restart the system. Remember to remove the LiveDVD from the DVD-ROM before restarting the computer or VM.

   .. figure:: img/dvd_install7.jpg
      :width: 600

      LiveDVD install: installation progress

   .. figure:: img/dvd_install8.jpg
      :width: 600

      LiveDVD install: installation finished

   .. figure:: img/dvd_running.jpg
      :width: 600

      LiveDVD install: Working system

   .. note::  The OS user name and password to use for this workshop are:
   
			  - username: `unredd`
			  - password: `unredd`
	  
   .. note::  For each installed application on the system we used the user `unredd` with password `unredd`.

At this point you should have a fully functional environment to run GeoServer, GIS tools and clients on your dataset.


.. module:: geoserver.gs_prerequisites

.. _geoserver.gs_prerequisites:


Installing GeoServer prerequisites
----------------------------------

This section will take a tour of the prerequisites to work fully with GeoServer.

FWTools
^^^^^^^

FWTools is a set of Open Source GIS binaries. The kits are intended to be easy for end users to install and get going with. No fudzing with building from source, or having to collect lots of interrelated packages. FWTools includes OpenEV, GDAL, MapServer, PROJ.4 and OGDI as well as some supporting components. For more information see `FWTools site <http://fwtools.maptools.org/>`_.

To install FWTools in your ubuntu system you must follow these steps:

#. Navigate to the `FWTools site <http://fwtools.maptools.org/>`_ and download the current release ``FWTools 2.0.6 (Linux x86 32bit)``.

#. Unpack the ``tar.gz file`` and run the install.sh script in the new directory, and then add the bin_safe directory to your path::

	% tar xzvf FWTools.tar.gz
	% cd FWTools
	% ./install.sh

#. If you use Bash as your shell add the following to your startup script (ie. ~/.bash_profile):

	PATH=$PATH:$HOME/FWTools/bin_safe

   or if you use csh or tcsh as your shell add the following to your .cshrc:

	setenv PATH $PATH:$HOME/FWTools/bin_safe

Java
^^^^

Running GeoServer requires a Java Development Kit (JDK), not just a Java Runtime Environment (JRE). To install java JDK follows these steps:

#. Update the package index::

            sudo apt-get update

#. Install the JDK::

            sudo apt-get install sun-java6-jdk

#. If you want you can configure out the ``JAVA_HOME`` environment variable in ``.baschrc``::

            sudo nano .bashrc    

            Adding the following commands at the end of the file:
            
            export JAVA_HOME=/usr/lib/jvm/java-6-sun-1.6.0.32
            export PATH=$PATH:/usr/lib/jvm/java-6-sun-1.6.0.32

Apache Tomcat
^^^^^^^^^^^^^

Apache Tomcat is an open source software implementation of the ``Java Servlet`` and ``JavaServer Pages`` technologies. The Java Servlet and JavaServer Pages specifications are developed under the `Java Community Process <http://jcp.org/en/introduction/overview>`_  .

#. Navigate to the `Apache Tomcat site <http://tomcat.apache.org/download-60.cgi>`_ and download the ``Core - tar.gz`` binary distribution.

#. Move the ``tar.gz file`` in ``/opt`` directory and unpack this with the following commands::

	sudo mv -f apache-tomcat.tar.gz /opt

	sudo tar -xzf apache-tomcat.tar.gz

#. To run Tomcat automatically on system startup create a ``tomcat`` file in ``/etc/init.d/`` directory and enter the following script::

      #!/bin/bash
      # Startup script for the Tomcat server
      #
      # chkconfig: - 83 53
      # description: Starts and stops the Tomcat daemon.
      # processname: tomcat
      # pidfile: /var/run/tomcat.pid
      # See how we were called.
      case $1 in
      start)
      export CLASSPATH=/opt/apache-tomcat-6.0.30/lib/servlet-api.jar
      export CLASSPATH=/opt/apache-tomcat-6.0.30/lib/jsp-api.jar
      echo "Tomcat is started"
      su - unredd -c "/opt/apache-tomcat-6.0.30/bin/startup.sh"
      ;;
      stop)
      su - unredd -c "/opt/apache-tomcat-6.0.30/bin/shutdown.sh -force"
      echo "Tomcat is stopped"
      ;;
      restart)
      su - unredd -c "/opt/apache-tomcat-6.0.30/bin/shutdown.sh -force"
      echo "Tomcat is stopped"
      su - unredd -c "/opt/apache-tomcat-6.0.30/bin/startup.sh"
      echo "Tomcat is started"
      ;;
      *)
      echo "Usage: /etc/init.d/tomcat start|stop|restart"
      ;;
      esac
      exit 0


  .. warning:: This script works fine with only one instance of Tomcat. Currently the workshop VM contains one Tomcat but two different contexts, one for GeoServer running on HTTP port 8080 and one for GeowebCache running on port 8081. See below for details on the two different contexts.

#. Give them the proper permissions, and make it executable and recorded::

	sudo chmod 755 /etc/init.d/tomcat
	sudo update-rc.d tomcat defaults 
	
#. Provide the environment setting for Tomcat creating the ``setenv.sh`` file in ``/opt/apache-tomcat-6.0.30/bin`` directory and enter the following script::

	JAVA_HOME="/usr/lib/jvm/java-6-sun-1.6.0.32"
	JRE_HOME="$JAVA_HOME"

	CATALINA_HOME="/opt/apache-tomcat-6.0.30"
	CATALINA_PID=$CATALINA_HOME/catalina.pid

	JAVA_OPTS="$JAVA_OPTS -server -Xms256m -Xmx256m 
	            -XX:SoftRefLRUPolicyMSPerMB=36000
				-XX:MaxPermSize=128m"

	LD_LIBRARY_PATH="/home/unredd/geoserver_src/nativelibs"
	GDAL_DATA="/home/snredd/geoserver_src/gdal_data"

   .. warning:: In this workshop the JAVA_HOME is defined at the Tomcat startup. If you define the JAVA_HOME variable inside the .bashrc file you must not define it here. 
				
#. To run Apache Tomcat::

	/etc/init.d/tomcat start

#. To stop Apache Tomcat::

	/etc/init.d/tomcat stop

  .. note::
    
	As pointed out above the workshop OS contains different scripts for Tomcat since we are running two different contexts on two HTTP ports, 8080 and 8081.
	Under ``/etc/init.d`` it's possible to find tree scripts:
   
   #. ``/etc/init.d/tomcatRunner`` : Executes a Tomcat startup command and notes the PID; takes 4 parameters: 1. the pidfile - 2. the tomcat logfile - 3. the command to execute - 4. other opts to attach to the command
   #. ``/etc/init.d/geoserver`` : Starts/Stop GeoServer tomcat context on HTTP 8080. **service geoserver {start/stop/restart}**
   #. ``/etc/init.d/geowebcache`` : Starts/Stop GeowebCache tomcat context on HTTP 8081. **service geowebcache {start/stop/restart}**



 

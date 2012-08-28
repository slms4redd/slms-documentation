.. module:: unredd.install.tomcat_instances

Preparing tomcat instances (S+D)
================================

Tomcat installation steps
-------------------------

This guide describes the tomcat installation steps.

Autostart scripts are a litte different according to the Linux distribution you are using:
 * If you are using a **Debian** or or derived distribution (e.g. **Ubuntu**), you will only need a single startup file in ``/etc/init.d`` directory for each tomcat instance.
 * If you are using a **RedHat** or derived distribution (e.g. **CentOS**), you will also need a further helper file (``tomcatRunner.sh``).
Note that the startup scripts of the two distribution families differ.

Requires a copy of the scripts tomcatRunner.sh and geoserver0.sh
from the unredd production server.

**TODO**: Get that files from server, copy them somewhere accessible,
preferably under version control (git, gist?).

1. Install in ``/opt/apache-tomcat-6.0.35/`` a full tomcat instance
   ``CATALINA_HOME=/opt/apache-tomcat-6.0.35/``
2. Create a folder ``/var/tomcat/``, where all the ``CATALINA_BASE`` dirs will be created.
   Create one dir for each application. Every instance will have the
   ``CATALINA_BASE`` path in the form ``/var/tomcat/<instance_name>``.
   **NB**: Is very important to use consistent naming for each instance.
3. In all ``CATALINA_BASE=/var/tomcat/<instance_name>``:  

   a. create the directories ``logs``, ``temp``, ``webapps``,
      ``work``, ``bin``, ``lib`` .

   #. Copy from ``CATALINA_HOME`` the dir ``conf`` . 

   #. Create within the ``bin`` dir the file ``setenv.sh`` .
 
4. Edit each ``server.xml`` in all ``CATALINA_BASE/conf`` dirs changing
   the port number for shutdown, ajp and http connector. Use a rational
   enumeration (see the table below).

5. Edit each previously created ``setenv.sh`` file setting all the env
   variables requested. Dont’t forget to set the ``CATALINA_HOME`` var
   with the own path for each application and set ``JAVA_OPTS`` variable
   setting the JVM paramenters.

6. Create the directory ``/var/run/tomcat/`` (if it isn’t already present)
   where PID file will be created.

7. Copy in ``/etc/init.d/`` ``tomcatRunner.sh``.

8. Copy in ``/etc/init.d/`` a copy of ``geoserver0.sh`` and rename it
   exactly like the instance name. Edit the file and assign to variable
   servicename the instancename.

9. Assign the executable permission to all script copied in ``/etc/init.d/``::

     chmod +x <script_name>

10. add them to ``chkconfig --add <script_name>``

11. Deploy all the applications into ``webapps``.

12. Launch all tomcat instances running all the scripts
    (obviously except ``tomcatRunner.sh``).

13. Celebrate.


Tomcat instance summary
-----------------------

============== ====  ====  ====  ==========================
instance       shut  ajp   http  location
============== ====  ====  ====  ==========================
stg_geostore   8020  8100  8200  /var/tomcat/stg_geostore
stg_geoserver  8021  8101  8201  /var/tomcat/stg_geoserver
stg_geobatch   8022  8102  8202  /var/tomcat/stg_geobatch
stg_frontend   8023  8103  8203  /var/tomcat/stg_frontend
diss_geostore  8024  8104  8204  /var/tomcat/diss_geostore
diss_geoserver 8025  8105  8205  /var/tomcat/diss_geoserver
diss_frontend  8026  8106  8206  /var/tomcat/diss_frontend
============== ====  ====  ====  ==========================

To see which tomcat instances are running, you can watch the dir
``/var/run/tomcat/`` which contains a file called like the instances
for every running instance (inside the file is stored the PID).


httpd mapping
-------------

Configurations to connect to all backend webapp throught AJP are
in ``/etc/httpd/conf.d/proxy_ajp.conf``.

.. module:: unredd.install.system_packages

Installing needed system packages (S+D)
=======================================


Java
----

Download and run/install ``jdk-6u26-linux-x64-rpm.bin`` from:
http://www.oracle.com/technetwork/java/javase/downloads/jdk-6u26-download-400750.html

Tomcat
------

Download from
http://it.apache.contactlab.it/tomcat/tomcat-6/v6.0.32/bin/apache-tomcat-6.0.32.tar.gz

Install under ``/opt``, so that the dir ``opt/`` will look like::

  ll /opt/ | grep tomcat
  
  drwxr-xr-x 9 root root 4096 Aug  1 15:40 apache-tomcat-6.0.32
  lrwxrwxrwx 1 root root   20 Aug  1 15:41 tomcat -> apache-tomcat-6.0.32


httpd
-----

In order to give editing permission to the https folder, the www group has
to be added:

* Add group ``www``
* Add tomcat and ftp users to group ``www``
* Assign group to www contents ``chgrp www -R /var/www/``
* Change permissions on html contents ``chmod g+w  /var/www/html/``


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

Maven
-----

::

  mkdir  /usr/local/java
  cd  /usr/local/java
  wget http://mirror.nohup.it/apache//maven/binaries/apache-maven-2.2.1-bin.tar.gz
  tar xzvf apache-maven-2.2.1-bin.tar.gz
  ln -s apache-maven-2.2.1 maven
  ln -s /usr/local/java/maven/bin/mvn /usr/bin/

Iptables
--------

::

  iptable -A INPUT -m state --state NEW -m tcp -p tcp --dport 21 -j ACCEPT
  iptable -A INPUT  -m state --state NEW -m tcp -p tcp --dport 64000:65535 -j ACCEPT
  iptable-save

vsftpd
------

Add users folder ``/etc/vsftpd/users/`` and user specific configuration.

vsftpd users are system users which have ftp R/W permissions on the
``~/ftp`` folder.

To make no login system users to login on vsftp service change the
pam authentication config::

  nano /etc/pam.d/vsftpd

Commenting out the following line::

  auth    required pam_shells.so

httpd
-----

In order to give editing permission to the https folder, the www group has
to be added:

* Add group ``www``
* Add tomcat and ftp users to group ``www``
* Assign group to www contents ``chgrp www -R /var/www/``
* Change permissions on html contents ``chmod g+w  /var/www/html/``


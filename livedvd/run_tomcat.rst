Starting and stopping the tomcat instances
==========================================

To **start** a tomcat instance, open a Terminal window and type::

  sudo /etc/init.d/[instance_name] start

For example, to use `geoserver`, type::

  sudo /etc/init.d/geoserver start
  
After a few seconds, the contained applications will be accessible through their corresponding URLs (see table above).

To **stop** a tomcat instance, type::

  sudo /etc/init.d/[instance_name] stop

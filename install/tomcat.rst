.. _unredd-install-tomcat_instances:

Preparing multiple tomcat instances
===================================

Run the steps described in :ref:`unredd-install-tomcat6` before reading this chapter.

The directory where tomcat binaries reside is known as ``CATALINA_HOME``, common to all running tomcat instances. In our case::

  CATALINA_HOME=/opt/tomcat

We are creating different Tomcat profiles to simultaneously run several tomcat instances on the same system. Each tomcat profile location is called ``CATALINA_BASE``, and it will be different for each running instance. In our case::

  CATALINA_BASE=/var/tomcat/$INSTANCE_NAME

Where ``$INSTANCE_NAME`` will receive different values.


Creating the tomcat profiles
----------------------------

First, create a template profile with the needed files and directories. It will be cloned to create the real ones::

  sudo -s
  mkdir -p /var/tomcat/template
  cd /var/tomcat/template
  mkdir logs temp webapps work bin lib
  cp -r /opt/tomcat/conf conf
  touch bin/setenv.sh

Create tomcat6 user::

  useradd tomcat6

From this template, we will create the different profiles. Following tables show suggested setup for the staging area:

============== ====  ====  ====  ==========================
instance       shut  ajp   http  location
============== ====  ====  ====  ==========================
stg_geostore   8020  8100  8200  /var/tomcat/stg_geostore
stg_geoserver  8021  8101  8201  /var/tomcat/stg_geoserver
stg_geobatch   8022  8102  8202  /var/tomcat/stg_geobatch
admin          8023  8103  8203  /var/tomcat/admin
============== ====  ====  ====  ==========================

And for dissemination area:

============== ====  ====  ====  ==========================
instance       shut  ajp   http  location
============== ====  ====  ====  ==========================
diss_geostore  8024  8104  8204  /var/tomcat/diss_geostore
diss_geoserver 8025  8105  8205  /var/tomcat/diss_geoserver
portal         8026  8106  8206  /var/tomcat/portal
============== ====  ====  ====  ==========================

For each of the instances, do:

  1. Copy ``/var/tomcat/template`` to ``/var/tomcat/<instance_name>``.
     This location will be the instance's ``CATALINA_BASE``::

       cp -r /var/tomcat/template /var/tomcat/<instance_name>

  2. Edit ``CATALINA_BASE/conf/server.xml``, replacing
     the port numbers for `shutdown`, `ajp` and `http` connectors. Use a
     rational enumeration on port assignation (see the tables above). For
     example, for ``stg_geoserver``, do::

       cd /var/tomcat/stg_geoserver
       sed -i 's/port="8005"/port="8021"/g' conf/server.xml #shutdown port
       sed -i 's/port="8009"/port="8101"/g' conf/server.xml #ajp port
       sed -i 's/port="8080"/port="8201"/g' conf/server.xml #http port

  3. Edit ``CATALINA_BASE/bin/setenv.sh`` and add the needed environment
     variables, depending on the applications running on the specific
     instance. Dont’t forget to set the ``CATALINA_HOME`` var
     with the own path for each application and set ``JAVA_OPTS`` variable
     setting the JVM parameters.

     These are the suggested ``setenv.sh`` contents for each
     instance. For more details on these environment setups, please refer to
     application specific documentation:

       **stg_geostore**:

       .. code-block:: sh

         SERVICE=stg_geostore
         
         # Application specific environment
         GEOSTORE_OVR_FILE=file:/var/$SERVICE/geostore-datasource-ovr.properties

         # Java options
         JAVA_OPTS="-server -Xms512m -Xmx512m -Dgeostore-ovr=$GEOSTORE_OVR_FILE -Duser.timezone=GMT"


       **stg_geoserver**:

       .. code-block:: sh

         SERVICE=stg_geoserver
         
         # Application specific environment
         GEOSERVER_DATA_DIR=/var/$SERVICE/data
         GEOSERVER_LOG_LOCATION=/var/$SERVICE/logs/geoserver.log

         # Java options
         JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m -XX:PermSize=64m -XX:+UseConcMarkSweepGC -XX:NewSize=48m -Dorg.geotools.shapefile.datetime=true -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION -Duser.timezone=GMT"


       **stg_geobatch**:

       .. code-block:: sh

         SERVICE=stg_geobatch
         
         # Application specific environment
         GEOBATCH_CONFIG_DIR=/var/$SERVICE/config
         GEOBATCH_TEMP_DIR=/var/$SERVICE/temp

         # Java options
         JAVA_OPTS="-server -Xms2048m -Xmx2048m -DGEOBATCH_CONFIG_DIR=$GEOBATCH_CONFIG_DIR -DGEOBATCH_TEMP_DIR=$GEOBATCH_TEMP_DIR -Duser.timezone=GMT"


       **admin**:

       .. code-block:: sh

         SERVICE=admin
         
         # Java options
         JAVA_OPTS="-server -Duser.timezone=GMT"


       **diss_geostore**:

       .. code-block:: sh

         SERVICE=diss_geostore
         
         # Application specific environment
         GEOSTORE_OVR_FILE=file:/var/$SERVICE/geostore-datasource-ovr.properties
         
         # Java options
         JAVA_OPTS="-server -Xms512m -Xmx1024m -Dgeostore-ovr=$GEOSTORE_OVR_FILE -Duser.timezone=GMT"


       **diss_geoserver**:

       .. code-block:: sh

         SERVICE=diss_geoserver
         
         # Application specific environment
         GEOSERVER_DATA_DIR=/var/$SERVICE/data
         GEOSERVER_LOG_LOCATION=/var/$SERVICE/logs/geoserver.log

         # Java options
         JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m -XX:PermSize=64m -XX:+UseConcMarkSweepGC -XX:NewSize=48m -Dorg.geotools.shapefile.datetime=true -DGEOSERVER_DATA_DIR=$GEOSERVER_DATA_DIR -DGEOSERVER_LOG_LOCATION=$GEOSERVER_LOG_LOCATION -Duser.timezone=GMT"


       **portal**:

       .. code-block:: sh

         SERVICE=portal

         # Application specific environment
         PORTAL_CONFIG_DIR=/var/$SERVICE/

         # Java options
         JAVA_OPTS="-server -DMINIFIED_JS=true -DPORTAL_CONFIG_DIR=$PORTAL_CONFIG_DIR -Duser.timezone=GMT"

  4. Create the file ``/etc/init.d/ubuntuTomcatRunner.sh`` with this content:

     .. code-block:: sh

         #!/bin/sh
         #
         # /etc/init.d/tomcat6 -- startup script for the Tomcat 6 servlet engine
         #
         # Written by Miquel van Smoorenburg <miquels@cistron.nl>.
         # Modified for Debian GNU/Linux  by Ian Murdock <imurdock@gnu.ai.mit.edu>.
         # Modified for Tomcat by Stefan Gybas <sgybas@debian.org>.
         # Modified for Tomcat6 by Thierry Carrez <thierry.carrez@ubuntu.com>.
         # Additional improvements by Jason Brittain <jason.brittain@mulesoft.com>.
         # Adapted to run multiple tomcat instances for UN-REDD NFMS platform.
         
         set -e
         
         DESC="NFMS4REDD Tomcat"
         CATALINA_BASE=/var/tomcat/$SERVICE
         PATH=/bin:/usr/bin:/sbin:/usr/sbin
         DEFAULT=/etc/default/$SERVICE
         JVM_TMP=$CATALINA_BASE/temp
         
         if [ -r $CATALINA_BASE/bin/setenv.sh ]; then
                 . $CATALINA_BASE/bin/setenv.sh
         fi
         
         if [ `id -u` -ne 0 ]; then
            echo "You need root privileges to run this script"
            exit 1
         fi
         
         # Make sure tomcat is started with system locale
         if [ -r /etc/default/locale ]; then
            . /etc/default/locale
            export LANG
         fi
         
         . /lib/lsb/init-functions
         
         if [ -r /etc/default/rcS ]; then
            . /etc/default/rcS
         fi
         
         
         # The following variables can be overwritten in $DEFAULT
         
         # Run Tomcat 6 as this user ID and group ID
         TOMCAT6_USER=tomcat6
         TOMCAT6_GROUP=tomcat6
         
         # The first existing directory is used for JAVA_HOME (if JAVA_HOME is not
         # defined in $DEFAULT)
         JDK_DIRS="/usr/lib/jvm/default-java"
         
         # Look for the right JVM to use
         for jdir in $JDK_DIRS; do
             if [ -r "$jdir/bin/java" -a -z "${JAVA_HOME}" ]; then
            JAVA_HOME="$jdir"
             fi
         done
         export JAVA_HOME
         
         # Directory where the Tomcat 6 binary distribution resides
         CATALINA_HOME=/opt/tomcat
         
         # Use the Java security manager? (yes/no)
         TOMCAT6_SECURITY=no
         
         # Default Java options
         # Set java.awt.headless=true if JAVA_OPTS is not set so the
         # Xalan XSL transformer can work without X11 display on JDK 1.4+
         # It also looks like the default heap size of 64M is not enough for most cases
         # so the maximum heap size is set to 128M
         if [ -z "$JAVA_OPTS" ]; then
            JAVA_OPTS="-Djava.awt.headless=true -Xmx128M"
         fi
         
         # End of variables that can be overwritten in $DEFAULT
         
         # overwrite settings from default file
         #if [ -f "$DEFAULT" ]; then
         #  . "$DEFAULT"
         #fi
         
         if [ ! -f "$CATALINA_HOME/bin/bootstrap.jar" ]; then
            log_failure_msg "$SERVICE is not installed"
            exit 1
         fi
         
         POLICY_CACHE="$CATALINA_BASE/work/catalina.policy"
         
         if [ -z "$CATALINA_TMPDIR" ]; then
            CATALINA_TMPDIR="$JVM_TMP"
         fi
         
         # Set the JSP compiler if set in the tomcat6.default file
         if [ -n "$JSP_COMPILER" ]; then
            JAVA_OPTS="$JAVA_OPTS -Dbuild.compiler=\"$JSP_COMPILER\""
         fi
         
         SECURITY="no"
         if [ "$TOMCAT6_SECURITY" = "yes" ]; then
            SECURITY="-security"
         fi
         
         # Define other required variables
         CATALINA_PID="/var/run/$SERVICE.pid"
         CATALINA_SH="$CATALINA_HOME/bin/catalina.sh"
         
         # Look for Java Secure Sockets Extension (JSSE) JARs
         if [ -z "${JSSE_HOME}" -a -r "${JAVA_HOME}/jre/lib/jsse.jar" ]; then
             JSSE_HOME="${JAVA_HOME}/jre/"
         fi
         
         catalina_sh() {
            # Escape any double quotes in the value of JAVA_OPTS
            JAVA_OPTS="$(echo $JAVA_OPTS | sed 's/\"/\\\"/g')"
         
            AUTHBIND_COMMAND=""
            if [ "$AUTHBIND" = "yes" -a "$1" = "start" ]; then
               JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true"
               AUTHBIND_COMMAND="/usr/bin/authbind --deep /bin/bash -c "
            fi
         
            # Define the command to run Tomcat's catalina.sh as a daemon
            # set -a tells sh to export assigned variables to spawned shells.
            TOMCAT_SH="set -a; JAVA_HOME=\"$JAVA_HOME\"; source \"$DEFAULT\"; \
               CATALINA_HOME=\"$CATALINA_HOME\"; \
               CATALINA_BASE=\"$CATALINA_BASE\"; \
               JAVA_OPTS=\"$JAVA_OPTS\"; \
               CATALINA_PID=\"$CATALINA_PID\"; \
               CATALINA_TMPDIR=\"$CATALINA_TMPDIR\"; \
               LANG=\"$LANG\"; JSSE_HOME=\"$JSSE_HOME\"; \
               cd \"$CATALINA_BASE\"; \
               \"$CATALINA_SH\" $@"
         
            if [ "$AUTHBIND" = "yes" -a "$1" = "start" ]; then
               TOMCAT_SH="'$TOMCAT_SH'"
            fi
         
            # Run the catalina.sh script as a daemon
            set +e
            touch "$CATALINA_PID" "$CATALINA_BASE"/logs/catalina.out
            #chown -R $TOMCAT6_USER:$TOMCAT6_USER $CATALINA_BASE
            chown $TOMCAT6_USER "$CATALINA_PID" "$CATALINA_BASE"/logs/catalina.out
            start-stop-daemon --start -b -u "$TOMCAT6_USER" -g "$TOMCAT6_GROUP" \
               -c "$TOMCAT6_USER" -d "$CATALINA_TMPDIR" -p "$CATALINA_PID" \
               -x /bin/bash -- -c "$AUTHBIND_COMMAND $TOMCAT_SH"
            status="$?"
            set +a -e
            return $status
         }
         
         case "$1" in
           start)
            if [ -z "$JAVA_HOME" ]; then
               log_failure_msg "no JDK found - please set JAVA_HOME"
               exit 1
            fi
         
            if [ ! -d "$CATALINA_BASE/conf" ]; then
               log_failure_msg "invalid CATALINA_BASE: $CATALINA_BASE"
               exit 1
            fi
         
            log_daemon_msg "Starting $DESC" "$SERVICE"
            if start-stop-daemon --test --start --pidfile "$CATALINA_PID" \
               --user $TOMCAT6_USER --exec "$JAVA_HOME/bin/java" \
               >/dev/null; then
         
               # Regenerate POLICY_CACHE file
         #     umask 022
         #     echo "// AUTO-GENERATED FILE from /etc/tomcat6/policy.d/" \
         #        > "$POLICY_CACHE"
         #     echo ""  >> "$POLICY_CACHE"
         #     cat $CATALINA_BASE/conf/policy.d/*.policy \
         #        >> "$POLICY_CACHE"
         
               # Remove / recreate JVM_TMP directory
               rm -rf "$JVM_TMP"
               mkdir -p "$JVM_TMP" || {
                  log_failure_msg "could not create JVM temporary directory"
                  exit 1
               }
               chown $TOMCAT6_USER "$JVM_TMP"
         
               catalina_sh start $SECURITY
               sleep 5
                  if start-stop-daemon --test --start --pidfile "$CATALINA_PID" --user $TOMCAT6_USER --exec "$JAVA_HOME/bin/java" \
                  >/dev/null; then
                  echo $?
                  if [ -f "$CATALINA_PID" ]; then
                     rm -f "$CATALINA_PID"
                  fi
                  log_end_msg 1
               else
                  log_end_msg 0
               fi
            else
                    log_progress_msg "(already running)"
               log_end_msg 0
            fi
            ;;
           stop)
            log_daemon_msg "Stopping $DESC" "$SERVICE"
         
            set +e
            if [ -f "$CATALINA_PID" ]; then
               start-stop-daemon --stop --pidfile "$CATALINA_PID" \
                  --user "$TOMCAT6_USER" \
                  --retry=TERM/20/KILL/5 >/dev/null
               if [ $? -eq 1 ]; then
                  log_progress_msg "$SERVICE is not running but pid file exists, cleaning up"
               elif [ $? -eq 3 ]; then
                  PID="`cat $CATALINA_PID`"
                  log_failure_msg "Failed to stop $SERVICE (pid $PID)"
                  exit 1
               fi
               rm -f "$CATALINA_PID"
               rm -rf "$JVM_TMP"
            else
               log_progress_msg "(not running)"
            fi
            log_end_msg 0
            set -e
            ;;
            status)
            set +e
            start-stop-daemon --test --start --pidfile "$CATALINA_PID" \
               --user "$TOMCAT6_USER" \
               >/dev/null 2>&1
            if [ "$?" = "0" ]; then
         
               if [ -f "$CATALINA_PID" ]; then
                   log_success_msg "$SERVICE is not running, but pid file exists."
                  exit 1
               else
                   log_success_msg "$SERVICE is not running."
                  exit 3
               fi
            else
               log_success_msg "$SERVICE is running with pid `cat $CATALINA_PID`"
            fi
            set -e
                 ;;
           restart|force-reload)
            if [ -f "$CATALINA_PID" ]; then
               $0 stop
               sleep 1
            fi
            $0 start
            ;;
           try-restart)
                 if start-stop-daemon --test --start --pidfile "$CATALINA_PID" \
               --user $TOMCAT6_USER --exec "$JAVA_HOME/bin/java" \
               >/dev/null; then
               $0 start
            fi
                 ;;
           *)
            log_success_msg "Usage: $0 {start|stop|restart|try-restart|force-reload|status}"
            exit 1
            ;;
         esac
         
         exit 0


  5. For each of the instances, create a file under ``/etc/init.d/``
     named exactly as the correspondig directory under ``/var/tomcat``.
     It will contain the INIT block, the service name, and a description.
     File contents for portal service woud be (for each file, replace
     *portal* occurences in this example with the corresponding service
     name):

     .. code-block:: sh

      #!/bin/sh
      ### BEGIN INIT INFO
      # Provides:          portal
      # Required-Start:    $local_fs $remote_fs $network
      # Required-Stop:     $local_fs $remote_fs $network
      # Should-Start:      $named
      # Should-Stop:       $named
      # Default-Start:     2 3 4 5
      # Default-Stop:      0 1 6
      # Description:       Start portal.
      ### END INIT INFO

      SERVICE=portal
      . /etc/init.d/ubuntuTomcatRunner.sh

  6. Make all scripts created in ``/etc/init.d/`` executable::

      chmod +x ubuntuTomcatRunner.sh stg_* diss_* admin portal

  7. Deploy all the applications into ``webapps``, and create the needed
     configuration and data files under ``/var``. Read the specific chapters
     on different instances to know what is needed in each case.

  8. Launch all tomcat instances running the correspondig ``/etc/init.d`` scripts.

  9. Check the applications under the various running instances.


Check tomcat running instances
------------------------------

To see which tomcat instances are running, you can watch the
``/var/run/`` directory, which contains one file for each of
the running instances (inside the file it is stored the PID).


Make services start at boot time
--------------------------------

Install ``chkconfig``::

  apt-get install chkconfig

Hack to make chkconfig work under ubuntu 12.04::

  ln -s /usr/lib/insserv/insserv /sbin/insserv

Add all of the services::

  chkconfig -s <service_name> on

Check their status::

  chkconfig --list


AJP proxying
------------

Configurations to connect to all backend webapp throught AJP are
in ``/etc/httpd/conf.d/proxy_ajp.conf``.

Create the file ``/etc/apache2/mods-available/proxy_ajp.conf`` and define the redirections to the various tomcat instances::

  # Don't rewrite hostname
  ProxyPreserveHost on

  # Proxy rules for the staging area
  ProxyPass        /stg_geostore   ajp://localhost:8100/stg_geostore
  ProxyPassReverse /stg_geostore   ajp://localhost:8100/stg_geostore
  ProxyPassReverse /stg_geostore/  ajp://localhost:8100/stg_geostore/

  ProxyPass        /stg_geoserver   ajp://localhost:8101/stg_geoserver
  ProxyPassReverse /stg_geoserver   ajp://localhost:8101/stg_geoserver
  ProxyPassReverse /stg_geoserver/  ajp://localhost:8101/stg_geoserver/

  ProxyPass        /stg_geobatch   ajp://localhost:8102/stg_geobatch
  ProxyPassReverse /stg_geobatch   ajp://localhost:8102/stg_geobatch
  ProxyPassReverse /stg_geobatch/  ajp://localhost:8102/stg_geobatch/

  ProxyPass        /admin   ajp://localhost:8103/admin
  ProxyPassReverse /admin   ajp://localhost:8103/admin
  ProxyPassReverse /admin/  ajp://localhost:8103/admin/

  # Proxy rules for the dissemination area
  ProxyPass        /diss_geostore   ajp://localhost:8104/diss_geostore
  ProxyPassReverse /diss_geostore   ajp://localhost:8104/diss_geostore
  ProxyPassReverse /diss_geostore/  ajp://localhost:8104/diss_geostore/

  ProxyPass        /diss_geoserver   ajp://localhost:8105/diss_geoserver
  ProxyPassReverse /diss_geoserver   ajp://localhost:8105/diss_geoserver
  ProxyPassReverse /diss_geoserver/  ajp://localhost:8105/diss_geoserver/

  ProxyPass        /portal   ajp://localhost:8106/portal
  ProxyPassReverse /portal   ajp://localhost:8106/portal
  ProxyPassReverse /portal/  ajp://localhost:8106/portal/


Create a link in mods-enabled::

  ln -s /etc/apache2/mods-available/proxy_ajp.conf /etc/apache2/mods-enabled/proxy_ajp.conf

Restart Apache server:

  /etc/init.d/apache2 restart

Linux
======

On the command prompt you can use a series of commands available on most Linux systems. But before we see
these commands it's important to know how Linux organizes the file system and how files are referenced using
relative and absolute paths.

The Linux file system is organized hierarchically starting from a directory called "root" and addressed by
the forward slash (/). Files in Linux are referenced using paths that can be relative or absolute. The absolute
paths start with "/", while the rleratives ones start with the name of the subdirectory, with "." (current directory),
or with ".." (parent directory).

So we can have absolute paths like::

  /tmp
  /home/unredd
  /home/geo/Desktop
  etc.

Absolute paths can be used from any directory. We can list the content of the directories
above with the following commands, regardless of the directory in which you are::

  $ ls /tmp
  $ ls /home/geo
  $ ls /home/geo/Desktop

Relative paths start from the current directory. If for example we are in */home/unredd*, we can
list the content of the above directories with the following commands::

  $ ls ../../tmp
  $ ls .
  $ ls Desktop

or "browsing" using . and ..::

  $ ls Desktop/../../../tmp
  $ ls ./././././././././././../unredd
  $ ls ../geo/Desktop


Below are listed some useful commands in Linux:

- less: displays a text file::

  $ less file_to_visualize

  The file is visualized in a way that is common to UNIX system. In the lower left corner we can see a
  colon sign (:) followed by the cursor.

  .. image :: _static/linux/training_postgresql_help.png

  We can browse the content by pressing the up and down cursor keys, as well and the page up and page down
  keys.

  It is also possible to search using the command /text. Once you press enter, the search results will be highlighted as you
  can see in the following image. To navigate to the next or previous matches you can press 'n' of Shift + 'n'.

  .. image :: _static/linux/training_postgresql_psql_help_search.png

  Press the '1' to exit.

- nano: used to edit text files. At the bottom you can find the commands to save the file, exit, etc.::

  $ nano file_path

- locate: finds files in the file system::

  $ locate part_of_the_file_name

  An axpect to consider in the use of locate is that the Operating System periodically scans the file system
  to build an index of existing in order for *locate* to quickly find files withoud having to go through
  all the file system, which would be time consuming. As a drawback, locate doesn't find the latest files created.

- id: Show the current user::

  $ id

- su: Allows to autenticate with a different user.

  $ su postgres

- sudo: runs the command that follows with superuser permissions. For example, to run the above command
with root permissions::

  $ sudo su postgres

- passwd: changes the user password. For example to change the root user passrord::

  $ sudo passwd root

- ssh: remote access by command line. With ssh is possible to access a remote server that enables
  ssh access. To do this you need to specify the server address::

  $ ssh 168.202.48.151
  The authenticity of host '168.202.48.151 (168.202.48.151)' can't be established.
  ECDSA key fingerprint is 9f:7c:a8:9c:8b:66:37:68:8b:7f:95:a4:1b:24:06:39.
  Are you sure you want to continue connecting (yes/no)? yes

  In the output above we can see that the system checks the autenticity of the machine you want to connect.
  After answering affermatibely the system tell us that the system we are connecting to is added to the
  list of known hosts, so that the previous message will not appear again the next time a connection is
  apptempted. Then the system asks the user passrord::

  Warning: Permanently added '168.202.48.151' (ECDSA) to the list of known hosts.
  unredd@168.202.48.151's password:

  If you want to connect as another user you need to prefix the name of the user, followed by the character
  "@" before the server address::

  $ ssh other_user@168.202.48.151

- scp: copies files to the server::

  $ scp from_server to_server

  The directory can be a normal route or an ssh connection string to a remote server. Some example follow.

  Here we copy a local file in the */tmp* directory on a remote server::

    $ scp local_file geo@168.202.48.151:/tmp

  The following command copies the file back to our host from the remote server::

    $ scp geo@168.202.48.151:/tmp/local_file .

  You can see that the format of the remote server URL is similar to the one used to connect to an ssh server.
  The only difference is that in the end, separated by *:*, there is the path of the file in the remote host.

- zip: compresses files::

  $ zip -r zipped_file.zip list_of_files_to_compress

  The "-r" option causes the zip command to travel the directory structure recursively .

- unzip: decompresses zip files::

  $ unzip file_path.zip

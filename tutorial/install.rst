Installation
=============

Install the 10.18.2 version of chef-solo
----------------------------------------

To install chef-solo 10.18.2 run the following commands. The lines beginning with # are comments, the ones that begin with $ should be copied on the Linux command line. The $ symbolizes the command line so you do not have to include it::

	# Add  "deb http://apt.opscode.com/ precise-0.10 main"
	# (Without the quotes) at the end of the cild /etc/apt/sources.list
	$ sudo gedit /etc/apt/sources.list
	$ gpg --keyserver keys.gnupg.net --recv-keys 83EF826A
	$ gpg --export packages@opscode.com | sudo tee /etc/apt/trusted.gpg.d/opscode-keyring.gpg > /dev/null
	$ sudo apt-get update
	$ sudo apt-get install opscode-keyring

	# During the next step you will be asked for a parameter that is not used, you can leave it blank.

	$ sudo apt-get install chef
	$ chef-solo -version

Run chef-solo
-------------

Execute the following command:

	$ sudo mkdir /var/chef-solo

Copy the cookbooks and the files solo.rb and dna.json in the directory we just created. The final result should be::

	/
	\- var
	    \- chef-solo
	        |- dna.json
	        |- solo.rb
	        \- cookbooks
	            |- apache2
	            |- apt
	            |- ark
	            |- build-essential
	            |- database
	            |- gis
	            |- java
	            |- logrotate
	            |- omnibus_updater
	            |- openssl
	            |- postgresql
	            |- tomcat
	            \- unredd-nfms-portal

Execute the following command from the directory /var/chef-solo::

	sudo chef-solo -c solo.rb -j dna.json

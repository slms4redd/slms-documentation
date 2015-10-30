Install the virtual machine
===========================

For this training we will use a virtual machine. We need to install a virtualization software that will host the Operating System. The virtualization software of our choice is VirtualBox and our Operating System will be Ubuntu/Linux

The steps we are going to take are the following ones:

* Download and install VirtualBox
* Create a virtual machine
* Install Ubuntu/Linux

Using the virtualization software terminology, the physical machine (hardware) is called *host* while the virtual machine is called *guest*.


VirtualBox installation
-----------------------

The first step is to download VirtualBox under "VirtualBox platform packages" on the download page (https://www.virtualbox.org/wiki/Downloads)
 and proceed with the installation.

Then you will need to install Ubuntu/Linux, so it is advisable to download the installer
while preparing the virtual machine. The Ubuntu installer is available on Ubuntu download page (http://www.ubuntu.com/download). We will download
Ubuntu Desktop, preferably the 32-bit package of the 12.04 LTS (Long Term Support) distribution.

The Ubuntu page asks for a small contribution for download, but you are not required to do it. You can continue
the download by clicking on the "not now, take me to the download":

.. image:: _static/virtualbox/ubuntu_download.png
   :width: 700 px
   :align: center

The downloaded file should be called "ubuntu-12.04.1-desktop-i386.iso".


Creation of a virtual machine
-----------------------------

Once VirtualVox is installed, we start it and create a new virtual machine:

.. image:: _static/virtualbox/p00.png
   :align: center

Below we give a name to the virtual machine and specify the operating system "Linux", "Ubuntu".

.. image:: _static/virtualbox/p01.png

We set 4096Mb of memory for the virtual machine. Keep in mind that this memory is taken from the host machine
so if the host has less than 2GB, 1GB allocated to the virtual machine may be too much, as the host
may run out of memory.

In no case the memory allocated to the virtaul machine should exceeded 50% of the total memory of the host.

.. image:: _static/virtualbox/p02.png
   :align: center

Finally we have to specify the size and type of the disc. For all the parameters but the hard disk size, choose the default option. For the hard disk size choose 80GB.

.. image:: _static/virtualbox/p03.png
   :align: center

.. image:: _static/virtualbox/p04.png
   :align: center

.. image:: _static/virtualbox/p05.png
   :align: center

.. image:: _static/virtualbox/p06.png
   :align: center

Now that the machine is created it can be started by selecting it and pressing the "Start" button.

.. image:: _static/virtualbox/p07.png
   :align: center

When starting, the VirtualBox show various informational messages that are not very important. One of them is about "host key". When
you are working on the virtual machine and press the host key, the virtualization software passes the focus from the guest to the host. The default key is right "Control".

.. image:: _static/virtualbox/p08.png
   :align: center

Ubuntu/Linux installation
-------------------------

The next step is to install a version of Ubuntu/Linux. When starting the virtual machine you will be asked to find an image of the operating system.

Then you have to press the button to select the image file full of Ubuntu you downloaded eralier: ubuntu-12.04.1-desktop-i386.iso.

.. image:: _static/virtualbox/p09.png
   :align: center

And finally we just have to press the *Start*  button to start the installation.

.. image:: _static/virtualbox/p10.png
   :align: center

You can choose any language for the installation by selecting it from the list on the left.

.. image:: _static/virtualbox/p11.png
   :width: 700 px
   :align: center

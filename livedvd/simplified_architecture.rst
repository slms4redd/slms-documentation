Training Machine Simplified Architecture
========================================

The UNREDD NMFS platform provides both a *staging* and a *dissemination* areas,
so the data can be reviewed and prior to its definite publication.

.. figure:: img/simplified_architecture.png
   :width: 1200

   LiveDVD simplified architecture
      
components not present in the Training machine have been grayed out in the figure.
Mainly, the dissemination block has been removed. This simplified version
contains all the building blocks needed to fully understand the platform, while
reducing the complexities needed only in real production environments.

In this case, both staging and dissemination areas share the same geoserver instance,
so the publishing flow, is copying the data from the *staging* workspace to tne
*dissemination* one in the same machine.

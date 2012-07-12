Simplified Architecture in LiveDVD
==================================

The UNREDD NMFS platform provides both a *staging* and a *dissemination* areas, so the data can be imported and reviewed previous to its definite publication in a separate environment.

.. figure:: img/simplified_architecture.png
   :width: 1200

   LiveDVD simplified architecture
      
The components not present in the LiveDVD have been grayed out in the figure, where the dissemination block has been removed. This simplified version contains all the building blocks needed to fully understand the platform, while reduces the complexity needed only in real production environments.

In this case, the publishing operation, which copies the data from the *staging* to the *dissemination* area, has been removed, and the User Portal is directly connected to the staging GeoServer and GeoStore instances.


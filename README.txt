
On GeoBatch chapter
===================

The "geobatch/external/" contents are pulled from (using subtree merge strategy):
https://github.com/geosolutions-it/geobatch/tree/master/src/doc/source

Please don't edit "geobatch/external/" contents here, but in the original GB repo. Then pull changes here using the git commands::

  git remote add gb_external git@github.com:geosolutions-it/geobatch.git
  git pull -X subtree=src/doc/source gb_external master

geobatch/index.rst will point to the chapters we need for UNREDD training, and some other project-specific ones.  


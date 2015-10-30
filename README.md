#### What's this?
This is the SLMS sphinx documentation deployed [here](http://slms4redd.github.io/doc/html/index.html).
Plese refer to the SLMS organization github-pages [README](https://github.com/slms4redd/slms4redd.github.io/blob/master/README.md) for deployment information

#### Build & Run

You need [sphinx](http://sphinx-doc.org/) to generate the HTML pages from this **reStructuredText (reST)** documentation

##### Install sphinx

Thanks to Geoserver community for [this](http://docs.geoserver.org/latest/en/docguide/install.html) handy tutorial.

BTW thanks also for this [reST syntax overview](http://docs.geoserver.org/latest/en/docguide/sphinx.html) :)

##### Build

Once sphinx is installed in your system from the root of this repo run

```
$# make.sh html
```

To create the HTML documentation. Please note that you are re-generating only the modified pages from the previously build.

In order to regenerate all the documentation form scratch run

```
$# make.sh clean
$# make.sh html
```

(Replace .sh with .bat if you are on Windows)

##### Run

The HTML produced is stored in the .build/html. 

Assuming the repo is cloned in the `/home/fao` directory open your browser at the (local to FS) URL

```
/home/fao/slms-documentation/.build/html/index.html
```

and... enjoy the documentation :)

#### Notes on GeoBatch chapter

The "geobatch/external/" contents are pulled from (using subtree merge strategy):
https://github.com/geosolutions-it/geobatch/tree/master/src/doc/source

Please don't edit "geobatch/external/" contents here, but in the original GB repo. Then pull changes here using the git commands::

  git remote add gb_external git@github.com:geosolutions-it/geobatch.git
  git pull -X subtree=src/doc/source gb_external master

geobatch/index.rst will point to the chapters we need for UNREDD training, and some other project-specific ones.  


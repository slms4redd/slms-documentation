#!/bin/bash

# Command file for Sphinx documentation

echo Experimental bash script  PARAM: \"$1\"

SPHINXBUILD=sphinx-build
ALLSPHINXOPTS="-d .build/doctrees $SPHINXOPTS ."

if [ ! -z "$PAPER" ] ; then
	ALLSPHINXOPTS="-D latex_paper_size=$PAPER $ALLSPHINXOPTS"
fi

if [ -z "$1" -o  "help" = "$1"  ] ; then 
	echo Usage: 
        echo "     make.sh [TARGET]"
        echo  Where target is one of:
	echo  "   html      to make standalone HTML files"
	echo  "   dirhtml   to make HTML files named index.html in directories"
	echo  "   pickle    to make pickle files"
	echo  "   json      to make JSON files"
	echo  "   htmlhelp  to make HTML files and a HTML help project"
	echo  "   qthelp    to make HTML files and a qthelp project"
	echo  "   latex     to make LaTeX files, you can set PAPER=a4 or PAPER=letter"
	echo  "   changes   to make an overview over all changed/added/deprecated items"
	echo  "   linkcheck to check all external links for integrity"
	echo  "   doctest   to run all doctests embedded in the documentation if enabled"
	echo  "   clean     remove the current build data"
	exit 1
fi

case "$1" in
   clean*)
	echo Removing the .build directory...
	rm -rf .build/
        echo Done.
	;;

   html*)
	$SPHINXBUILD -b html $ALLSPHINXOPTS .build/html
	echo
	echo Build finished. The HTML pages are in .build/html.
	;;

   dirhtml*)
	$SPHINXBUILD -b dirhtml $ALLSPHINXOPTS .build/dirhtml
	echo
	echo Build finished. The HTML pages are in .build/dirhtml.
	;;

   pickle*)
	$SPHINXBUILD -b pickle $ALLSPHINXOPTS .build/pickle
	echo
	echo Build finished. Now you can process the pickle files.
	;;

   json*)
	echo NOT IMPLEMENTED YET
#	%SPHINXBUILD% -b json %ALLSPHINXOPTS% .build/json
#	echo.
#	echo.Build finished; now you can process the JSON files.
#	goto end
	;;

# if "%1" == "htmlhelp" (
#	%SPHINXBUILD% -b htmlhelp %ALLSPHINXOPTS% .build/htmlhelp
#	echo.
#	echo.Build finished; now you can run HTML Help Workshop with the ^
#.hhp project file in .build/htmlhelp.
#	goto end
#)
#
#if "%1" == "qthelp" (
#	%SPHINXBUILD% -b qthelp %ALLSPHINXOPTS% .build/qthelp
#	echo.
#	echo.Build finished; now you can run "qcollectiongenerator" with the ^
#.qhcp project file in .build/qthelp, like this:
#	echo.^> qcollectiongenerator .build\qthelp\GeoServer.qhcp
#	echo.To view the help file:
#	echo.^> assistant -collectionFile .build\qthelp\GeoServer.ghc
#	goto end
#)
#
#if "%1" == "latex" (
#	%SPHINXBUILD% -b latex %ALLSPHINXOPTS% .build/latex
#	echo.
#	echo.Build finished; the LaTeX files are in .build/latex.
#	goto end
#)
#
    changes*)
	$SPHINXBUILD -b changes $ALLSPHINXOPTS .build/changes
	echo
	echo The overview file is in .build/changes.
	;;

    linkcheck*)
	$SPHINXBUILD -b linkcheck $ALLSPHINXOPTS .build/linkcheck
	echo
	echo Link check complete, look for any errors in the above output or in .build/linkcheck/output.txt
	;;

    doctest*)
	$SPHINXBUILD -b doctest $ALLSPHINXOPTS .build/doctest
	echo
	echo Testing of doctests in the sources finished, look at the results in .build/doctest/output.txt
	;;


    pdf*)
	$SPHINXBUILD -b pdf $ALLSPHINXOPTS .build/pdf
	echo
	echo Build finished. The PDF files are in .build/pdf.
	;;

esac

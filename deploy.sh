#!/bin/bash
if [ -e seasons.tar.gz ]; then
    rm seasons.tar.gz
fi
if [ -e seasons.zip ]; then
    rm seasons.zip
fi
mkdir seasons
cp -r seasons.jar assets seasons
mkdir seasons/lib
cp -r lib/linux lib/macosx lib/windows seasons/lib
mkdir seasons/log
tar -cvvf seasons.tar seasons
gzip seasons.tar
zip -r seasons.zip seasons
rm -rf seasons/
#!/bin/bash
if [ -e shamanbear.tar.gz ]; then
    rm shamanbear.tar.gz
fi
if [ -e shamanbear.zip ]; then
    rm shamanbear.zip
fi
mkdir shamanbear
cp -r shamanbear.jar assets shamanbear
mkdir shamanbear/lib
cp -r lib/linux lib/macosx lib/windows shamanbear/lib
mkdir shamanbear/log
tar -cvvf shamanbear.tar shamanbear
gzip shamanbear.tar
zip -r shamanbear.zip shamanbear
rm -rf shamanbear/
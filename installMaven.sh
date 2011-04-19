#!/bin/sh

mavenArchive=apache-maven-2.2.1-bin.tar.gz;
mavenUrl=http://www.eu.apache.org/dist//maven/binaries/$mavenArchive;

echo 'Downloading maven';
curl -O -4 -R -# $mavenUrl;

echo;
echo 'Extracting:';
tar -zxvf $mavenArchive;

mavenDirectory=apache-maven-2.2.1;
m2HomeExport="export M2_HOME=`pwd`/apache-maven-2.2.1";
pathExport='export PATH=$M2_HOME/bin:$PATH';
echo;
echo 'For maven to work, M2_HOME must be defined and PATH must contain it';
echo 'For current maven location you can use the following exports:';
echo $m2HomeExport;
echo $pathExport;
echo;
eval $m2HomeExport;
eval $pathExport;
echo 'If you source-d this script, the mentioned exports have been made.';
echo 'To check if maven works, run mvn --version';

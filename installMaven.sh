#!/bin/sh

mavenArchive=apache-maven-2.2.1-bin.tar.gz
mavenUrl=http://www.eu.apache.org/dist//maven/binaries/$mavenArchive

echo 'Downloading maven'
curl -O -4 -R -# $mavenUrl

echo
echo 'Extracting:'
tar -zxvf $mavenArchive

mavenDirectory=apache-maven-2.2.1
echo
echo 'For maven to work, M2_HOME must be defined and PATH must contain it'
echo 'For current maven location you can use the following exports:'
echo "export M2_HOME=`pwd`/apache-maven-2.2.1"
echo 'export PATH=$M2_HOME/bin:$PATH'
echo
echo 'To check if maven works, run mvn --version'

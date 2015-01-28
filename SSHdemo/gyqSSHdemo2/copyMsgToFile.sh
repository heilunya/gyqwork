#!/bin/bash 
#./copyMsgToFile.sh
cd /
mkdir -p gyqNewFolder
cd gyqNewFolder
touch testfile
echo $1 >> testfile
cat testfile

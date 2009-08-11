#!/bin/bash
cd ..
ant make-jar
cd test
rm -Rf javachains
hadoop jar ../dist/hadoop02.jar

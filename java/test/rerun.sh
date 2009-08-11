#!/bin/bash
cd ..
ant make-jar
cd test
rm -Rf chains
hadoop jar ../dist/hadoop02.jar

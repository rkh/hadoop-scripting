This code has been written by Johan Uhle and Konstantin Haase during the summer term 2009 at Hasso Plattner Institute, Universität Potsdam, D-14482 Potsdam, Germany, in the seminar “Map/Reduce Algorithms on Hadoop” supervised by Alexander Albrecht and Prof. Felix Naumann.

Since this is not an eclipse (nor a java) project, we did not provide an Ant file.

For running wordcount benchmarks:
Make sure you have setup hadoop, jaql and pig.
  ./wordcount.sh

For runnning markov benchmarks:
Make sure you have setup hadoop and pig.
Execute ./markov.sh

For generating the pdf:
Make sure you have latex, gnuplot and ruby installed.
Execute rake

For generating the Pig UDF:
cd pig/splitsuc
javac -cp pig.jar SPLITSUC.java STORESQL.java
cd ..
jar -cf splitsuc.jar splitsuc
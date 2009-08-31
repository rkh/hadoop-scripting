#!/bin/bash

SIZES="10 50 100 500 1000 2000 3000 4000 5000 3000 4000"


function time_that() {
  rm $HOME/logs/$3_$1_mb_$2_run
  /usr/bin/time -a -o $HOME/logs/$3_$1_mb_$2_run $4 1>>$HOME/logs/$3_$1_mb_$2_run 2>>$HOME/logs/$3_$1_mb_$2_run
}

function run_hadoop() {
  echo "Running Hadoop on $1 MB in run no $2"
  hadoop fs -rmr javachains
  time_that $1 $2 "java" "hadoop jar /home/hadoop02/hadoop02.jar"
}

function run_pig() {
  echo "Running Pig on $1 MB in run no $2"
  hadoop fs -rmr wikipedia.sql
  current_wd="$(pwd)"
  cd /home/hadoop02/pig
  time_that $1 $2 "pig" "pig /home/hadoop02/hadoop-scripting/pig/splitsuc/splitsuc.pig"
  cd "$current_wd"
}

function run_jaql() {
  echo "Running Jaql on $1 MB in run no $2"
}

for i in $SIZES; do
  dd if=corpera/full.out of=current.dat bs=1M count=$i
  hadoop fs -rm corpera/current.dat
  hadoop fs -put current.dat corpera/current.dat
  if [ $SIZE -eq 3000 ]; then
    for j in `seq 3 4`; do
      run_hadoop $i $j
      run_pig $i $j
      #run_jaql $i $j
    done
  else
    for j in `seq 1 4`; do
      run_hadoop $i $j
      run_pig $i $j
      #run_jaql $i $j
    done
  fi
  done
done


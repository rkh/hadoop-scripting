register splitsuc.jar;

corpera = LOAD 'corpera' USING PigStorage('\t') AS (row:chararray);
tuples = FOREACH corpera GENERATE FLATTEN(splitsuc.SPLITSUC(*));
grouped = GROUP tuples by (keyTuple, successorTuple);
grouped_counted = FOREACH grouped GENERATE group, COUNT(tuples);
final = order grouped_counted by $0 parallel 10; 
STORE final INTO 'wikipedia.sql' USING splitsuc.STORESQL();
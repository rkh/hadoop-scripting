register splitsuc.jar;

corpera = LOAD 'corpera' USING PigStorage('\t') AS (row:chararray);
tuples = FOREACH corpera GENERATE FLATTEN(splitsuc.SPLITSUC(*));
grouped = GROUP tuples by (word1, word2, word3, word4) PARALLEL 1;
grouped_counted = FOREACH grouped GENERATE group, COUNT(tuples);
STORE grouped_counted INTO 'wikipedia.sql' USING splitsuc.STORESQL();
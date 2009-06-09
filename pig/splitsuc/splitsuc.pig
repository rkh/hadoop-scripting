register splitsuc.jar;
wikipedia = LOAD 'wikipedia' USING PigStorage() AS (row:chararray);
tuples = FOREACH wikipedia GENERATE FLATTEN(splitsuc.SPLITSUC(*));
grouped = GROUP tuples by keyTuple;
grouped_clean = FOREACH grouped GENERATE group, $1.successor;
STORE grouped_clean INTO 'grouped_clean';
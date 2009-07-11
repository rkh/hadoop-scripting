register splitsuc.jar;
wikipedia = LOAD 'corpera' USING PigStorage('\t') AS (row:chararray);
wikipedia2 = LIMIT wikipedia 1;
tuples = FOREACH wikipedia GENERATE FLATTEN(splitsuc.SPLITSUC(*));

DESCRIBE tuples;
--DUMP tuples;

--grouped = GROUP tuples by keyTuple;

--DESCRIBE grouped;
--DUMP grouped;

--grouped_clean = FOREACH grouped GENERATE group, FLATTEN($1.$1);
--DESCRIBE grouped_clean;
group_final = GROUP tuples by (keyTuple, successorTuple);
DESCRIBE group_final;
group_final_counted = FOREACH group_final GENERATE group, COUNT(tuples);
DESCRIBE group_final_counted;
final = order group_final_counted by $0 parallel 10; 

DESCRIBE final;
--DUMP final;

STORE final INTO 'grouped_clean' USING splitsuc.STORESQL();



-- DUMP grouped;

/*index = FOREACH grouped_clean {
	word = DISTINCT $1;
	--count = COUNT(word);
	GENERATE $0, FLATTEN(word);
};

--result = FOREACH index GENERATE FLATTEN($1);

--result2 = GROUP result ALL;

STORE index INTO 'results';*/
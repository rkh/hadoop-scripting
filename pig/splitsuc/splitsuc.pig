register splitsuc.jar;
wikipedia = LOAD 'wikipedia' USING TextLoader() AS (row:chararray);
--wikipedia = LIMIT wikipedia 1;
tuples = FOREACH wikipedia GENERATE FLATTEN(splitsuc.SPLITSUC(*));

--DESCRIBE tuples;
--DUMP tuples;

grouped = GROUP tuples by keyTuple;

--DESCRIBE grouped;

grouped_clean = FOREACH grouped GENERATE group, FLATTEN($1.$1);

--DESCRIBE grouped_clean;

--EXPLAIN grouped_clean;

STORE grouped_clean INTO 'grouped_clean' USING splitsuc.STORESQL();



-- DUMP grouped;

/*index = FOREACH grouped_clean {
	word = DISTINCT $1;
	--count = COUNT(word);
	GENERATE $0, FLATTEN(word);
};

--result = FOREACH index GENERATE FLATTEN($1);

--result2 = GROUP result ALL;

STORE index INTO 'results';*/
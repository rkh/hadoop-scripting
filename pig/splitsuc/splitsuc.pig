register splitsuc.jar;
wikipedia = LOAD 'wikipedia' USING PigStorage() AS (row:chararray);
--wikipedia = LIMIT wikipedia 2;
tuples = FOREACH wikipedia GENERATE FLATTEN(splitsuc.SPLITSUC(*));

DESCRIBE tuples;

grouped = GROUP tuples by word;

DESCRIBE grouped;

grouped_clean = FOREACH grouped GENERATE group, $1.successor;

DESCRIBE grouped_clean;

STORE grouped_clean INTO 'grouped_clean';



-- DUMP grouped;

/*index = FOREACH grouped_clean {
	word = DISTINCT $1;
	--count = COUNT(word);
	GENERATE $0, FLATTEN(word);
};

--result = FOREACH index GENERATE FLATTEN($1);

--result2 = GROUP result ALL;

STORE index INTO 'results';*/
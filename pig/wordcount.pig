raw = LOAD 'corpera' USING PigStorage() AS (row:chararray);
words = FOREACH raw GENERATE FLATTEN(TOKENIZE(*));
grouped = GROUP words BY $0;
counts = FOREACH grouped GENERATE COUNT(words), group;
ordered = ORDER counts by $0 DESC;
STORE ordered INTO 'pig';


/*
 * This first run will isolate and sum-up the links from the input pages
 * It will also beautify them, removing brackets, stars and different writing
 */

package de.uni_potsdam.hpi.hadoop02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class ChainUp {
	static boolean stemming = true;

	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, IntWritable> {

		public void map(LongWritable key, Text value,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {

			ArrayList<String> words = new ArrayList<String>(4);
			StringTokenizer tokenizer = new StringTokenizer(value.toString());
			Text okey = new Text();
			IntWritable one = new IntWritable(1);

			while (tokenizer.hasMoreTokens()) {
				words.add("'"+tokenizer.
						nextToken().
						replaceAll("\\\\", "\\\\\\\\").
				    	replaceAll("'", "\\\\'")+"'");
				if (words.size() == 4) {
					for (String word : words) {
						okey.set(okey.toString()+word+",");
					}
					output.collect(okey, one);
					okey.clear();
					words.clear();
				}
			}
		}
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterator<IntWritable> inValues,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {

			String sqlhead = "INSERT INTO javatest.splitsuc VALUES ";
			Text outsql = new Text();
			IntWritable outcount = new IntWritable();
			
			int sum = 0;
			
		    while (inValues.hasNext()) {
		      sum += inValues.next().get();
		    }
		    
		    outcount.set(sum);
		    outsql.set(sqlhead + key.toString());
		   
		    output.collect(outsql, outcount);
		}
	}

	public static void run() throws Exception {

		JobConf conf = new JobConf(ChainUp.class);
		conf.setJobName("markov_chain");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);

		conf.setMapperClass(Map.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path("input"));
		FileOutputFormat.setOutputPath(conf, new Path("chains"));

		JobClient.runJob(conf);
	}
}

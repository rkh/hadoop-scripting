package de.uni_potsdam.hpi.hadoop02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MarkovChains implements Tool {

	/**
	 * @param args
	 * @throws Exception
	 */
	public int run(String[] args) throws Exception {
		ChainUp.run();
		return 0;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new MarkovChains(), args);
	}

	public Configuration getConf() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConf(Configuration arg0) {
		// TODO Auto-generated method stub

	}
}

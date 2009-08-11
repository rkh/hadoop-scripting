package splitsuc;

import java.io.IOException;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.data.DataType;

public class SPLITSUC extends EvalFunc<DataBag> {
    TupleFactory mTupleFactory = TupleFactory.getInstance();
    BagFactory mBagFactory = BagFactory.getInstance();
		Integer numberOfWords = 4;

		public void myDebug(String message) {
			if(false) {
				System.err.println(message);
			}
			return;
		}

    public DataBag exec(Tuple input) throws IOException {

      if (input == null) {
				System.err.println("Input is null");
      	return null;
			}
      try {
	      DataBag output = mBagFactory.newDefaultBag();
	      Object o = input.get(0);
	      if (!(o instanceof String)) {
	          throw new IOException("Expected input to be chararray, but got " + o.getClass().getName());
	      }
				String[] words = o.toString().split("\\s|&#160;");
				LinkedList<String> suc_words = new LinkedList<String>();
				for (String word : words) {
				  if (!word.equals("")) {			
						if (suc_words.size() >= numberOfWords) {
							ArrayList<Tuple> currentList = new ArrayList<Tuple>();                                                                                               
						
							output.add(mTupleFactory.newTuple(suc_words));
							suc_words.removeFirst();
						}
						suc_words.addLast(word);
					}
				}
	       return output;
			} catch (Exception e) {
				System.err.println("Failed to process input in SplitSuc; error - " + e.getMessage());
        return null;
 			}
		}

    public Schema outputSchema(Schema input) {
      try{

				Schema wordsTupleSchema = new Schema();

				for (int count=1; count <= numberOfWords; count++) {
					wordsTupleSchema.add(new Schema.FieldSchema("word" + Integer.toString(count), DataType.CHARARRAY));
				}
				Schema.FieldSchema wordsTupleFs;
				wordsTupleFs = new Schema.FieldSchema("keyTuple", wordsTupleSchema, DataType.TUPLE);
                                       
        Schema bagSchema = new Schema(wordsTupleFs);
        bagSchema.setTwoLevelAccessRequired(true);
        Schema.FieldSchema bagFs = new Schema.FieldSchema("BagOfWordsWithSuccessor",bagSchema, DataType.BAG);

        return new Schema(bagFs); 

      }catch (Exception e){
				System.err.println("Failed to process Output Schema; error - " + e.getMessage());
        return null;
      }
		}
}


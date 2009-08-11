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
				myDebug("Input: " + o.toString());      	
				String[] words = o.toString().split("\\s|&#160;");
				myDebug("Split String: " + words.toString());
				LinkedList<String> suc_words = new LinkedList<String>();
				for (String word : words) {
					myDebug("Current Word: " + word.toString());
				  if (!word.equals("")) {			
						if (suc_words.size() >= numberOfWords) {
							ArrayList<Tuple> currentList = new ArrayList<Tuple>();                                                                                               

							/*							
							// create key tuple
							Tuple keyTuple = mTupleFactory.newTuple(keys.size());
							for (int count=0; count < keys.size(); count++) {
								keyTuple.set(count, keys.get(count));
							}
							myDebug("KeyTuple: " +  (keyTuple.toDelimitedString("; ")).toString());
							currentList.add(keyTuple);
							
							// create word tuple
							Tuple wordTuple = mTupleFactory.newTuple(1);
							wordTuple.set(0, word);
							currentList.add(wordTuple);
							myDebug("WordTuple: " +  (wordTuple.toDelimitedString("; ")).toString());

							myDebug("CurrentList: " +  (currentList.get(0).toDelimitedString(";")).toString() + " -> " + (currentList.get(1).toDelimitedString(";")).toString());*/
							
							output.add(mTupleFactory.newTuple(suc_words));
							suc_words.removeFirst();
						}
						suc_words.addLast(word);
						myDebug("Words List: " + suc_words.toString());
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
	
/*				Schema keyTupleSchema = new Schema();
				
				myDebug("Number of Keys: " + Integer.toString(numberOfWords));
								
				for (int count=1; count <= numberOfWords; count++) {
					keyTupleSchema.add(new Schema.FieldSchema("word" + Integer.toString(count), DataType.CHARARRAY));
				}
				Schema.FieldSchema keyTupleFs;
        keyTupleFs = new Schema.FieldSchema("keyTuple", keyTupleSchema, DataType.TUPLE);
				
				Schema successorSchema = new Schema();
				successorSchema.add(new Schema.FieldSchema("successor", DataType.CHARARRAY));
				Schema.FieldSchema successorTupleFs;
        successorTupleFs = new Schema.FieldSchema("successorTuple", successorSchema, DataType.TUPLE);

        Schema tupleSchema = new Schema();
				tupleSchema.add(keyTupleFs);
				tupleSchema.add(successorTupleFs);
        Schema.FieldSchema tupleFs;
        tupleFs = new Schema.FieldSchema("keySuccessorTuple", tupleSchema, DataType.TUPLE);

        Schema bagSchema = new Schema(tupleFs);
        bagSchema.setTwoLevelAccessRequired(true);
        Schema.FieldSchema bagFs = new Schema.FieldSchema("BagOfSuccessorTuple",bagSchema, DataType.BAG);  */         

				myDebug("Schema Number of Keys: " + Integer.toString(numberOfWords));                            

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


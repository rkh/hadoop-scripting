/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitsuc;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
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

    public DataBag exec(Tuple input) throws IOException {

      if (input == null)
      	return null;
      try {
	      DataBag output = mBagFactory.newDefaultBag();
	      Object o = input.get(0);
	      if (!(o instanceof String)) {
	          throw new IOException("Expected input to be chararray, but  got " + o.getClass().getName());
	      }
      
				String[] words = ((String)o).split("\\W");
				String last = "";
				for (String word : words) {
				  if (!word.equals("")) {				
						if (last != "") {
							List<String> currentList = new ArrayList<String>();
							currentList.add(last);
							currentList.add(word);								
							output.add(mTupleFactory.newTuple(currentList));
						}
					last = word;
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
        Schema.FieldSchema word = new Schema.FieldSchema("word", DataType.CHARARRAY); 

       	Schema.FieldSchema successor = new Schema.FieldSchema("successor", DataType.CHARARRAY);

        Schema tupleSchema = new Schema();
				tupleSchema.add(word);
				tupleSchema.add(successor);

        Schema.FieldSchema tupleFs;
        tupleFs = new Schema.FieldSchema("tuple_of_tokens", tupleSchema,
                DataType.TUPLE);

        Schema bagSchema = new Schema(tupleFs);
        bagSchema.setTwoLevelAccessRequired(true);
        Schema.FieldSchema bagFs = new Schema.FieldSchema(
                    "bag_of_tokenTuples",bagSchema, DataType.BAG);
        
        return new Schema(bagFs); 

      }catch (Exception e){
              return null;
      }
		}
}


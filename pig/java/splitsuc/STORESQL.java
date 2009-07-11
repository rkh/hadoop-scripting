package splitsuc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;

import org.apache.pig.StoreFunc;
import org.apache.pig.data.Tuple;


public class STORESQL implements StoreFunc {
    OutputStream os;
    private byte recordDel = (byte)'\n';
		private int currentTupleNumber = 0;
		private String sqlHead = "INSERT INTO pigtest.splitsuc VALUES";

		public void myDebug(String message) {
			if(false) {
				System.err.println(message);
			}
			return;
		}

    public void bindTo(OutputStream os) throws IOException
    {
        this.os = os;
				String output = this.sqlHead + (char)this.recordDel;
				this.os.write(output.getBytes("utf8"));
    }
    public void putNext(Tuple t) throws IOException
    {
				List<Object> mainTuple = new ArrayList<Object>();
				mainTuple = t.getAll();
				
				List<Object> groupTuple = new ArrayList<Object>();
				groupTuple = ((Tuple)mainTuple.get(0)).getAll();
				
				List<Object> keyTuple = new ArrayList<Object>();
				keyTuple = ((Tuple)groupTuple.get(0)).getAll();
				
				myDebug("For count " + Integer.toString(this.currentTupleNumber) + " is 0 " + keyTuple.get(0).toString());
				
				String output = "('";
				output = output + escapeForSql(keyTuple.get(0).toString());
				output = output + "', '";
				output = output + escapeForSql(keyTuple.get(1).toString());
				output = output + "', '";
				output = output + escapeForSql(keyTuple.get(2).toString());
				output = output + "', '";		
				output = output + escapeForSql(((Tuple)groupTuple.get(1)).get(0).toString());
				output = output + "', '";
				output = output + escapeForSql(mainTuple.get(1).toString());

				this.currentTupleNumber = this.currentTupleNumber + 1;				
				if (this.currentTupleNumber >= 1000) {
					output = output + "');" + (char)this.recordDel;
					output = output + (char)this.recordDel;
					output = output + this.sqlHead + (char)this.recordDel;			
					this.currentTupleNumber = 0;		
				}
				else {
					output = output + "'),";
					output = output + (char)this.recordDel;
				}
				
				os.write(output.getBytes("utf8"));
    }
    public void finish() throws IOException
    {
				this.os.write("('DELETEME', 'DELETEME', 'DELETEME', 'DELETEME', 1);".getBytes("utf8"));
				this.os.flush();
    }

    @Override
    public Class getStorePreparationClass() throws IOException {
        return null;
    }

		private String escapeForSql(String s) {
			String output = "";
			output = s.replaceAll("\\\\", "\\\\\\\\");
			output = output.replaceAll("'", "\\\\'");
			return output;
		}
}
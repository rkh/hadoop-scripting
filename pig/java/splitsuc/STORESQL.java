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

    public void bindTo(OutputStream os) throws IOException
    {
        this.os = os;
				String output = "INSERT INTO table VALUES";
				output = output + (char)this.recordDel;				
				this.os.write(output.getBytes("utf8"));
    }
    public void putNext(Tuple t) throws IOException
    {
				List<Object> sourceTuple = new ArrayList<Object>();
				sourceTuple = t.getAll();
				
				List<Object> keyTuple = new ArrayList<Object>();
				keyTuple = ((Tuple)sourceTuple.get(0)).getAll();
				
				String output = "('";
				output = output + keyTuple.get(0).toString();
				output = output + "', '";
				output = output + keyTuple.get(1).toString();
				output = output + "', '";
				output = output + keyTuple.get(2).toString();
				output = output + "', '";		
				output = output + ((Tuple)sourceTuple.get(1)).get(0).toString();
				output = output + "'),";
				output = output + (char)this.recordDel;
				
				os.write(output.getBytes("utf8"));
    }
    public void finish() throws IOException
    {
				os.write("('DELETEME', 'DELETEME', 'DELETEME', 'DELETEME');".getBytes("utf8"));
				os.flush();
    }

    @Override
    public Class getStorePreparationClass() throws IOException {
        return null;
    }
}
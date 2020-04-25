import java.nio.ByteBuffer;
import java.util.Arrays; 

public class Record implements Comparable<Record> {
    private byte[] record; 
    private int runNumber;
    
    public Record(byte[] record)
    {
        this.record = record; 
    }
    
    public Record(byte[] record, int runNumber)
    {
        this.record = record; 
        this.runNumber = runNumber;
    }
    
    public int getRunNumber()
    {
        return runNumber; 
    }
    
    public byte[] getRawRecord()
    {
        return record; 
    }
    
    public long getID()
    {
        byte[] rawID = Arrays.copyOfRange(record, 0, 8); 
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawID); 
        return byteBuffer.getLong(); 
    }
    
    public double getKey()
    {
        byte[] rawKey = Arrays.copyOfRange(record, 8, 16); 
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawKey); 
        return byteBuffer.getDouble(); 
    }

    @Override
    public int compareTo(Record o) {
        //System.out.println(o); 
        if (getKey() < o.getKey())
            return -1; 
        else if (getKey() > o.getKey())
            return 1; 
        else 
            return 0; 
    }
    
    @Override
    public String toString()
    {
        return Double.toString(getKey()); 
    }
    

}

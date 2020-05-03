import java.nio.ByteBuffer;
import java.util.Arrays; 

/**
 * Record class
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 *
 */
public class Record implements Comparable<Record> {
    private byte[] recArr; 
    private int runNumber;
    
    /**
     * Record one argument constructor
     * @param record byte array of record
     */
    public Record(byte[] record)
    {
        this.recArr = record; 
    }
    
    /**
     * Record two argument constructor
     * @param record byte array of record
     * @param runNumber run number of record
     */
    public Record(byte[] record, int runNumber)
    {
        this.recArr = record; 
        this.runNumber = runNumber;
    }
    
    /**
     * Gets record run number
     * @return run number
     */
    public int getRunNumber()
    {
        return runNumber; 
    }
    
    /**
     * Gets byte array of record
     * @return byte array of record
     */
    public byte[] getRawRecord()
    {
        return recArr; 
    }
    
    /**
     * Gets ID of record
     * @return ID
     */
    public long getID()
    {
        byte[] rawID = Arrays.copyOfRange(recArr, 0, 8); 
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawID); 
        return byteBuffer.getLong(); 
    }
    
    /**
     * Gets key of record
     * @return key
     */
    public double getKey()
    {
        byte[] rawKey = Arrays.copyOfRange(recArr, 8, 16); 
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawKey); 
        return byteBuffer.getDouble(); 
    }

    /**
     * compareTo method of Record class
     * 
     * @param o record to compare to
     * @return negative one if smaller than param,
     *          zero if equivalent,
     *          one if greater then param
     */
    @Override
    public int compareTo(Record o) {
        if (getKey() < o.getKey())
        {
            return -1; 
        }
        else if (getKey() > o.getKey())
        {
            return 1; 
        }
        else 
        {
            return 0; 
        }
    }
    
    /**
     * Gets string representation (key) of record
     * @return record key
     */
    @Override
    public String toString()
    {
        return Double.toString(getKey()); 
    }
    

}

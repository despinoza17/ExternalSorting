import java.util.Arrays;

/**
 * Input ByteBuffer
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 *
 */
public class InputBuffer {
    private byte[] buffer;
    private int offset;
    
    /**
     * InputBuffer's one argument constructor
     * @param arr byte array buffer
     */
    public InputBuffer(byte[] arr) {
        buffer = arr; 
        offset = 0; 
    }
    
    /**
     * Gets the next record in the byte buffer
     * @return record in byte array
     */
    public byte[] getNextRecordArr() {
        byte[] arr = Arrays.copyOfRange(buffer, offset, offset + 16);
        offset += 16; 
        return arr; 
    }
    
    /**
     * Clears the buffer
     */
    public void clearBuffer()
    {
        buffer = null; 
        offset = 0;
    }
    
    /**
     * Adds byte array to input buffer
     * @param arr byte array
     */
    public void addNextBlock(byte[] arr)
    {
        buffer = arr;
        offset = 0; 
    }
    
    /**
     * Checks if input buffer is empty
     * @return true if is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return offset == 8192 || buffer == null; 
    }
    

}

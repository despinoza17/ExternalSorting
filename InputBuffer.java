import java.util.Arrays;

public class InputBuffer {
    private byte[] buffer;
    private int offset;
    
    public InputBuffer(byte[] arr) {
        buffer = arr; 
        offset = 0; 
    }
    
    public byte[] getNextRecordArr() {
        byte[] arr = Arrays.copyOfRange(buffer, offset, offset + 16);
        offset += 16; 
        return arr; 
    }
    
    public void clearBuffer()
    {
        buffer = null; 
        offset = 0;
    }
    
    public void addNextBlock(byte[] arr)
    {
        buffer = arr;
        offset = 0; 
    }
    
    public boolean isEmpty()
    {
        return offset == 8192 || buffer == null; 
    }
    

}

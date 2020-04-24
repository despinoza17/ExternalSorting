import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class OutputBuffer {
    private byte[] buffer; 
    private int offset; 
    private RandomAccessFile runFile; 
    private File rF; 
    
    public OutputBuffer() throws FileNotFoundException
    {
        rF = new File("runFile.bin"); 
        runFile = new RandomAccessFile(rF, "rw"); 
        buffer = new byte[8192];
        offset = 0; 
    }
    
    public void insertRecordArr(byte[] arr)
    {
        int counter = 0; 
        for (int i = offset; i < offset + 16; i++)
        {
            buffer[i] = arr[counter]; 
            counter++; 
        }
        offset += 16; 
    }
    
    public boolean isFilled()
    {
        return offset == 8192; 
    }
    
    public byte[] getLastRecordArr()
    {
        return Arrays.copyOfRange(buffer, offset - 16, offset); 
    }
    
    public byte[] getArr()
    {
        return buffer; 
    }
    
    public void unloadRun() throws IOException
    {
        runFile.write(buffer);
        buffer = new byte[8192]; 
        offset = 0;
    }
    
    public void closeRunFile() throws IOException
    {
        runFile.close(); 
    }
    
    
}

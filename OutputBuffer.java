import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Output byte buffer
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 */
public class OutputBuffer {
    private byte[] buffer; 
    private int offset; 
    private RandomAccessFile runFile; 
    
    /**
     * OutputBuffer default constructor
     * @throws FileNotFoundException
     */
    public OutputBuffer() throws FileNotFoundException
    {
        File rF = new File("runFile.bin"); 
        runFile = new RandomAccessFile(rF, "rw"); 
        buffer = new byte[8192];
        offset = 0; 
    }
    
    /**
     * Inserts record byte array to output buffer
     * @param arr record byte array
     */
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
    
    /**
     * Checks if output buffer if filled
     * @return true if filled, false 
     *      otherwise
     */
    public boolean isFilled()
    {
        return offset == 8192; 
    }
    
    /**
     * Checks if output buffer is empty
     * @return true if empty, false 
     *      otherwise
     */
    public boolean isEmpty()
    {
        return offset == 0; 
    }
    
    /**
     * Gets the last inserted record in the 
     * output buffer
     * @return last inserted byte record array
     */
    public byte[] getLastRecordArr()
    {
        return Arrays.
                copyOfRange(buffer, offset - 16, offset); 
    }
    
    /**
     * Gets byte buffer array
     * @return byte buffer array
     */
    public byte[] getArr()
    {
        return buffer; 
    }
    
    /**
     * Unloads run to run file
     * @throws IOException
     */
    public void unloadRun() throws IOException
    {
        runFile.write(buffer, 0, offset);
        buffer = new byte[8192]; 
        offset = 0;
    }
    
    /**
     * Closes run file
     * @throws IOException
     */
    public void closeRunFile() throws IOException
    {
        runFile.close(); 
    }
    
    /**
     * Prints output buffer
     */
    public void printOutputBuffer()
    {
        Parser.printBlock(getArr());
    }
    
    
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Parser {
    private RandomAccessFile reader; 
    private long offset; 
    private int read; 
    
    public Parser(File file) throws FileNotFoundException
    {
        reader = new RandomAccessFile(file, "rw"); 
        offset = 0; 
    }
    
    public static void printBlock(byte[] arr)
    {
        for (int i = 0; i < arr.length; i+=16)
        {
            System.out.println(new Record(Arrays.copyOfRange(arr, i, i + 16))); 
        }
    }
    
    public void readRange(byte[] arr, int startOffset, int endOffset) throws IOException
    {
        int length = endOffset - startOffset; 
        //System.out.println(startOffset + " - " + endOffset);
        reader.seek(startOffset);
        reader.read(arr, 0, arr.length);
    }
    
    public byte[] nextBlock() throws IOException
    {
        byte[] block; 
        if (read == -1)
        {
            return null; 
        }
        else if (offset + 8192 > reader.length())
        {
            long size = reader.length() - offset; 
            block = new byte[Integer.parseInt(Long.toString(size))]; 
            read = reader.read(block); 
            offset += read; 
        }
        else
        {
            block = new byte[8192]; 
            read = reader.read(block); 
            offset += read; 
        }
        return block; 
    }
    
    public boolean hasNextBlock() throws IOException
    {
        if (read == -1)
        {
            return false; 
        }
        return offset != reader.length(); 
    }
    
    public void close() throws IOException
    {
        reader.close();
    }

}

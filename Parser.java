import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Parser {
    private RandomAccessFile reader; 
    private long offset; 
    private int read; 
    
    public Parser(File file) throws FileNotFoundException
    {
        reader = new RandomAccessFile(file, "r"); 
        offset = 0; 
    }
    
    public byte[] readRange(int startOffset, int endOffset) throws IOException
    {
        int length = endOffset - startOffset; 
        byte[] arr = new byte[length];
        reader.readFully(arr, startOffset, length);
        return arr;
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

}

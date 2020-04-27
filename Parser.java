import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
    
    public static void dumpFile(String source, String outputFile) throws IOException
    {
        FileWriter writer = new FileWriter(outputFile); 
        Parser parser = new Parser(new File(source)); 
        byte[] block; 
        while (parser.hasNextBlock())
        {
            block = parser.nextBlock(); 
            for (int i = 0; i < block.length; i += 16)
            {
                Record rec = new Record(Arrays.copyOfRange(block, i, i + 16)); 
                writer.write(rec.getKey() + "\n"); 
            }
        }
        parser.close();
        writer.close();
    }
    
    public static void printFirstRecordOfEachBlock(String fileName) throws IOException
    {
        File file = new File(fileName); 
        Parser parser = new Parser(file);
        byte[] block;
        int count = 0; 
        while (parser.hasNextBlock())
        {
            block = parser.nextBlock();
            Record rec = new Record(Arrays.copyOfRange(block, 0, 16));
            System.out.print(rec.getID() + " " + rec.getKey());
            count++; 
            if (!parser.hasNextBlock())
            {
                return;
            }
            else if (count % 5 == 0)
            {
                System.out.println(); 
            }
            else
            {
                System.out.print(" ");
            }
            
        }
        parser.close();
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

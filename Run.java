import java.io.IOException;

public class Run {
    private int startOffset; 
    private int endOffset;
    private int currOffset;
    private Parser parser; 
    
    public Run()
    {
        startOffset = -1; 
        endOffset = -1;
        currOffset = startOffset;
    }
    
    public Run(int startOffset, int endOffset)
    {
        this.startOffset = startOffset; 
        this.endOffset = endOffset;
        currOffset = startOffset;
    }
    
    public void setParser(Parser parser)
    {
        this.parser = parser; 
    }
    
    public void setStartOffset(int offset)
    {
        startOffset = offset;
    }
    
    public void setEndOffset(int offset)
    {
        endOffset = offset; 
    }
    
    public int getStartOffset()
    {
        return startOffset;
    }
    
    public int getEndOffset()
    {
        return endOffset;
    }
    
    public Parser getParser()
    {
        return parser;
    }
    
    public byte[] getNextBlockOrRemaining() throws IOException
    {
        byte[] arr;
        if (currOffset == endOffset)
        {
            return null;
        }
        else if (currOffset + 8192 > endOffset)
        { 
            arr = new byte[endOffset - currOffset];
            parser.readRange(arr, currOffset, endOffset);
            currOffset = endOffset; 
        }
        else
        {
            arr = new byte[8192];
            parser.readRange(arr, currOffset, currOffset + 8192);
            currOffset += 8192;
        }
        return arr; 
    }
    
    public void printRunInfo()
    {
        System.out.println("Current offset: " + currOffset); 
    }

}

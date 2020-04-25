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
    
    public byte[] getNextBlockOrRemaining() throws IOException
    {
        if (currOffset == endOffset)
        {
            return null;
        }
        else if (currOffset + 8192 > endOffset)
        { 
            return parser.readRange(currOffset, endOffset); 
        }
        else
        {
            return parser.readRange(startOffset, startOffset + 8192);
        }
    }

}


public class Run {
    private int startOffset; 
    private int endOffset;
    private int currOffset;
    
    public Run()
    {
        startOffset = -1; 
        endOffset = -1;
        currOffset = 0; 
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
    
    public byte[] getNextBlock()
    {
        if (currOffset == endOffset)
        {
            return null; 
        }
        if (startOffset == -1 || endOffset == -1)
        {
            return null; 
        }
        return new byte[1];
    }

}

import java.io.IOException;

/**
 * Run class
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 */
public class Run {
    private int startOffset; 
    private int endOffset;
    private int currOffset;
    private Parser parser; 
    
    /**
     * Run default constructor
     */
    public Run()
    {
        startOffset = -1; 
        endOffset = -1;
        currOffset = startOffset;
    }
    
    /**
     * Run two argument constructor
     * @param startOffset start offset
     * @param endOffset end offset
     */
    public Run(int startOffset, int endOffset)
    {
        this.startOffset = startOffset; 
        this.endOffset = endOffset;
        currOffset = startOffset;
    }
    
    /**
     * Sets parser of run
     * @param parser Parser object
     */
    public void setParser(Parser parser)
    {
        this.parser = parser; 
    }
    
    /**
     * Sets start offset of run
     * @param offset offset
     */
    public void setStartOffset(int offset)
    {
        startOffset = offset;
    }
    
    /**
     * Sets end offset of run
     * @param offset offset
     */
    public void setEndOffset(int offset)
    {
        endOffset = offset; 
    }
    
    /**
     * Getter for start offset
     * @return start offset
     */
    public int getStartOffset()
    {
        return startOffset;
    }
    
    /**
     * Getter for end offset
     * @return end offset
     */
    public int getEndOffset()
    {
        return endOffset;
    }
    
    /**
     * Getter for parser
     * @return Parser object
     */
    public Parser getParser()
    {
        return parser;
    }
    
    /**
     * Gets next block or remaining records as
     * byte array
     * @return byte array of records
     * @throws IOException
     */
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
    
    /**
     * Prints run current offset
     */
    public void printRunInfo()
    {
        System.out.println("Current offset: " + currOffset); 
    }

}

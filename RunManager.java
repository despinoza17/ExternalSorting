import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * RunManager class tracks runs
 * in file
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 */
public class RunManager {
    private ArrayList<Run> runs;
    private String fileName; 
    
    /**
     * RunManager default constructor
     */
    public RunManager()
    {
        runs = new ArrayList<>(); 
    }
    
    /**
     * Gets RunManager object with runs
     * @param source run file
     * @return RunManager object
     * @throws IOException
     */
    public static RunManager getRunManager(String source)
            throws IOException
    {
        RunManager runManager = new RunManager();
        runManager.fileName = source;
        Parser parser = new Parser(new File(source));
        byte[] block; 
        Record prev = null;
        int startOffset = 0; 
        int endOffset = 0;
        while (parser.hasNextBlock())
        {
            block = parser.nextBlock();
            for (int i = 0; i < block.length; i += 16)
            {
                Record curr = new Record(
                        Arrays.copyOfRange(block, i, i + 16));
                if (prev != null && prev.compareTo(curr) == 1) 
                {
                    runManager.addRun(new Run(startOffset, endOffset));
                    startOffset = endOffset;
                }
                endOffset += 16;
                prev = curr; 
            }
        }
        runManager.addRun(new Run(startOffset, endOffset));
        startOffset = endOffset;
        parser.close();
        return runManager;
    }
    
    /**
     * Sets parser for runs
     * @param parser Parser object
     */
    public void setParserForRuns(Parser parser)
    {
        for (int i = 0; i < runs.size(); i++)
        {
            runs.get(i).setParser(parser); 
        }
    }
    
    /**
     * Gets run file name
     * @return file name
     */
    public String getFileName()
    {
        return fileName; 
    }
    
    /**
     * Getter for number of runs
     * @return number of runs
     */
    public int getNumRuns()
    {
        return runs.size(); 
    }
    
    /**
     * Adds run to RunManager
     * @param run run to be added
     */
    public void addRun(Run run)
    {
        runs.add(run); 
    }
    
    /**
     * Gets run based on run index
     * @param indx run index
     * @return run at run index
     */
    public Run getRun(int indx)
    {
        return runs.get(indx); 
    }
    
    /**
     * Closes run file
     * @throws IOException
     */
    public void close() throws IOException
    {
        for (int i = 0; i < runs.size(); i++)
        {
            runs.get(i).getParser().close();
        }
    }
    
    /**
     * Prints runs info
     */
    public void printRunInfo()
    {
        System.out.println("Runs Info:");
        for (int i = 0; i < runs.size(); i++)
        {
            System.out.println("Run " + (i + 1) + ": " + 
                    runs.get(i).getStartOffset() + " - " + 
                    runs.get(i).getEndOffset());
        }
    }
    
}

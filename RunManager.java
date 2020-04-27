import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RunManager {
    private ArrayList<Run> runs;
    private String fileName; 
    
    public RunManager()
    {
        runs = new ArrayList<>(); 
    }
    
    public static RunManager getRunManager(String source) throws IOException
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
                Record curr = new Record(Arrays.copyOfRange(block, i, i + 16));
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
    
    public void setParserForRuns(Parser parser)
    {
        for (int i = 0; i < runs.size(); i++)
        {
            runs.get(i).setParser(parser); 
        }
    }
    
    public String getFileName()
    {
        return fileName; 
    }
    
    public int getNumRuns()
    {
        return runs.size(); 
    }
    
    public void addRun(Run run)
    {
        runs.add(run); 
    }
    
    public Run getRun(int indx)
    {
        return runs.get(indx); 
    }
    
    public void close() throws IOException
    {
        for (int i = 0; i < runs.size(); i++)
        {
            runs.get(i).getParser().close();
        }
    }
    
    public void printRunInfo()
    {
        System.out.println("Runs Info:");
        for (int i = 0; i < runs.size(); i++)
        {
            System.out.println("Run " + (i + 1) + ": " + 
                    runs.get(i).getStartOffset() + " - " + runs.get(i).getEndOffset());
        }
    }
    
}

import java.util.ArrayList;

public class RunManager {
    private ArrayList<Run> runs;
    
    public RunManager()
    {
        runs = new ArrayList<>(); 
    }
    
    public void setParserForRuns(Parser parser)
    {
        for (int i = 0; i < runs.size(); i++)
        {
            runs.get(i).setParser(parser); 
        }
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
    
    public void printRunInfo()
    {
        for (int i = 0; i < runs.size(); i++)
        {
            System.out.println("Run " + (i + 1) + ": " + 
                    runs.get(i).getStartOffset() + " - " + runs.get(i).getEndOffset());
        }
    }
    
}

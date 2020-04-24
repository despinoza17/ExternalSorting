import java.util.ArrayList;

public class RunManager {
    private ArrayList<Run> runs;
    
    public RunManager()
    {
        runs = new ArrayList<>(); 
    }
    
    public void addRun(Run run)
    {
        runs.add(run); 
    }
    
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class MergeSort {
    private RunManager runManager; 
    
    public MergeSort(RunManager runManager)
    {
        this.runManager = runManager; 
    }
    
    public void merge(String runFile) throws IOException
    {
        int currRun = 0; 
        File origFile = new File(runFile);
        Parser parser; 
        File outputFile = new File("outputFile.bin"); 
        RandomAccessFile outFile;
        //runManager.printRunInfo();
        
        while (runManager.getNumRuns() > 1)
        {
            outFile = new RandomAccessFile(outputFile, "rw");
            parser = new Parser(origFile); 
            runManager.setParserForRuns(parser);
            MinHeap<Record> heap = new MinHeap<>(new Record[4096], 0, 4096);
            int[] recCount = new int[runManager.getNumRuns()]; 
            for (int i = 0; i < recCount.length; i++)
            {
                recCount[i] = -1; 
            }
            while (currRun < runManager.getNumRuns())
            {
                // Load first block of the 8 runs
                while (currRun < runManager.getNumRuns() && (currRun % 8 != 0 || currRun == 0))
                {       
                    //System.out.println("Current run: " + currRun); 
                    //runManager.getRun(currRun).printRunInfo();
                    byte[] block = runManager.getRun(currRun).getNextBlockOrRemaining();
                    //Parser.printBlock(block);
                    int numRecords = block.length / 16; 
                    for (int i = 0; i < block.length; i += 16)
                    {
                        Record rec = new Record(Arrays.copyOfRange(block, i, i + 16), currRun);
                        heap.insert(rec);
                    }
                    recCount[currRun] = numRecords;
                    //System.out.println("_______");
                    currRun++;
                }
                
                Record prev = null; 
                int count = 0; 
                // Sort
                while (heap.heapSize() > 0)
                {
                    Record rec = heap.removeMin();
                    //if (prev != null && prev.compareTo(rec) == 1)
                    //{
                    //    System.out.println(prev + " : " + rec);
                    //}
                    prev = rec;
                    count++;
                    //System.out.println(rec);
                    outFile.write(rec.getRawRecord());
                    recCount[rec.getRunNumber()]--; 
                    int exhaustedRun = checkExhaustedRuns(recCount);
                    if (exhaustedRun != -1)
                    {
                        //System.out.println("Run " + exhaustedRun);
                        //runManager.getRun(exhaustedRun).printRunInfo();
                        byte[] block = runManager.getRun(exhaustedRun).getNextBlockOrRemaining();
                        if (block != null)
                        {
                            //Parser.printBlock(block);
                            //System.out.println("_______");
                            int numRecords = block.length / 16; 
                            //System.out.println(new Record(Arrays.copyOfRange(block, 0, 16))); 
                            for (int i = 0; i < block.length; i += 16)
                            {
                                Record r = new Record(Arrays.copyOfRange(block, i, i + 16), exhaustedRun);
                                heap.insert(r);
                            }
                            recCount[exhaustedRun] = numRecords;
                        }
                        else
                        {
                            recCount[exhaustedRun] = -1; 
                        }
                    }
                }
                //System.out.println(count);
                        
            }
            outFile.close();
            parser.close();
            
            runManager = RunManager.getRunManager("outputFile.bin");
            runManager.printRunInfo();
            //File temp = new File("outputFile.bin"); 
            //File file = new File(runFile); 
            //Files.move(outputFile.toPath(), outputFile.toPath().resolveSibling(runFile), StandardCopyOption.REPLACE_EXISTING);
            //ReplacementSelection.dumpFile("runFile.bin", "rft.txt");
            //Files.deleteIfExists(outputFile.toPath());
            currRun = 0;
            //break; 
        }
    }
    
    private int checkExhaustedRuns(int[] recCount)
    {
        for (int i = 0; i < recCount.length; i++)
        {
            if (recCount[i] == 0)
            {
                return i; 
            }
        }
        return -1; 
    }

}

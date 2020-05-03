import java.io.File;  
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/**
 * MergeSort performs multiway merge
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 *
 */
public class MergeSort {
    private RunManager runManager; 
    
    /**
     * MergeSort one argument constructor
     * @param runManager RunManager tracks offsets
     */
    public MergeSort(RunManager runManager)
    {
        this.runManager = runManager; 
    }
    
    /**
     * Performs multiway merge sort
     * @throws IOException
     */
    public void merge() throws IOException
    {
        String fileName = runManager.getFileName();
        int currRun = 0; 
        File origFile = new File(fileName);
        Parser parser; 
        File outputFile = new File("outputFile.bin"); 
        RandomAccessFile outFile;
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
                int cap = currRun + 8; 
                while (currRun < cap && currRun < runManager.getNumRuns())
                {       
                    byte[] block = runManager.getRun(currRun)
                            .getNextBlockOrRemaining();
                    int numRecords = block.length / 16; 
                    for (int i = 0; i < block.length; i += 16)
                    {
                        Record rec = new Record(Arrays.copyOfRange(
                                block, i, i + 16), currRun);
                        heap.insert(rec);
                    }
                    recCount[currRun] = numRecords;
                    currRun++;
                }
                
                // Sort
                while (heap.heapSize() > 0)
                {
                    Record rec = heap.removeMin();
                    outFile.write(rec.getRawRecord());
                    recCount[rec.getRunNumber()]--; 
                    int exhaustedRun = checkExhaustedRuns(recCount);
                    if (exhaustedRun != -1)
                    {
                        byte[] block = runManager.getRun(exhaustedRun)
                                .getNextBlockOrRemaining();
                        if (block != null)
                        {
                            int numRecords = block.length / 16; 
                            for (int i = 0; i < block.length; i += 16)
                            {
                                Record r = new Record(
                                        Arrays.copyOfRange(block, i, i + 16),
                                        exhaustedRun);
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
            }
            outFile.close();
            parser.close();
            
            Files.move(outputFile.toPath(), outputFile.toPath()
                    .resolveSibling(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            runManager = RunManager.getRunManager(fileName);
            currRun = 0;
        }
    }
    
    /**
     * Checks if any of the runs have been exhausted during
     * multiway merge
     * @param recCount array that keeps track of record count
     * @return run number if exhausted run exists, negative one 
     *          otherwise
     */
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

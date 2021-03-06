import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
 
/**
 * ReplacementSelection performs 
 * replacement selection sort
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 *
 */
public class ReplacementSelection {
    private MinHeap<Record> heap; 
    private InputBuffer inputBuffer; 
    private OutputBuffer outputBuffer; 
    private File recordFile; 
    private Parser parser;
    private Record[] arr; 
    
    /**
     * ReplacementSelection sort
     * @param recordFile Record file
     * @throws FileNotFoundException
     */
    public ReplacementSelection(File recordFile)
            throws FileNotFoundException
    {
        this.recordFile = recordFile;
        arr = new Record[4096]; 
        heap = new MinHeap<Record>(arr, 0, 4096);
        inputBuffer = new InputBuffer(new byte[8192]); 
        outputBuffer = new OutputBuffer(); 
        parser = new Parser(recordFile); 
    }
    
    /**
     * Performs replacement selection sort
     * @throws IOException
     */
    public void replacementSort() throws IOException
    {
        int count = 0;
        // SET UP -- 
        // Fill heap with 8 blocks
        byte[] block; 
        for (int i = 0; i < 8; i++)
        {
            if (parser.hasNextBlock())
            {
                block = parser.nextBlock();
                for (int j = 0; j < block.length; j += 16)
                {
                    Record rec = new Record(
                            Arrays.copyOfRange(block, j, j + 16)); 
                    heap.insert(rec);
                    count++;
                }
            }
        }
        
        if (heap.heapSize() < 4096)
        {
            arr = Arrays.copyOfRange(arr, 0, count);
            heap = new MinHeap<Record>(arr, count, count);
        }
        
        while (parser.hasNextBlock())
        {
            inputBuffer.addNextBlock(parser.nextBlock());
            while (heap.heapSize() != 0 || arr.length != 0)
            {
                if (outputBuffer.isFilled())
                {
                    outputBuffer.unloadRun();
                }
                if (heap.filledWithHiddenValues())
                {
                    outputBuffer.unloadRun();
                    arr = Arrays.copyOfRange(
                            arr, 0, arr.length);
                    heap = new MinHeap<Record>(
                            arr, arr.length, arr.length);
                }
                

                Record min = heap.getMin();
                outputBuffer.insertRecordArr(min.getRawRecord());
                
                if (inputBuffer.isEmpty())
                {
                    if (parser.hasNextBlock())
                    {
                        inputBuffer.addNextBlock(parser.nextBlock());
                        Record currRecord = new Record(
                                inputBuffer.getNextRecordArr());
                        heap.modify(0, currRecord);
                        if (currRecord.compareTo(min) == -1)
                        {
                            heap.hideMin(); 
                             
                        }
                    }
                    else
                    {
                        heap.hideMin();
                        arr = combineArr(
                                Arrays.copyOfRange(arr, 0,
                                        heap.heapSize()),
                                Arrays.copyOfRange(arr,
                                        heap.heapSize() + 1, arr.length));
                        heap = new MinHeap<Record>(
                                arr, arr.length, arr.length); 
                    }
                    
                }
                else
                {
                    Record currRecord = new Record(
                            inputBuffer.getNextRecordArr());
                    heap.modify(0, currRecord);
                    if (currRecord.compareTo(min) == -1)
                    {
                        heap.hideMin(); 
                    }
                    
                }
            }           
        }  
        inputBuffer.clearBuffer();
        while (heap.heapSize() != 0 || arr.length != 0)
        {
            if (outputBuffer.isFilled())
            {
                outputBuffer.unloadRun();
            }
            if (heap.filledWithHiddenValues())
            {
                outputBuffer.unloadRun();
                arr = Arrays.copyOfRange(arr, 0, arr.length);
                heap = new MinHeap<Record>(arr, arr.length, arr.length);
            }
            

            Record min = heap.getMin();
            outputBuffer.insertRecordArr(min.getRawRecord());
            
            if (inputBuffer.isEmpty())
            {
                if (parser.hasNextBlock())
                {
                    inputBuffer.addNextBlock(parser.nextBlock());
                    Record currRecord = new Record(
                            inputBuffer.getNextRecordArr());
                    heap.modify(0, currRecord);
                    if (currRecord.compareTo(min) == -1)
                    {
                        heap.hideMin(); 
                         
                    }
                }
                else
                {
                    heap.hideMin();
                    arr = combineArr(
                            Arrays.copyOfRange(arr, 0,
                                    heap.heapSize()),
                            Arrays.copyOfRange(arr,
                                    heap.heapSize() + 1, arr.length));
                    heap = new MinHeap<Record>(arr, arr.length, arr.length); 
                }
                
            }
            else
            {
                Record currRecord = new Record(inputBuffer.getNextRecordArr());
                heap.modify(0, currRecord);
                if (currRecord.compareTo(min) == -1)
                {
                    heap.hideMin(); 
                }
            }
        }
        outputBuffer.unloadRun();
        outputBuffer.closeRunFile();
        parser.close();
        File runfile = new File("runFile.bin");
        Files.move(runfile.toPath(), runfile.toPath().
                resolveSibling(recordFile.getName()),
                StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Combines two arrays
     * @param arr1 first array to combine
     * @param arr2 second array to combine
     * @return combined array
     */
    private Record[] combineArr(Record[] arr1, Record[] arr2)
    {
        int length = arr1.length + arr2.length;
        Record[] result = new Record[length];
        for (int i = 0; i < arr1.length; i++)
        {
            result[i] = arr1[i]; 
        }
        for (int j = arr1.length; j < length; j++)
        {
            result[j] = arr2[j - arr1.length];
        }
        return result;
    }
}

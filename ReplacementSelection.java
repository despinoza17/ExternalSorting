import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ReplacementSelection {
    private MinHeap<Record> heap; 
    private InputBuffer inputBuffer; 
    private OutputBuffer outputBuffer; 
    private File recordFile; 
    private Parser parser;
    private Record[] arr; 
    
    public ReplacementSelection(File recordFile) throws FileNotFoundException
    {
        this.recordFile = recordFile; 
        arr = new Record[4096]; 
        heap = new MinHeap<Record>(arr, 0, 4096);
        inputBuffer = new InputBuffer(new byte[8192]); 
        outputBuffer = new OutputBuffer(); 
        parser = new Parser(recordFile); 
    }
    
    public void replacementSort() throws IOException
    {
        int hiddenElements = 0; 
        // SET UP -- 
        // Fill heap with 8 blocks
        byte[] block; 
        for (int i = 0; i < 8; i++)
        {
            block = parser.nextBlock(); 
            for (int j = 0; j < block.length; j += 16)
            {
                Record rec = new Record(Arrays.copyOfRange(block, j, j + 16)); 
                heap.insert(rec);
            }
        }
        
        int runs = 0;
        int recordOffset = 0; 
        int errorCount = 0;
        Record prev = null;
        while (parser.hasNextBlock())
        {
            inputBuffer.addNextBlock(parser.nextBlock());
            while (heap.heapSize() != 0)
            {
                if (outputBuffer.isFilled())
                {
                    outputBuffer.unloadRun();
                    recordOffset = 0;
                    runs++;
                }
                
                Record min = heap.getMin();
                outputBuffer.insertRecordArr(min.getRawRecord());
                recordOffset++; 
                
                if (inputBuffer.isEmpty())
                {
                    if (parser.hasNextBlock())
                    {
                        inputBuffer.addNextBlock(parser.nextBlock());
                        Record currRecord = new Record(inputBuffer.getNextRecordArr());
                        heap.modify(0, currRecord);
                        if (currRecord.compareTo(min) == -1)
                        {
                            heap.hideMin(); 
                            hiddenElements++; 
                        }
                    }
                    else
                    {
                        //System.out.println(min); 
                        heap.hideMin();
                        //System.out.println(heap.getElement(heap.heapSize()));
                        arr = combineArr(
                                Arrays.copyOfRange(arr, 0,
                                        heap.heapSize()),
                                Arrays.copyOfRange(arr,
                                        heap.heapSize() + 1, arr.length));
                        heap = new MinHeap<Record>(arr, arr.length, arr.length); 
                        hiddenElements = 0;
                    }
                    
                }
                else
                {
                    Record currRecord = new Record(inputBuffer.getNextRecordArr());
                    heap.modify(0, currRecord);
                    if (currRecord.compareTo(min) == -1)
                    {
                        heap.hideMin(); 
                        hiddenElements++; 
                    }
                }
            }           
        }  
        outputBuffer.unloadRun();
        runs++;
        outputBuffer.closeRunFile();   
    }
    
    public void dumpFile(String source, String outputFile) throws IOException
    {
        FileWriter writer = new FileWriter(outputFile); 
        Parser parser = new Parser(new File(source)); 
        byte[] block; 
        while (parser.hasNextBlock())
        {
            block = parser.nextBlock(); 
            for (int i = 0; i < block.length; i += 16)
            {
                Record rec = new Record(Arrays.copyOfRange(block, i, i + 16)); 
                writer.write(rec.getKey() + "\n");
                //System.out.println("Key: " + rec.getKey() + "; Value: " + rec.getID()); 
            }
        }
    }
    
    private Record[] combineArr(Record[] arr1, Record[] arr2)
    {
        //System.out.println(Arrays.toString(arr1)); 
        //System.out.println(Arrays.toString(arr2));
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
        //System.arraycopy(arr1, 0, result, 0, arr1.length);
        //System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}

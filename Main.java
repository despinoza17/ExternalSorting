import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException
    {
        ReplacementSelection ext = new ReplacementSelection(new File("8NBlocks.bin")); 
        ext.dumpFile("8NBlocks.bin", "sampleFile.txt");
        ext.replacementSort();
        ext.dumpFile("runFile.bin", "rf.txt");
        MergeSort merge = new MergeSort(RunManager.getRunManager("runFile.bin")); 
        merge.merge("runFile.bin");
        ext.dumpFile("outputFile.bin", "outputFileTr.txt");
        //ext.dumpFile("32NBlocks.bin", "sampleFile.txt");
        
    }

}

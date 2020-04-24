import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException
    {
        ReplacementSelection ext = new ReplacementSelection(new File("sampleInput16.bin")); 
        ext.replacementSort();
        ext.dumpFile("runFile.bin", "runFileTranslated.txt");
        //ext.dumpFile("sampleInput16Sorted.bin", "sampleFile.txt");
        
    }

}

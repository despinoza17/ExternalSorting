import java.io.File; 
import java.io.IOException;
/**
 * Externalsort class executes the program
 * 
 * @author despi17
 * @author oli1230
 *
 */
public class Externalsort {
    
    public static void main(String[] args) throws IOException
    {
        ReplacementSelection ext = new ReplacementSelection(new File(args[0])); 
        ext.replacementSort();
        MergeSort merge = new MergeSort(RunManager.getRunManager(args[0])); 
        merge.merge();
        Parser.printFirstRecordOfEachBlock(args[0]);
    }

}

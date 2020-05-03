import java.nio.ByteBuffer;
import java.util.Arrays;
import student.TestCase; 

/**
 * Record test class
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 */
public class RecordTest extends TestCase {
    private Record rec;
    private byte[] rawRecord;
    
    /**
     * Sets up test cases
     */
    public void setUp()
    {
        long id = 553; 
        double key = 5.09;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] arr = buffer.putLong(id).array();
        buffer = ByteBuffer.allocate(8);
        byte[] arr2 = buffer.putDouble(key).array();
        byte[] recordArr = new byte[16];
        for (int i = 0; i < 8; i++)
        {
            recordArr[i] = arr[i];
        }
        for (int i = 8; i < 16; i++)
        {
            recordArr[i] = arr2[i - 8];
        }
        this.rawRecord = recordArr;
        rec = new Record(recordArr);
    }
    
    /**
     * Tests multiple Record methods
     */
    public void testComplex()
    {
        assertTrue(Arrays.equals(rawRecord, rec.getRawRecord()));
        
        assertEquals(553, rec.getID()); 
        
        assert (5.09 == rec.getKey());
        
        assertEquals("5.09", rec.toString());
        
        assertEquals(0, rec.compareTo(rec));
    }

}

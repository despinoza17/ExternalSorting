import student.TestCase;

public class MinHeapTest extends TestCase {
    private MinHeap<Integer> heap; 
    
    public void setUp()
    {
        heap = new MinHeap<Integer>(new Integer[20], 0, 20); 
    }
    
    public void testComplex()
    {
        heap.insert(2);
        heap.insert(4);
        heap.insert(9);
        heap.insert(6);
        heap.insert(7); 
        heap.insert(1);
        heap.insert(12);
        heap.insert(11);
        heap.insert(20);
        heap.insert(15);
        heap.insert(25);
        heap.insert(30);
        
        //heap.printArray();
        //heap.removeMin();
        //heap.printArray();
        heap.hideMin();
        //heap.printArray();
        heap.hideMin(); 
        //heap.printArray();
        heap.hideMin(); 
        //heap.printArray();
        //heap.unhideElements();
        heap.buildHeap();
        heap.insert(4); 
        //heap.removeMin(); 
        heap.hideMin();
        //heap.unhideElements();
        heap.buildHeap();
        heap.insert(89);
        assertEquals(10, heap.heapSize());

        

        //heap.printArray();
    }
    
    public void testComplexTwo()
    {
        Integer result = heap.remove(0); 
        assertNull(result);
        assertEquals(0, heap.heapSize());
        
        result = heap.removeMin();
        assertNull(result); 
        assertEquals(0, heap.heapSize());
        
        heap.insert(2);
        result = heap.removeMin();
        assert(result == 2);
        assertEquals(0, heap.heapSize());
        
        heap.insert(2);
        result = heap.remove(0); 
        assert(2 == result);
        assertEquals(0, heap.heapSize());
        
        result = heap.remove(2);
        assertNull(result);
        assertEquals(0, heap.heapSize());
        
        heap.insert(3);
        result = heap.remove(0);
        assert(3 == result);
        assertEquals(0, heap.heapSize());
        
        heap.insert(3);
        heap.modify(0, 2);
        result = heap.getMin();
        assert(2 == result);
        assertEquals(1, heap.heapSize());
        
        heap.modify(2, 4);
        heap.insert(4);
        heap.insert(5);
        assertEquals(1, heap.leftChild(0));
        assertEquals(2, heap.rightChild(0));
        assertEquals(3, heap.heapSize());
        
        assertFalse(heap.filledWithHiddenValues());
        
        for (int i = 0; i < 17; i++)
        {
            heap.insert(i + 6);
        }
        for (int i = 0; i < 20; i++)
        {
            heap.hideMin();
        }
        assertTrue(heap.filledWithHiddenValues());
        
        
                
        
    }
    
    

}

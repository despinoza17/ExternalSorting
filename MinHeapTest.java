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
        
        heap.printArray();
        //heap.removeMin();
        heap.printArray();
        heap.hideMin();
        heap.printArray();
        heap.hideMin(); 
        heap.printArray();
        heap.hideMin(); 
        heap.printArray();
        //heap.unhideElements();
        heap.buildHeap();
        heap.insert(4); 
        //heap.removeMin(); 
        heap.hideMin();
        //heap.unhideElements();
        heap.buildHeap();
        heap.insert(89);

        

        heap.printArray();
    }

}

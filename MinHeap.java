import java.util.Arrays;

/**
 * MinHeap data structure 
 * 
 * @author despi17
 * @author oli1230
 * @version 4.27.2020
 * @param <T> Generic type
 */
public class MinHeap<T extends Comparable<? super T>> {
    private T[] heap; // Pointer to heap array
    private int size; // Max size of the heap
    private int n; // Number of things now in the heap
    private int hiddenValues; 
    
    /**
     * MinHeap three argument data structure
     * @param h heap array
     * @param num number of elements in heap
     * @param max max size of heap array
     */
    public MinHeap(T[] h, int num, int max)
    {
        hiddenValues = 0; 
        heap = h; 
        n = num;
        size = max; 
        buildHeap(); 
    }
    
    /**
     * Gets size of heap
     * @return heap size
     */
    public int heapSize()
    {
        return n; 
    }
    
    /**
     * Checks if element at position is a leaf
     * @param pos position of element
     * @return true if element at pos is a leaf, 
     *          false otherwise
     */
    public boolean isLeaf(int pos)
    {
        return (pos >= n / 2) && (pos < n); 
    }
    
    /**
     * Getter for min element
     * @return min element
     */
    public T getMin()
    {
        return heap[0]; 
    }
    
    /**
     * Getter for left child of element
     * @param pos position of element
     * @return position of left child
     *          of element
     */
    public int leftChild(int pos)
    {
        if (pos >= n / 2)
        {
            return -1; 
        }
        return 2 * pos + 1; 
    }
    
    /**
     * Getter for right child of element
     * @param pos position of element
     * @return position of right child 
     *          of element
     */
    public int rightChild(int pos)
    {
        if (pos >= (n - 1) / 2)
        {
            return -1; 
        }
        return 2 * pos + 2; 
    }
    
    /**
     * Getter for parent of element
     * @param pos position of element
     * @return position of parent of 
     *          element
     */
    public int parent(int pos)
    {
        if (pos <= 0)
        {
            return -1; 
        }
        return (pos - 1) / 2; 
    }
    
    /**
     * Inserts element in heap
     * @param key element to be added
     */
    public void insert(T key)
    {
        if (n >= size)
        {
            return;
        }
        int curr = n++; 
        heap[curr] = key; 
        while ((curr != 0) && 
                (heap[curr].compareTo(heap[parent(curr)]) < 0)) 
        {
            swap(heap, curr, parent(curr)); 
            curr = parent(curr); 
        } 
    }
    
    /**
     * Builds heap with array
     */
    public void buildHeap()
    {
        for (int i = n / 2 - 1; i >= 0; i--)
        {
            siftDown(i); 
        }
    }
    
    /**
     * Hides element in heap
     * @return hidden element
     */
    public T hideMin()
    {
        if (n == 0)
        {
            return null; 
        }
        swap(heap, 0, --n);
        siftDown(0); 
        hiddenValues++; 
        return heap[n]; 
    }
    /**
     * Checks if heap is filled with hidden 
     * values
     * @return true if filled with hidden 
     *      values, false otherwise
     */
    public boolean filledWithHiddenValues()
    {
        return hiddenValues == heap.length;
    }
    
    /**
     * Removes min element from heap
     * @return removed min element
     */
    public T removeMin()
    {
        if (n == 0)
        {
            return null;
        }
        swap(heap, 0, --n); 
        siftDown(0); 
        T element = heap[n];
        heap[n] = null;
        return element;
    }
    
    /**
     * Removes element at position
     * @param pos position of element to be removed
     * @return removed element
     */
    public T remove(int pos)
    {
        if ((pos < 0) || (pos >= n))
        {
            return null; 
        }
        if (pos == (n - 1))
        {
            n--;
        }
        else
        {
            swap(heap, pos, --n); 
            update(pos); 
        }
        return heap[n]; 
    }
    
    /**
     * Changes the value of element at position
     * @param pos position of element
     * @param newVal new element value
     */
    public void modify(int pos, T newVal)
    {
        if ((pos < 0) || (pos >= n))
        {
            return; 
        }
        heap[pos] = newVal; 
        update(pos); 
    }
    
    /**
     * Getter for element at position
     * @param pos position of element
     * @return element
     */
    public T getElement(int pos)
    {
        return heap[pos]; 
    }
    
    /**
     * Updates the heap upon insertion
     * @param pos position of element to 
     *      update
     */
    public void update(int pos)
    {
        while ((pos > 0) && 
                (heap[pos].compareTo(heap[parent(pos)]) < 0))
        {
            swap(heap, pos, parent(pos)); 
            pos = parent(pos);
        }
        siftDown(pos); 
    }
    
    /**
     * Prints heap array
     */
    public void printArray()
    {
        System.out.println(Arrays.toString(heap)); 
    }
    
    /**
     * Gets the last element in heap array
     * @return last element in heap array
     */
    public T getLast()
    {
        return heap[n - 1]; 
    }
    
    /**
     * Prints elements in heap
     */
    public void printList()
    {
        for (T t : heap)
        {
            System.out.println(t.toString()); 
        }
    }
    
    /**
     * Sifts down element at position
     * @param pos position of element to be sift down
     */
    private void siftDown(int pos)
    {
        if ((pos < 0) || (pos >= n))
        {
            return; 
        }
        while (!isLeaf(pos))
        {
            int j = leftChild(pos); 
            if ((j < (n - 1)) && 
                    (heap[j].compareTo(heap[j + 1]) >= 0))
            {
                j++; 
            }
            if (heap[pos].compareTo(heap[j]) < 0)
            {
                return;
            }
            swap(heap, pos, j); 
            pos = j; 
        }
    }
       
    /**
     * Swaps two elements in array
     * 
     * @param heapArr heap array
     * @param posOne position of first element
     * @param posTwo position of second element
     */
    private void swap(T[] heapArr, int posOne, int posTwo)
    {
        T temp = heapArr[posOne]; 
        heapArr[posOne] = heapArr[posTwo]; 
        heapArr[posTwo] = temp; 
    }
}

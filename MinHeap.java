import java.util.Arrays;

public class MinHeap<T extends Comparable<? super T>> {
    private T[] heap; // Pointer to heap array
    private int size; // Max size of the heap
    private int n; // Number of things now in the heap
    
    public MinHeap(T[] h, int num, int max)
    {
        heap = h; 
        n = num;
        size = max; 
        buildHeap(); 
    }
    
    public int heapSize()
    {
        return n; 
    }
    
    public boolean isLeaf(int pos)
    {
        return (pos >= n/2) && (pos < n); 
    }
    
    public T getMin()
    {
        return heap[0]; 
    }
    
    public int leftChild(int pos)
    {
        if (pos >= n/2)
        {
            return -1; 
        }
        return 2 * pos + 1; 
    }
    
    public int rightChild(int pos)
    {
        if (pos >= (n - 1) / 2)
        {
            return -1; 
        }
        return 2 * pos + 2; 
    }
    
    public int parent(int pos)
    {
        if (pos <= 0)
        {
            return -1; 
        }
        return (pos - 1) / 2; 
    }
    
    public void insert(T key)
    {
        if (n >= size)
        {
            System.out.println("Heap is full"); 
            return;
        }
        int curr = n++; 
        heap[curr] = key; 
        while((curr != 0) && (heap[curr].compareTo(heap[parent(curr)]) < 0)) 
        {
            swap(heap, curr, parent(curr)); 
            curr = parent(curr); 
        } 
    }
    
    public void buildHeap()
    {
        
        for (int i = n/2-1; i >= 0; i--)
        {
            siftDown(i); 
        }
    }
    public T hideMin()
    {
        if (n == 0)
        {
            return null; 
        }
        swap(heap, 0, --n);
        siftDown(0); 
        return heap[n]; 
    }
    
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
    
    public T remove(int pos)
    {
        if ((pos < 0) || (pos >= n))
        {
            return null; 
        }
        if (pos == (n-1))
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
    
    public void modify(int pos, T newVal)
    {
        if ((pos < 0) || (pos >= n))
        {
            return; 
        }
        heap[pos] = newVal; 
        update(pos); 
    }
    
    public T getElement(int pos)
    {
        return heap[pos]; 
    }
    
    public void update(int pos)
    {
        while ((pos > 0) && (heap[pos].compareTo(heap[parent(pos)]) < 0))
        {
            swap(heap, pos, parent(pos)); 
            pos = parent(pos);
        }
        siftDown(pos); 
    }
    
    public void printArray()
    {
        System.out.println(Arrays.toString(heap)); 
    }
    
    
    public T getLast()
    {
        return heap[n-1]; 
    }
    
    
    public void printList()
    {
        for (T t : heap)
            System.out.println(t.toString()); 
    }
    
    private void siftDown(int pos)
    {
        if ((pos < 0) || (pos >= n))
        {
            return; 
        }
        while (!isLeaf(pos))
        {
            int j = leftChild(pos); 
            if ((j < (n - 1)) && (heap[j].compareTo(heap[j+1]) >= 0))
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
       
    private void swap(T[] heap, int posOne, int posTwo)
    {
        T temp = heap[posOne]; 
        heap[posOne] = heap[posTwo]; 
        heap[posTwo] = temp; 
    }
}

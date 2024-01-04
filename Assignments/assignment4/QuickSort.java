public class QuickSort<T extends Comparable<? super T>> implements Sorter<T>
{   
    private Partitionable<T> partAlgo;
    private int MIN_SIZE; // min size to recurse, use InsertionSort
                          // for smaller sizes to complete sort
    public QuickSort(Partitionable<T> part)
    {
    partAlgo = part;
    MIN_SIZE = 5;
    }

    public void setMin(int minSize){
        MIN_SIZE = minSize;
    }   
    public void sort(T[] a, int size){
        sort(a, 0, size-1);
    } 

    public void sort(T[] a, int first, int last){
        if (last - first < MIN_SIZE - 1)
  	    {
  	        insertionSort(a, first, last);
  	    }else{
            // create the partition: Smaller | Pivot | Larger
	        int pivotIndex = partAlgo.partition(a, first, last);
	    
	        // sort subarrays Smaller and Larger
	        sort(a, first, pivotIndex - 1);
	        sort(a, pivotIndex + 1, last);
	  } // end if
        }


    public static <T extends Comparable<? super T>> 
	       void insertionSort(T[] a, int n)
	{
		insertionSort(a, 0, n - 1);
	} // end insertionSort

    public static <T extends Comparable<? super T>> void insertionSort(T[] a, int first, int last)
	{
		int unsorted, index;
		
		for (unsorted = first + 1; unsorted <= last; unsorted++)
		{   // Assertion: a[first] <= a[first + 1] <= ... <= a[unsorted - 1]
		
			T firstUnsorted = a[unsorted];
			
			insertInOrder(firstUnsorted, a, first, unsorted - 1);
		} // end for
	} // end insertionSort

    private static <T extends Comparable<? super T>> 
	        void insertInOrder(T element, T[] a, int begin, int end)
	{
		int index;
		
		for (index = end; (index >= begin) && (element.compareTo(a[index]) < 0); index--)
		{
			a[index + 1] = a[index]; // make room
		} // end for
		
		// Assertion: a[index + 1] is available
		a[index + 1] = element;  // insert
	} // end insertInOrder
}
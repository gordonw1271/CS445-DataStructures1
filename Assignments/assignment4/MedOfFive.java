public class MedOfFive<T extends Comparable<? super T>> implements Partitionable<T>
{
  public static final int MIN_SIZE = 5;

  public void quickSort(T[] array, int n)
	{
		quickSort(array, 0, n-1);
	} // end quickSort

	/** Sorts an array into ascending order. Uses quick sort with
	 *  median-of-three pivot selection for arrays of at least
	 *  MIN_SIZE elements, and uses insertion sort for other arrays. */
	public void quickSort(T[] a, int first, int last)
	{
	  if (last - first + 1 < MIN_SIZE)
	  {
	    insertionSort(a, first, last);
	  }
	  else
	  {
	    // create the partition: Smaller | Pivot | Larger
	    int pivotIndex = partition(a, first, last);

	    // sort subarrays Smaller and Larger
	    quickSort(a, first, pivotIndex - 1);
	    quickSort(a, pivotIndex + 1, last);
	  } // end if
	} // end quickSort

	// 12.17
	/** Task: Partitions an array as part of quick sort into two subarrays
	 *        called Smaller and Larger that are separated by a single
	 *        element called the pivot.
	 *        Elements in Smaller are <= pivot and appear before the
	 *        pivot in the array.
	 *        Elements in Larger are >= pivot and appear after the
	 *        pivot in the array.
	 *  @param a      an array of Comparable objects
	 *  @param first  the integer index of the first array element;
	 *                first >= 0 and < a.length
	 *  @param last   the integer index of the last array element;
	 *                last - first >= 3; last < a.length
	 *  @return the index of the pivot */
	public int partition(T[] a, int first, int last)
	{
		int mid = (first + last)/2;
    	int fmid = (first + mid)/2;
    	int lmid = (mid+last)/2;
    	sortFirstMiddleLast(a, first, fmid, mid);
    	sortFirstMiddleLast(a, mid, lmid, last);

    	// Assertion: The pivot is a[mid]; a[first] and a[fmid] <= pivot and 
    	// a[last] and a[lmid]>= pivot, so do not compare these two array elements
		// with pivot.
    
    	// move pivot to next-to-last position in array
	  swap(a, mid, last - 1);
	  int pivotIndex = last - 1;
	  T pivot = a[pivotIndex];
	  
	  // determine subarrays Smaller = a[first..endSmaller]
	  // and                 Larger  = a[endSmaller+1..last-1]
	  // such that elements in Smaller are <= pivot and 
	  // elements in Larger are >= pivot; initially, these subarrays are empty

	  int indexFromLeft = first + 1; 
	  int indexFromRight = last - 2; 
	  boolean done = false;
	  while (!done)
	  {
	    while (a[indexFromLeft].compareTo(pivot) < 0)
	      indexFromLeft++; 
	    while (a[indexFromRight].compareTo(pivot) > 0 & indexFromRight > 0)
	      indexFromRight--;
	      
	    assert a[indexFromLeft].compareTo(pivot) >= 0;
	           
	    if (indexFromLeft < indexFromRight)
	    {
	      swap(a, indexFromLeft, indexFromRight);
	      indexFromLeft++;
	      indexFromRight--;
	    }
	    else 
	      done = true;
	  } // end while
	  
	  // place pivot between Smaller and Larger subarrays
	  swap(a, pivotIndex, indexFromLeft);
	  pivotIndex = indexFromLeft;
	  
	  return pivotIndex; 
	} // end partition

	public void sortFirstMiddleLast(T[] a, int first, int mid, int last)
	{
	  order(a, first, mid); // make a[first] <= a[mid]
	  order(a, mid, last);  // make a[mid] <= a[last]
	  order(a, first, mid); // make a[first] <= a[mid]
	} // end sortFirstMiddleLast

	public void order(T[] a, int i, int j)
	{
	  if (a[i].compareTo(a[j]) > 0)
	    swap(a, i, j);
	} // end order

  public void swap(Object[] a, int i, int j)
  {
    Object temp = a[i];
    a[i] = a[j];
    a[j] = temp; 
  } // end swap

  public void insertionSort(T[] a, int n)
	{
		insertionSort(a, 0, n - 1);
	} // end insertionSort

  public void insertionSort(T[] a, int first, int last)
	{
		int unsorted, index;
		
		for (unsorted = first + 1; unsorted <= last; unsorted++)
		{   // Assertion: a[first] <= a[first + 1] <= ... <= a[unsorted - 1]
		
			T firstUnsorted = a[unsorted];
			
			insertInOrder(firstUnsorted, a, first, unsorted - 1);
		} // end for
	} // end insertionSort

  public void insertInOrder(T element, T[] a, int begin, int end)
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
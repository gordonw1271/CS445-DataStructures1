import java.util.*;
import java.util.Random;

public class RandomPivot<T extends Comparable<? super T>> implements Partitionable<T>
{
	public void quickSort(T[] array, int n)
	{
		quickSort(array, 0, n-1);
	} // end quickSort

	public void quickSort(T[] array, int first, int last)
	{
		if (first < last)
		{
			// create the partition: Smaller | Pivot | Larger
			int pivotIndex = partition(array, first, last);

			// sort subarrays Smaller and Larger
			quickSort(array, first, pivotIndex-1);
			quickSort(array, pivotIndex+1, last);
		} // end if
	}  // end quickSort

	public int partition(T[] a, int first, int last)
	{
    Random R = new Random();

    int pivotIndex = R.nextInt(last-first)+first;
		T pivot = a[pivotIndex];

    // determine subarrays Smaller = a[first..endSmaller]
		//                 and Larger  = a[endSmaller+1..last-1]
		// such that elements in Smaller are <= pivot and
		// elements in Larger are >= pivot; initially, these subarrays are empty

		int indexFromLeft = first;
		int indexFromRight = last - 1;

		boolean done = false;
		while (!done)
		{
			// starting at beginning of array, leave elements that are < pivot;
			// locate first element that is >= pivot
			while (a[indexFromLeft].compareTo(pivot) < 0)
				indexFromLeft++;

			// starting at end of array, leave elements that are > pivot;
			// locate first element that is <= pivot

			while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
				indexFromRight--;

			// Assertion: a[indexFromLeft] >= pivot and
			//            a[indexFromRight] <= pivot.

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

		// Assertion:
		// Smaller = a[first..pivotIndex-1]
		// Pivot = a[pivotIndex]
		// Larger  = a[pivotIndex + 1..last]

		return pivotIndex;
	}  // end partition

	public void swap(Object [] a, int i, int j)
	{
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	} // end swap
}

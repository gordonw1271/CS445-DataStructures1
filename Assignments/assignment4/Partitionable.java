// CS 0445 Spring 2023
// Partitionable interface.  Your QuickSort classes should have a
// Partitionable<T> object inside them from which the partition() 
// method will be called.

public interface Partitionable<T extends Comparable<? super T>> 
{
	public int partition(T[] a, int first, int last);
}
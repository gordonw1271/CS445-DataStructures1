// CS 0445 Spring 2023
// Sorter<T> interface.  All of your sorting classes must implement
// this interface.  To see how this interface is used, read over
// the SortTest.java program.

public interface Sorter<T extends Comparable<? super T>> 
{
	public void sort(T[] a, int size);
	public void setMin(int minSize);
}
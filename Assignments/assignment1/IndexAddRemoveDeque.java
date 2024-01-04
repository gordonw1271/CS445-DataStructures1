
// CS 0445 Spring 2023
// Gordon Wong
// IndexAddRemoveDeque is a subclass of IndexDeque and implements IndexableAddRemove
// Include methods such as addToFront(), addToBack(), removeFront(), removeBack()
// These methods allow the user to enter the position relative to the front or back
// where they want to add or remove an item

public class IndexAddRemoveDeque<T> extends IndexDeque<T> implements IndexableAddRemove<T> {
	
	public IndexAddRemoveDeque(int sz) {
		super(sz);
	}
	
	// in the normal case, removeFront i times and add those items to temp. Then addToFront(item).
	// iterate through temp and add all items back to data
	public void addToFront(int i, T item) {
		if(i == 0) {
			addToFront(item);
		}else if(i < 0 || N < i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i];
			for(int x = 0;x<i;x++) {
				temp[x] = removeFront();
			}
			addToFront(item);
			for(int x = i-1;x>=0;x--) {
				addToFront(temp[x]);
			}
		}

	}

	// same logic as addToFront, but reversed
	public void addToBack(int i, T item) {
		if(i == 0) {
			addToBack(item);
		}else if(i < 0 || N < i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i];
			for(int x = 0;x<i;x++) {
				temp[x] = removeBack();
			}
			addToBack(item);
			for(int x = i-1;x>=0;x--) {
				addToBack(temp[x]);
			}
		}
		
	}

	// in the normal case, removeFront i + 1 times and add those items to temp.
	// Iterate through temp and add all items, excluding last item in temp, back to data.
	public T removeFront(int i){
		if(i < 0 || N< i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else if(i == 0) {
			return removeFront();
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i+1];
			for(int x = 0;x<=i;x++) {
				temp[x] = removeFront();
			}
			for(int x = i-1;x>=0;x--) {
				addToFront(temp[x]);
			}
			return(temp[i]);
		}
		
	}

	// same logic as removeFron() but reversed for removeBack()
	public T removeBack(int i){
		if(i < 0 || N < i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else if(i == 0) {
			return removeBack();
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i+1];
			for(int x = 0;x<=i;x++) {
				temp[x] = removeBack();
			}
			for(int x = i-1;x>=0;x--) {
				addToBack(temp[x]);
			}
			return(temp[i]);
		}
	}

}

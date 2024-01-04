
// CS 0445 Spring 2023
// Gordon Wong
// IndexDeque is a subclass of MyDeque and  implements Indexable
// IndexDeque contains methods getFront(), getBack(), setFront(), setBack(),
// and size()

public class IndexDeque<T> extends MyDeque<T> implements Indexable<T> {
	
	public IndexDeque(int sz) {
		super(sz);
	}
	
	public T getFront(int i){
		if(i < 0 || N < i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else if(i == 0) {
			return(data[front]);
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i];
			T out;
			for(int x = 0;x<i;x++) {				//removeFront() till desired item in front of desired item
				temp[x] = removeFront();			// is removed. Add all items to temp[]
			}
			out = data[front];
			for(int x = i-1;x>=0;x--) {				// iterated from back to front of temp[] and addToFront
				addToFront(temp[x]);				// each item
			}
			return out;
		}
		
	}
	
	// same logic as getFront but reversed for the back
	public T getBack(int i) {
		if(i < 0 || N < i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else if(i == 0) {
			return data[back];
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i];
			T out;
			for(int x = 0;x<i;x++) {
				temp[x] = removeBack();
			}
			out = data[back];
			for(int x = i-1;x>=0;x--) {
				addToBack(temp[x]);
			}
			return out;
		}
	}
	
	public void setFront(int i, T item){
		if(i < 0 || N< i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else if(i == 0) {
			data[front] = item;
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i];
			for(int x = 0;x<i;x++) {
				temp[x] = removeFront();
			}
			data[front] = item;					// same logic as getFront but instead of setting out
			for(int x = i-1;x>=0;x--) {			//  to data[front]. data[front] is set to the item.
				addToFront(temp[x]);
			}
		}
    	
	}

	public void setBack(int i, T item){
		if(i < 0 || N < i+1) {
			throw new IndexOutOfBoundsException("Illegal Index " + i);
		}else if(i == 0) {
			data[back] = item;
		}else {
			@SuppressWarnings("unchecked")
	    	T[] temp = (T []) new Object[i];
			for(int x = 0;x<i;x++) {
				temp[x] = removeBack();
			}
			data[back] = item;					// same logic as getFront but instead of setting out
			for(int x = i-1;x>=0;x--) {			//  to data[front]. data[front] is set to the item.
				addToBack(temp[x]);
			}
		}
	}
	
	public int size(){
		return N;
	}

}

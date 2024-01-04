
// CS 0445 Spring 2023
// Gordon Wong
// MyDeque implements DequeInterface
// Includes two constructors. One constructor that initalizes 
// deque of size sz and one that copys another inputed Deque
// In addition to methods in DequeInterface, MyDeque also contains
// resize(), equals(), and toSting()

public class MyDeque<T> implements DequeInterface<T> {

    protected T[] data;
    protected int front;
    protected int back;
    protected int N;

    public MyDeque(int sz){
        data = (T []) new Object[sz];
        this.front = -1;
        this.back = -1;
        this.N = 0;
    }
    
    // initializes array with same length as the old array.
    // set all instance variables equal
    // iterates through the old array and sets each index of data to old.data
    public MyDeque(MyDeque<T> old){
    	data = (T []) new Object[old.data.length]; 
        this.front = old.front;
        this.back = old.back;
        this.N = old.N;
        for(int i = 0; i < old.data.length; i++){
            data[i] = old.data[i];
        }
    }

    // if front < back, data is just copied from data to temp.
    // We start at temp[(data.length/2) + 1] to ensure that 
    // there is at least data.length/2 indicies open on both sides.
    protected void resize(){
    	@SuppressWarnings("unchecked")
    	T[] temp = (T []) new Object[data.length * 2];
        if(front < back) {
        	for(int i = front;i<=back;i++) {
        		temp[(data.length/2)+i] = data[i];
        	}
        // if front>back, we know that front needs to wrap back to index 0.
        // first we copy data from front to the end of array and use ind to 
        // iterate throught the indicies of temp. We also use a data.lengt/2
        // buffer in the front to make sure there are open indices on both sides.
        // Then we iterate from 0 to back and add those to temp.
        }else {
        	int ind = 0;
        	int ind1 =0;
			for(int i = front;i<data.length;i++) {
				temp[(data.length/2)+ind] = data[i];
				ind++;
			}
			for(int i = 0;i<=back;i++) {
				temp[(data.length/2) + data.length-front + ind1] = data[i];
				ind1++;
			}
        }
        front = data.length/2;
        back = data.length/2 + N -1;
        data = temp;
    }
    
    // create two StringBuilders. Iterate through rhs.data and data 
    // and append each item to their respective StringBuilders in order. Then compare
    // the two using toString() and equals(). 
    public boolean equals(MyDeque<T> rhs) {
    	StringBuilder str1 = new StringBuilder();
    	StringBuilder str2 = new StringBuilder();
    	
    	if(!isEmpty()) {
    		if(front<back) {
    			for(int i = front; i <= back;i++) {	// iterated from front to back
    				str1.append(data[i]);
    			}
    		}else {
    			for(int i = front;i<data.length;i++) { // iterates from index front to data.length
    				str1.append(data[i]);
    			}
    			for(int i = 0;i<=back;i++) {		   // iterates from index 0 to back
    				str1.append(data[i]);
    			}
    		}
    	}else {
    		str1.append("empty");
    	}
    	
    	if(!rhs.isEmpty()) {
    		if(rhs.front<rhs.back) {
    			for(int i = rhs.front; i <= rhs.back;i++) {	// iterated from front to back
    				str2.append(rhs.data[i]);
    			}
    		}else {
    			for(int i = rhs.front;i<rhs.data.length;i++) { // iterates from index front to data.length
    				str2.append(rhs.data[i]);
    			}
    			for(int i = 0;i<=rhs.back;i++) {		   // iterates from index 0 to back
    				str2.append(rhs.data[i]);
    			}
    		}
    	}else {
    		str2.append("empty");
    	}
    	
    	if(str1.toString().equals(str2.toString())) {
    		return true;
    	}else {
    		return false;
    	}	
    }

    // Uses same logic as equals methods. 
    // appends data in order from front to back
    // to a StringBuilder. Returns a sting of the StringBuilder
    @Override
    public String toString(){
    	StringBuilder str = new StringBuilder("Contents: ");
    	if(!isEmpty()) {
    		if(front<back) {
    			for(int i = front; i <= back;i++) {
    				str.append(data[i] + " ");
    			}
    		}else {
    			for(int i = front;i<data.length;i++) {
    				str.append(data[i] + " ");
    			}
    			for(int i = 0;i<=back;i++) {
    				str.append(data[i] + " ");
    			}
    		}
    		return str.toString();
    	}else {
    		return str.toString();
    	}
    }
    
    
    // default front position is data.length - 1 and goes to the left
    public void addToFront(T newEntry){
        if(N == data.length){ //deque is full
            resize();
            front--;
            data[front] = newEntry;
            N++;
        }else if(isEmpty()){  // deque is empty
            front = data.length/2 - 1;
            back = front;
            data[front] = newEntry;
            N ++;
        }else if(front != 0 & front == data.length/2){ // case for when addToFront() has not been called yet
            front = data.length/2 -1;				   // in this case front is in the original "back" position
            data[front] = newEntry;					   // which is data.length/2. Front is set to data.length/2 - 1
            N ++;
        }else if(front != 0) {	// normal case whena adding to front
        	front--;
            data[front] = newEntry;
            N ++;
        }else if(front == 0){ //adding when front needs to wrap
            front = data.length -1;
            data[front] = newEntry;
            N ++;
        }
    }
    
 // default front position is data.length - 1 and goes to the right
    public void addToBack(T newEntry){
        if(N == data.length){ //deque is full
            resize();
            back++;
            data[back] = newEntry;
            N++;
        }else if(isEmpty()){ //deque is empty
            back = data.length/2;
            front = back;
            data[back] = newEntry;
            N ++;
        }else if(back != data.length -1 & back == data.length/2 -1){  //case where addToBack() has not been called
            back = data.length/2;									  // in this case, back is at data.length/2 -1
            N++;													  // which is the original front position
            data[back] = newEntry;	
        }else if(back != data.length -1){ // normal case when adding to back
        	back++;
            N++;
            data[back] = newEntry;
        }else if(back == data.length -1){ // adding when add to back needs to wrap
            back = 0;
            N++;
            data[back] = newEntry;
        }
    }

    public T removeFront(){
        if(N == 0){
            return null;
        }else if(N == 1){
        	T output = data[front];
        	N--;
        	front = -1;
        	back = -1;						// bc there is only one item, both front and back set 
        	return output;					// to -1
        }else {
        	T output = data[front];			// normal cases
            if(front == data.length - 1){	//front needs to wrap back to 0
                front = 0;
                N--;
            }else{							// front does not need to wrap
                front = front + 1;
                N--;
            }
            return output;
        }
    }
    
    public T removeBack(){
        if(N == 0){
            return null;
        }else if(N == 1){
            T output = data[back];
            N--;
            back = -1;						// bc there is only one item, both front and back set 
            front = -1;						// to -1
            return output;
        }else {
        	T output = data[back];			//normal cases
            if(back == 0 ){					// back needs to wrap back to data.length -1
                back = data.length - 1;
                N--;
            }else{							// front does not need to wrap
                back = back - 1;
                N--;
            }
            return output;
        }
    }

    public T getFront(){
        if(N == 0){
            return null;
        }else{
            return data[front];
        }
    }
    public T getBack(){
        if(N == 0){
            return null;
        }else{
            return data[back];
        }
    }

    public boolean isEmpty(){
        if(this.N == 0){
            return true;
        }else{
            return false;
        }
    }

    public void clear(){
        this.data = (T []) new Object[data.length];
        this.front = -1;
        this.front = -1;
        this.N = 0;
    }

    public int size(){
        return N;
    }

    public int capacity(){
        return data.length;
    }
}

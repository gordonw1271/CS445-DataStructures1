// CS 0445 Spring 2023
// Gordon Wong

// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a circular, doubly linked list of nodes.  See more comments below on the
// specific requirements for the class.

// You should use this class as the starting point for your implementation. 
// Note that all of the methods are listed -- you need to fill in the method
// bodies.  Note that you may want to add one or more private methods to help
// with your implementation -- that is fine.

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class. 

public class MyStringBuilder
{
	private CNode firstC;	
	private int length;

	// Create a new MyStringBuilder initialized with the chars in String s
	// Note: This method is implemented for you.  See code below.  Also read
	// the comments.  The code here may be helpful for some of your other
	// methods.
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0)  // special case for empty String
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s.charAt(0));  // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the String, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));  // create Node
				currNode.next = newNode;  	// link new node after current
				newNode.prev = currNode;	// line current before new node
				currNode = newNode;			// move down the list
				length++;
			}
			// After all nodes are created, connect end back to front to make
			// list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Return the entire contents of the current MyStringBuilder as a String
	// For this method you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	// Note: This method is implemented for you.  See code below.
	public String toString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC;
		
		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.next;
		}
		return new String(c);
	}

	// Create a new MyStringBuilder initialized with the chars in array s. 
	// You may NOT create a String from the parameter and call the first
	// constructor above.  Rather, you must build your MyStringBuilder by
	// accessing the characters in the char array directly.  However, you
	// can approach this in a way similar to the other constructor.
	public MyStringBuilder(char [] s)
	{
		if (s == null || s.length == 0)  // special case for empty array
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s[0]);  // create first node
			length = 1;
			CNode currNode = firstC;

			for (int i = 1; i < s.length; i++)
			{
				CNode newNode = new CNode(s[i]);  // create Node
				currNode.next = newNode;  	// link new node after current
				newNode.prev = currNode;	// line current before new node
				currNode = newNode;			// move down the list
				length++;
			}
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}
	
	// Copy constructor -- make a new MyStringBuilder from an old one.  Be sure
	// that you make new nodes for the copy (traversing the nodes in the original
	// MyStringBuilder as you do)
	public MyStringBuilder(MyStringBuilder old)
	{
		if(old.length == 0 || old == null){
			this.firstC = null;
			this.length = 0;
		}
		else
		{
			firstC = new CNode(old.firstC.data);
			CNode currNode = firstC;
			CNode oldCurrNode = old.firstC;

			for(int i = 1;i<old.length;i++){
				CNode newNode = new CNode(oldCurrNode.next.data);
				connect(currNode,newNode);
				currNode = newNode;
				oldCurrNode = oldCurrNode.next;
			}
			connect(currNode,firstC);
			length = old.length;
		}
	}
	
	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		this.firstC = null;
		this.length = 0;
	}
	
	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!  Note
	// that you cannot simply link the two MyStringBuilders together -- that is
	// very simple but it will intermingle the two objects, which you do not want.
	// Thus, you should copy the data in argument b to the end of the current
	// MyStringBuilder.
	public MyStringBuilder append(MyStringBuilder b)
	{
		if(b.length == 0 || b == null){			// invalid argument
			return this;
		}
		else if(length > 1)					// MyStringBuilder length > 1, normal case
		{
			CNode currNode = firstC.prev;
			CNode bCurrNode = b.firstC;

			for(int i = 0; i < b.length; i++){
				CNode newNode = new CNode(bCurrNode.data);
				connect(currNode,newNode);
				currNode = newNode;
				bCurrNode = bCurrNode.next;
			}
			connect(currNode,firstC);
			length += b.length;
			return this;
		}
		else if(length == 0)				// MyStringBuilder is empty or null 
		{
			CNode currNode = new CNode(b.firstC.data);
			firstC = currNode;
			CNode bCurrNode = b.firstC;

			for(int i = 1; i < b.length; i++){
				CNode newNode = new CNode(bCurrNode.data);
				connect(currNode,newNode);
				currNode = newNode;
				bCurrNode = bCurrNode.next;
			}
			connect(currNode,firstC);
			length += b.length;
			return this;
		}
		else					// MyStringBuilder has length 1
		{
			CNode currNode = firstC;
			CNode bCurrNode = b.firstC;

			for(int i = 0; i < b.length; i++){
				CNode newNode = new CNode(bCurrNode.data);
				connect(currNode,newNode);
				currNode = newNode;
				bCurrNode = bCurrNode.next;
			}
			connect(currNode,firstC);
			length += b.length;
			return this;
		}
	}

	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
		if(s.equals("") || s == null){		// s is an empty string
			return this;
		}else if(length > 1){							// base case
			CNode first = new CNode(s.charAt(0));
			CNode currNode = first;
			for(int i = 1;i < s.length();i++){
				CNode newCurrNode = new CNode(s.charAt(i));
				connect(currNode,newCurrNode);
				currNode = currNode.next;
			}
			connect(firstC.prev,first);
			connect(currNode,firstC);
		}
		else if(length == 0){							// MyStringBuilder length == 0
			firstC = new CNode(s.charAt(0));
			CNode currNode = firstC;
			for(int i = 1;i < s.length();i++){
				CNode newCurrNode = new CNode(s.charAt(i));
				connect(currNode,newCurrNode);
				currNode = currNode.next;
			}
			connect(currNode,firstC);
		}else{											// MyStringBuilder length == 1
			CNode first = new CNode(s.charAt(0));
			CNode currNode = first;
			for(int i = 1;i < s.length();i++){
				CNode newCurrNode = new CNode(s.charAt(i));
				connect(currNode,newCurrNode);
				currNode = currNode.next;
			}
			connect(firstC,first);
			connect(currNode,firstC);

		}
		length += s.length();
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c)
	{
		if(c == null){					// array is null
			return this;
		}
		else if(length > 1){							// base case, length > 1
			CNode currNode = firstC.prev;
			for(char x : c){
				CNode newCurrNode = new CNode(x);
				connect(currNode,newCurrNode);
				currNode = currNode.next;
			}
			connect(currNode,firstC);
		}
		else if(length == 0){
											// MyStringBuilder has length 0
			firstC = new CNode(c[0]);
			CNode currNode = firstC;
			for(int i = 1;i < c.length;i++){
				CNode newCurrNode = new CNode(c[i]);
				connect(currNode,newCurrNode);
				currNode = currNode.next;
			}
			connect(currNode,firstC);
		}
		else{								//length == 1
			CNode currNode = firstC;
			for(char x : c){
				CNode newCurrNode = new CNode(x);
				connect(currNode,newCurrNode);
				currNode = currNode.next;
			}
			connect(currNode,firstC);
		}
		length += c.length;
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		if(length > 1){
			CNode newNode = new CNode(c);
			CNode back = firstC.prev;
			connect(back,newNode);
			connect(newNode,firstC);
		}else if(length == 0){
			CNode newNode = new CNode(c);
			firstC = newNode;
		}else{
			CNode newNode = new CNode(c);
			connect(firstC,newNode);
			connect(newNode,firstC);
		}
		length ++;
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		char out;
		if(index < 0 || index > length - 1){
			throw new IndexOutOfBoundsException("Illegal Index " + index);
		}else{
			CNode currNode = firstC;
			for(int i = 0;i < index;i++ ){
				currNode = currNode.next;
			}
			out = currNode.data;
		}
		return out;
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
		if(start < 0 || start > length-1 || end <= start){		// special cases
			return this;
		}else if(start == 0){									// start at 0
			if(end > length - 1){							// delete whoe list
				firstC = null;
				length = 0;
			}else{											// end is not whole list
				CNode currNode = firstC;
				for(int i = 0;i < end;i++ ){
					currNode = currNode.next;
				}
				connect(firstC.prev,currNode);
				firstC = currNode;
				length -= end;
			}
		}else{													// normal case where start != 0
			// find first
			CNode currNode = firstC;
			for(int i = 0;i < start -1;i++ ){
			currNode = currNode.next;
		 	}
			if(end > length - 1){
				connect(currNode,firstC);
				length = start;
			}else{
				//find end
				CNode endCurrNode = currNode;
				for(int i = 0;i < end - start +1;i++ ){
					endCurrNode = endCurrNode.next;
				}
				connect(currNode, endCurrNode);
				length -= (end - start);
			}
		}
		return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).  If "index"
	// is the last character in the MyStringBuilder, go backward in the list in
	// order to make this operation faster (since the last character is simply
	// the previous of the first character)
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		if(index < 0 || index > length - 1){			// index is invalid
			return this;
		}else if(index == length - 1){				// index is last node 
			CNode currNode = firstC.prev.prev;
			connect(currNode,firstC);
		}else if(index == 0){
			CNode currNode = firstC.next;
			firstC = currNode;
		}else{
			CNode currNode = firstC;
			for(int i = 0;i < index -1;i++ ){
				currNode = currNode.next;
			}
			CNode nextNode = currNode.next.next;
			connect(currNode,nextNode);
		}
		length --;
		return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str){
		CNode currNode = firstC;
		for(int i = 0;i < length - str.length() +1;i++){
			if(currNode.data == str.charAt(0)){
				if(str.length() == 1){
					return i;
				}
				else{
					CNode temp = currNode;
					boolean found = false;
					for(int x = 1;x<str.length();x++){
						if(temp.next.data == str.charAt(x)){
							found = true;
							temp = temp.next;
						}else{
							found = false;
						}
					}
					if(found == true){
						return i;
					}else{
						currNode = currNode.next;
					}
				}
			}else{
				currNode = currNode.next;
			}
		}
		return -1;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
		if(offset < 0 || offset > length || str.equals("") || str == null){	// invalid cases
			return this;
		}else {
			// create linked list for string
			CNode first = new CNode(str.charAt(0));
			CNode currNode = first;
			for (int i = 1; i < str.length(); i++){
			CNode newNode = new CNode(str.charAt(i));
			connect(currNode,newNode);
			currNode = newNode;	
			}
			if(length > 1){								// length  > 1
				if(offset == 0){ 								// append to front base case
					connect(firstC.prev,first);
		 			connect(currNode,firstC);
					firstC = first;
				}else if(offset == length){						// append to back base case
					connect(firstC.prev,first);
					connect(currNode,firstC);
				}else{
					CNode beginNode = firstC;							// inserting in the middle
					for(int i = 0; i < offset - 1;i++){
					beginNode = beginNode.next;
					}
					//inserting new nodes into MyStringBuilder list
					connect(currNode,beginNode.next);
					connect(beginNode,first);
				}
			}else if(length == 1){						// length  == 1
				if(offset == 0){
					connect(currNode,firstC);
					connect(firstC,first);
					firstC = first;
				}else if(offset == length){
				connect(firstC,first);					
					connect(currNode, firstC);
				}
			}else{										// length == 0
				firstC = first;
				connect(firstC,currNode);
			}
		}
		length += str.length();
		return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		if(offset < 0 || offset > length){
			return this;
		}
		else if(length > 1)
		{
			CNode newNode = new CNode(c);
			if(offset < length && offset > 0){
				CNode beginNode = firstC;	
				for(int i = 0; i < offset - 1;i++){
					beginNode = beginNode.next;
				}
				// CNode end = beginNode.next;
				//inserting new nodes into MyStringBuilder list
				connect(newNode,beginNode.next);
				connect(beginNode,newNode);
				// connect(newNode,end);
			}else if(offset == 0){
				connect(firstC.prev,newNode);
				connect(newNode,firstC);
				firstC = newNode;
			}else{
				connect(firstC.prev,newNode);
				connect(newNode,firstC);
			}
			length += 1;
			return this;
		}
		else if(length == 1)
		{
			CNode newNode = new CNode(c);
			if(offset == 0){
				connect(newNode,firstC);
				connect(firstC,newNode);
			}else{
				connect(firstC,newNode);
				connect(newNode,firstC);
			}
			length += 1;
			return this;
		}
		else
		{
			CNode newNode = new CNode(c);
			firstC = newNode;
			length += 1;
			return this;
		}
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
		return this.length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String str)
	{
		if(start < 0 || start > length-1 || end <= start){		// special cases
			return this;
		}else if(end > length - 1){								// if start is valid and end is greater than length - 1
			if(start == 0){
				// create linked list for str
				firstC = new CNode(str.charAt(0));
				CNode strCurrNode = firstC;
				for(int i = 1;i<str.length();i++){
				strCurrNode.next = new CNode(str.charAt(i));
				strCurrNode = strCurrNode.next;
				}
				length = str.length();
			}else{
				CNode startCurrNode = firstC;
				for(int i = 0;i < start -1;i++ ){
					startCurrNode = startCurrNode.next;
				}
				// create linked list for str
				CNode first = new CNode(str.charAt(0));
				CNode strCurrNode = first;
				for(int i = 1;i<str.length();i++){
					strCurrNode.next = new CNode(str.charAt(i));
					strCurrNode = strCurrNode.next;
				}
				// connect str list to MyStringBuilder
				connect(startCurrNode,first);
				connect(strCurrNode,firstC);
				length = start + str.length();
			}
		}else{										// normal cases
			if(start == 0){
				//find end node starting from start node so we dont traverse again
				CNode endCurrNode = firstC;
				for(int i = 0;i < end;i++ ){
					endCurrNode = endCurrNode.next;
				}
				// create linked list for str
				firstC = new CNode(str.charAt(0));
				CNode strCurrNode = firstC;
				for(int i = 1;i<str.length();i++){
					strCurrNode.next = new CNode(str.charAt(i));
					strCurrNode = strCurrNode.next;
				}
				connect(strCurrNode,endCurrNode);
				length = length + str.length() - (end - start);
			}else{
				// find start node
				CNode currNode = firstC;
				for(int i = 0;i < start -1;i++ ){
					currNode = currNode.next;
				}
				//find end node starting from start node so we dont traverse again
				CNode endCurrNode = currNode;
				for(int i = 0;i < end - start +1;i++ ){
					endCurrNode = endCurrNode.next;
				}
				// create linked list for str
				CNode first = new CNode(str.charAt(0));
				CNode strCurrNode = first;
				for(int i = 1;i<str.length();i++){
					strCurrNode.next = new CNode(str.charAt(i));
					strCurrNode = strCurrNode.next;
				}
				// connect nodes
				connect(currNode,first);
				connect(strCurrNode, endCurrNode);
				length = length - (end - start) + str.length();
				}
		}
		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder.  For this method
	// you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	public String substring(int start, int end){
		String out = "";
		char[] chars = new char[(end - start)];
		// find start node
		CNode currNode = firstC;
		for(int i = 0;i < start;i++ ){
			currNode = currNode.next;
		}
		for(int i = 0;i< (end -start); i++){
			chars[i] = currNode.data;
			currNode = currNode.next;
		}
		for(char c : chars){
			out = out + c;
		}
		return out;
	}

	// Return as a String the reverse of the contents of the MyStringBuilder.  Note
	// that this does NOT change the MyStringBuilder in any way.  See substring()
	// above for the basic approach.
	public String revString()
	{
		String out = "";
		CNode currNode = firstC;
		for(int i = 0; i<length;i++){
			out = currNode.data + out;
			currNode = currNode.next;
		}
		return out;
	}
	
	// Connects two nodes to each other
	private void connect(CNode a, CNode b){
		a.next = b;
		b.prev = a;
	}

	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data, next and prev fields directly.
	public class CNode
	{
		private char data;
		private CNode next, prev;

		public CNode(char c)
		{
			data = c;
			next = null;
			prev = null;
		}

		public CNode(char c, CNode n, CNode p)
		{
			data = c;
			next = n;
			prev = p;
		}
	}
}
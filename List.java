//-----------------------------------------------------------------------------
// List.java
// A Doubly Linked List implementation for the Matrix ADT
//
// Yona Edell
// CruzID: yedell
// CMPS 101
// PA3
// 02/01/2017
//-----------------------------------------------------------------------------

public class List {
	// Node Class Definition
	private class Node {
		Object data;
		Node next;
		Node prev;

		// Node Constructor
		Node(Object d) {
			data = d;
		}

		// equals(): overrides Object's equals() method
		public boolean equals(Object x) {

			boolean eq = false;
			Node that;

			if (x instanceof Node) {
				that = (Node) x;
				eq = (this.data.equals(that.data));
			}

			return eq;
		}

		// toString(): overrides Object's toString() method
		public String toString() {
			return String.valueOf(data);
		}
	}
	
	// Fields
	private int length; // number of elements in List
	private int index;
	private Node front;
	private Node back;
	private Node cursor;

	// Default constructor
	List() {
		length = 0;
		index = -1;
		front = back = cursor = null;
	}
	
	// Access Functions -----------------------------------------------------------
	
	int length() {
		return length;
	}
		
	int index() {
		// If cursor is defined, returns the index of the cursor element,
		// otherwise returns -1. if(cursor == null), return -1;
		if(cursor != null) {
			return index;
		}
		return -1;
	}

	Object front() {
		// Returns front element. Pre: length()>0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"front()\" on an empty List");
		}
		return front.data;
	}

	Object back() {
		// Returns back element. Pre: length()>0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"back()\" on an empty List");
		}
		return back.data;
	}

	Object get() {
		// Returns cursor element. Pre: length()>0, index()>=0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"get()\" on an empty List");
		}
		if (index < 0) {
			throw new RuntimeException("Cannot call \"get()\" on a List with undefined index");
		}

		return cursor.data;
	}

	public boolean equals(Object L) {
		// Returns true if this List and L are the same integer
		// sequence. The cursor is ignored in both lists.
		if (((List) L).length() != length) { // Check if lengths are the same
			return false;
		}
		boolean eq = true;
		Node aNode = front, bNode = ((List) L).front;
		for (int i = 0; i < length; i++) {
			if (aNode.data != bNode.data) {
				eq = false; // If data doesn't match, returns false
				break;
			}
			aNode = aNode.next;
			bNode = bNode.next;
		}
		return eq;
	}

	// Manipulation Procedures ----------------------------------------------------
	
	void clear() {
		// Resets this List to its original empty state.
		length = 0;
		index = -1;
		front = back = cursor = null;
	}

	void moveFront() {
		// If List is non-empty, places the cursor under the front element,
		// otherwise does nothing.		
		if (length != 0) {
			cursor = front;
			index = 0;
		}
	}

	void moveBack() {
		// If List is non-empty, places the cursor under the back element,
		// otherwise does nothing.
		if (length != 0) {
			cursor = back;
			index = length - 1;
		}
	}

	void movePrev() {
		// If cursor is defined and not at front, moves cursor one step toward
		// front of this List, if cursor is defined and at front, cursor becomes
		// undefined, if cursor is undefined does nothing.		
		if(index < length && index >= 0){
			if (index != 0){
				index--;
				cursor = cursor.prev;
			} else { // becomes undefined
				cursor = null;
				index = -1;
			}
		}
	}

	void moveNext() {
		// If cursor is defined and not at back, moves cursor one step toward
		// back of this List, if cursor is defined and at back, cursor becomes
		// undefined, if cursor is undefined does nothing.
		if (index < length && index >= 0) {
			if (index != length - 1) {
				cursor = cursor.next;
				index++;
			} else { // becomes undefined
				cursor = null;
				index = -1;
			}
		}
	}

	void append(Object data) {
		// Insert new element into this List. If List is non-empty,
		// insertion takes place after back element.
		Node temp = new Node(data);
		if (length == 0) { // If empty list
			front = back = temp;
		} else { // If list is not empty
			back.next = temp;
			temp.prev = back;
			back = back.next;
		}
		length++;
	}

	void prepend(Object data) {
		// Insert new element into this List. If List is non-empty,
		// insertion takes place before front element.

		Node temp = new Node(data);
		if (length == 0) { // If empty list
			front = back = temp;
		} else { // If non empty List
			front.prev = temp;
			temp.next = front;
			front = front.prev;
		}
		if (index >= 0) {
			index++;
		}
		length++;
	}

	void insertBefore(Object data) {
		// Insert new element before cursor.
		// Pre: length()>0, index()>=0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"insertBefore()\" on an empty List");
		}
		if(index < 0){
			throw new RuntimeException("Cannot call \"insertBefore()\" on a List with undefined index");
		}
		if (index == 0) { // If index is at front, prepend
			prepend(data);
		} 
		else {
			Node temp = new Node(data);
			cursor.prev.next = temp;
			temp.next = cursor;
			cursor.prev = temp;
			temp.prev = front;
			length++;
		}
		index++;
	}

	void insertAfter(Object data) {
		// Inserts new element after cursor.
		// Pre: length()>0, index()>=0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"insertAfter()\" on an empty List");
		}
		if(index < 0){
			throw new RuntimeException("Cannot call \"insertAfter()\" on a List with undefined index");
		}
		if (index == length - 1) { // If index is at end, append to back
			append(data);
		} else {
			Node temp = new Node(data);
			temp.next = cursor.next;
			temp.prev = cursor;
			cursor.next = temp;
			temp.next.prev = temp;
			length++;
		}
	}

	void deleteFront() {
		// Deletes the front element. Pre: length()>0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"deleteFront()\" on an empty List");
		}
		if (length == 1) { // If length 1, head and tail are the same
			clear();
		} else {
			front = front.next;
			front.prev = null;
			length--;
			
			if(index == 0) { // If cursor is at front, it's now reset
				cursor = null;
				index = -1;
				return;
			}
			index--;
		}
	}

	void deleteBack() {
		// Deletes the back element. Pre: length()>0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"deleteBack()\" on an empty List");
		}
		if (length == 1) { // If length 1, head and tail are the same
			clear();
		} else {
			back = back.prev;
			back.next = null;
			length--;
			
			if(index == length -1) { // If cursor is at back, it's now reset
				cursor = null;
				index = -1;
			}
		}
	}

	void delete() {
		// Deletes cursor element, making cursor undefined.
		// Pre: length()>0, index()>=0
		if (length == 0) {
			throw new RuntimeException("Cannot call \"delete()\" on an empty List");
		}
		if(index < 0){
			throw new RuntimeException("Cannot call \"delete()\" on a List with undefined index");
		}
		if (index == 0) { // When index is at front
			deleteFront();
		} else if (index == length - 1) { // When index is at back
			deleteBack();
		} else {
			cursor.prev.next = cursor.next; // Previous node points to the
							// cursor's next node
			cursor.next.prev = cursor.prev; // Node after target points to
							// target's previous node
			length--;
			index = -1;
			cursor = null;
		}
	}

	// Other operations -----------------------------------------------------------
	
	public String toString() {
		// Overrides Object's toString method. Returns a String
		// representation of this List consisting of a space
		// separated sequence of integers, with front on left.
		StringBuffer sb = new StringBuffer();
		Node N = front;
		while (N != back) {
			sb.append(N.toString());
			sb.append(" ");
			N = N.next;
		}
		if(N == back)
		{
			sb.append(N.toString());
		}

		return new String(sb);
	}

//	List copy() {
//		// Returns a new List representing the same integer sequence as this
//		// List. The cursor in the new list is undefined, regardless of the
//		// state of the cursor in this List. This List is unchanged.
//
//		List copy = new List();
//		Node N = front;
//		while (N != null) {
//			copy.append(N.data);
//			N = N.next;
//		}
//		return copy;
//	}
//
//	List concat(List L) {
//		// Returns a new List which is the concatenation of
//		// this list followed by L. The cursor in the new List
//		// is undefined, regardless of the states of the cursors
//		// in this List and L. The states of this List and L are
//		// unchanged.
//		List concat = this.copy();
//		Node N = L.front;
//		while (N != null) {
//			concat.append(N.data);
//			N = N.next;
//		}
//		return concat;
//	}
	
}

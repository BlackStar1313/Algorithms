package structures;

/**
 * The {@code LinkedListStack} class represents a last-in-first-out
 * (LIFO) stack of objects using a {@code LinkedList}. The usual
 * {@code push} and {@code pop} operations are provided, as well as a
 * method to {@code peek} at the top item on the stack and a method to test
 * for whether the stack is {@code empty}
 * 
 * @author Njoyim Peguy
 *
 * @param <T>	Type of component elements
 */
public final class LinkedListStack<T> {
	
	Node<T> top;//The top node
	Node<T> end;//A sentinel node which represents the end of the node.
	
	public LinkedListStack() {
		end = Node.Build(null, null);//Create the END node
		top = end;//Make the TOP and the END point to each other!!
		end.next = end;//Make the next END pointer, points to itself.
	}
	
	
	public void push(T element) {

		if(top == end) {//If the stack is empty, i.e., if there is no TOP element then this node will become the TOP element.
			final Node<T> newNode = Node.Build(element, end);
			top = newNode;
		}else {
			final Node<T> next_node = top;//Hold the address of the TOP
			//Create a node such that the next pointer of this new node, points to the TOP element.
			final Node<T> newNode = Node.Build(element, next_node);
			top = newNode;//Then we need to switch the TOP element.
		}
	}
	
	public T peek() {
		return !IsEmpty() ? top.data : null;
	}
	
	public T pop() {
		if(IsEmpty()) {
			throw new IllegalStateException("The stack is empty");
		}
		Node<T> oldTop = top;//Hold the address of the TOP
		T elt = oldTop.data;//Take data from the oldTop 
		top = top.next;//Advance the TOP to the next element on the linked list
		
		//Dereferencing everything about the oldTop
		oldTop.data = null;
		oldTop.next = null;
		return elt;
	}
	
	public boolean IsEmpty() {
		return top == end;
	}
	
	public final void clear() {
		Node<T> current = top;
		while(current != end) {
			Node<T> next_node = current.next;
			current.data = null;
			current.next = null;
			current = next_node;
		}
	}
	
	public String toString() {
		String s = "[";
		Node<T> current = top;
		
		while(current != end) {
			s+=current.data;
			if(current.next != end) {
				s += ", ";
			}
			current = current.next;
		}
		s += "]";
		return s;
	}
}

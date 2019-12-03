package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code LinkedListQueue} class represents a first-in-first-out
 * (FIFO) stack of objects using a circular {@code LinkedList} for which the last node points to the first node.
 * A circular structure allows one to handle the structure by a single pointer, instead of two. 
 * The usual {@code Enqueue} and {@code Dequeue} operations are provided, as well as 
 * a method to test for whether the stack is {@code empty}.
 * 
 * @author Peguy Calusha
 *
 * @param <T>	Type of component elements
 */
public class LinkedListQueue<T> implements Iterable<T>{

	private Node<T> last;//A pointer to the last Node.
	private int size;

	public void Enqueue(T data) {
		//A pointer to the last node (i.e., the pointer which holds the last node before the creation of a new one).
		Node<T> oldLast = null;
		
		if(last != null) {//don't take the address when we create the very first node.
			oldLast = last;
		}
		final Node<T> newNode = Node.Build(data);//Create a new node.
		last = newNode;//Take a new address

		if(IsEmpty()) {//If the Queue is empty, i.e., if there is no LAST element then this node will become the LAST node.
			//The last node is also the first one.
			last.next = last;
		}else {
			//Wrap up the queue to make it circular.
			last.next = oldLast.next;
			oldLast.next = last;
		}
		size++;
		return;
	}

	public T Dequeue() {
		if(IsEmpty()) {
			throw new IllegalStateException("The Queue is empty");
		}

		Node<T> oldFirst = last.next;//Take the first node (LAST.next is the first node)
		T currentData = oldFirst.data;//Take first element from the first node.
		last.next = oldFirst.next;//point to the new first node.
		
		//Unreferencing the current first node.
		oldFirst.data = null;
		oldFirst = null;
		size--;
		return currentData;
	}

	/**
	 * 
	 * @return the size of elements within the circular linked list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Removing all links
	 */
	public void clear() {
		Node<T> current = last.next;

		while(current != null) {
			Node<T> next_node = current.next;
			current.data = null;
			current.next = null;
			current = next_node;
		}
	}

	/**
	 * Check whether the circular linked list is empty or not.
	 * 
	 * @return	true if the list is empty, otherwise false.
	 */
	public boolean IsEmpty() {
		return size==0;
	}

	public String toString() {
		if(IsEmpty()) {
			System.out.println("The Queue is empty");
		}else {
			String s = "[";
			Node<T> head = last.next;
			Node<T> current = last.next;

			do {
				s+=current.data;
				if(current.next != head) {
					s += ", ";
				}
				current = current.next;
			}while(current != head);
			s += "]";
			return s;
		}
		
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		LinkedListQueueIterator llQueueIterator = new LinkedListQueueIterator();
		return llQueueIterator;
	}

	private class LinkedListQueueIterator implements Iterator<T>{
		
		private Node<T> current = last.next;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException("The node does not exist!");
			}
			
			T data = current.data;
			current = current.next;
			return data;
		}
		
		@Override
		public void remove() {
			Dequeue();
			return;
		}
	}
}

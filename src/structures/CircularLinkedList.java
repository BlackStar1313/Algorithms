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
public class CircularLinkedList<T> implements Iterable<T>{

	private Node<T> last;//A pointer to the last Node.
	private int size;

	public void enqueue(T data) {
		//A pointer to the last node (i.e., the pointer which holds the last node before the creation of a new one).
		Node<T> oldLast = null;
		
		if(last != null) {//don't take the address when we create the very first node.
			oldLast = last;
		}
		final Node<T> newNode = Node.Build(data);//Create a new node.
		last = newNode;//Take a new address

		if(isEmpty()) {//If the Queue is empty, i.e., if there is no LAST element then this node will become the LAST node.
			//The last node is also the first one.
			last.next = last;
		}else {
			//Wrap up the queue to make it circular.
			last.next = oldLast.next;
			oldLast.next = last;
		}
		size++;
	}

	public T dequeue() {
		if(isEmpty()) {
			throw new IllegalStateException("The Queue is empty");
		}

		Node<T> oldFirst = last.next;//Take the first node (LAST.next is the first node)
		T currentData = oldFirst.data;//Take first element from the first node.
		last.next = oldFirst.next;//point to the new first node.
		
		//Unreferencing the current first node.
		oldFirst.data = null;
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
	public boolean isEmpty() {
		return size==0;
	}

	public String toString() {
		if(isEmpty()) {
			System.out.println("The Queue is empty");
		}else {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			Node<T> head = last.next;
			Node<T> current = last.next;
			do {
				sb.append(current.data);
				if(current.next != head) {
					sb.append(", ");
                    current = current.next;
				}
			}while(current != head);
			sb.append("]");
			return sb.toString();
		}
		
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListQueueIterator();
	}

	private class LinkedListQueueIterator implements Iterator<T>{

		private Node<T> head = last.next;
		private Node<T> current = head;
		private boolean visited = false;

		@Override
		public boolean hasNext() {
			if(isEmpty()) return false;
			return !visited;
		}

		@Override
		public T next() {
            T data = current.data;
			current = current.next;
			visited = current == head;
			return data;
		}
		
		@Override
		public void remove() {
			dequeue();
		}
	}
}

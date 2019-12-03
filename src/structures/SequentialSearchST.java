package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Symbol table implementation with sequential search in an
 * unordered linked list of key-value pairs.
 * 
 * @author Peguy Calusha.
 *
 * @param <K>	type of key components.
 * @param <V>	type of value components.
 */
public class SequentialSearchST<K extends Comparable<K>, V> implements Iterable<K> {

	private int size;
	private SSNode<K, V> head;
	private SSNode<K, V> end;

	public SequentialSearchST() {
		end = SSNode.build(null, null);
		end.next = null;
		head = end;
	}

	public boolean add(K key, V value) {

		if(value == null) {
			delete(key);
			return false;
		}

		// Checking if a given key does already exist!
		for(SSNode<K, V> x = head; x != end; x = x.next) {
			if(x.key.equals(key)) {
				x.value = value;
				return false;
			}
		}

		// Adding new node in front
		final SSNode<K, V> next_node = head;
		final SSNode<K, V> newNode = SSNode.build(key, value, next_node);
		head = newNode;
		size++;
		return true;
	}

	
	public boolean delete(K key) {
		
		if (key == null){
			throw new IllegalArgumentException("argument to delete() is null");
		}
		
		if(isEmpty()) {
			throw new NoSuchElementException("There is nothing to be delete!!");
		}
		
		SSNode<K, V> nodeToBeDeleted = head;
		// If head node itself holds the key to be deleted
		if(nodeToBeDeleted.key.equals(key)) {
			head = nodeToBeDeleted.next;
			nodeToBeDeleted.key = null;
			nodeToBeDeleted.value = null;
			nodeToBeDeleted = null;
			size--;
			return true;
		}else {
			
			end.key = key;// a small trick of programming.
			
			// Search for the key to be deleted, keep track of the 
		    // previous node as we need to change 'previousNode.next' 
			SSNode<K, V> previousNode = end;
			while(!nodeToBeDeleted.key.equals(key)) {
				previousNode = nodeToBeDeleted;
				nodeToBeDeleted = nodeToBeDeleted.next;
			}

			// Unlink the node from linked list if it is not the 'end'
			if(nodeToBeDeleted != end) {
				previousNode.next = nodeToBeDeleted.next;
				nodeToBeDeleted.key = null;
				nodeToBeDeleted.value = null;
				nodeToBeDeleted = null;
				end.key = null;
				size--;
				return true;
			}
		}

		// The key was not present in linked list .
		return false;
	}

	public void clear() {
		head = null;
		end = null;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}

	public V get(K key) {

		end.key = key;
		SSNode<K, V> x = head;
		while(!x.key.equals(key)) {
			x = x.next;
		}

		//found!
		if(x != end) {
			end.key = null;
			return x.value;
		}
		end.key = null;
		return null;
	}

	@Override
	public Iterator<K> iterator() {
		Queue<K> queue = new Queue<>();

		for(SSNode<K, V> x = head; x != end; x = x.next) {
			queue.Enqueue(x.key);
		}
		return queue;
	}
}

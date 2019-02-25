package structures;

import java.util.Iterator;

public class Queue<T> implements Iterator<T>{
	
	private T[] data;
	private int tail, head, currentSize;
	private static final int DEFAULT_SIZE = 16;
	
	// Fields for Iterator<T>
	private T lastAccessed;
	private int pos;

	public Queue() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	private Queue(int capacity) {
		data = (T[]) new Object[capacity];//Casting is very important!!
		tail = head = -1;
	}

	@SuppressWarnings("unchecked")
	public void grow(int newCapacity) {
		
		T[] newData = (T[]) new Object[newCapacity];

		for(int j = 0; j <= tail; j++) {
			newData[j] = data[j];
		}
		data = newData;
		newData = null;//To help GC!!
		return;
	}

	public void Enqueue(T element){
		if(isFull()) {
			grow(data.length * 2);
		}
		data[++tail] = element;
		currentSize++;
	}

	public T Dequeue(){
		T elt = null;
		if(IsEmpty()) {
			throw new IllegalStateException("The Queue is empty");
		}else {
			elt = data[++head];
			currentSize--;
			data[head] = null;//To help GC!!
		}
		return elt;
	}
	
	public int size() {
		return currentSize;
	}

	public boolean isFull() {
		return (tail == data.length-1);
	}

	public boolean IsEmpty(){
		return (currentSize == 0);
	}
	
	@Override
	public String toString() {
		String s = "[";
		for(int i = head+1; i <= tail; i++) {
			s += data[i].toString();
			if(i < tail) {
				s += ", ";
			}
		}
		s += "]";
		return s;
	}

	@Override
	public boolean hasNext() {
		return pos < currentSize;
	}

	@Override
	public T next() {
		if(!hasNext()) {
			return null;
		}
		lastAccessed = data[pos];
		pos++;
		return lastAccessed;
	}
}
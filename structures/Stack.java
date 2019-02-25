package structures;

import java.util.Comparator;

public class Stack<T>{
	private T[] data;
	private int currentSize, indexOfTopElement;
	private static final int DEFAULT_SIZE = 2;

	public Stack() {
		this(DEFAULT_SIZE);
	}

	
	public Stack(int initialCapacity) {
		data = (T[]) new Comparable[initialCapacity];
		indexOfTopElement = -1;
	}

	@SuppressWarnings("unchecked")
	public void EnsureCapacity(int newCapacity) {
		T[] newData = (T[]) new Comparable[newCapacity];

		for(int j = 0; j < currentSize; j++) {
			newData[j] = data[j];
		}
		data = newData;
		newData = null; //To help GC!!
		return;
	}

	/**
	 * Push elements in LIFO.
	 * 
	 * @param element	the element to be pushed.
	 */
	public void push(T element){
		if(isFull()) {
			EnsureCapacity(currentSize * 2);
		}
		data[++indexOfTopElement] = element;
		currentSize++;
	}

	public T peek(){
		return indexOfTopElement >= 0 ? data[indexOfTopElement] : null;
	}

	public T pop(){
		T elt = null;
		if(isEmpty()) {
			throw new IllegalStateException("The stack is empty");
		}else {
			elt = data[indexOfTopElement];
			data[indexOfTopElement] = null;//To help GC!!
			indexOfTopElement--;
			currentSize--;
		}
		return elt;
	}
	
	/**
	 * Find the minimum element of this stack.
	 * 
	 * @return	the min element.
	 */
	/*public T min() {
		
		if(this.isEmpty()) {
			throw new IllegalStateException("The stack is empty");
		}
		
		Stack<T> temp = new Stack<>();// creating a temporary stack.
		T min = this.pop();
		temp.push(min);// pushing right after so that the content of this stack will not altered later.
		
		while(!this.isEmpty()) {
			T elt = this.pop();
			
			if(comp.compareTo(min) < 0) {
				min = elt;
			}
			temp.push(elt);
		}
		
		while(!temp.isEmpty()) {
			this.push(temp.pop());// restoring the remaining elements into the original stack.
		}
		return min;
	}*/

	public boolean isFull(){
		return currentSize == data.length;
	}

	public boolean isEmpty(){
		return currentSize == 0;
	}
	
	@Override
	public String toString() {
		String str = "[";
		int i;
		for(i = 0; i < currentSize - 1; i++) {
			str += data[i] + ", ";
		}
		str += data[i] + "]";
		return str;
	}
}

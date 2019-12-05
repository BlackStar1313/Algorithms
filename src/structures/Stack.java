package structures;

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
	private void ensureCapacity(int newCapacity) {
		T[] newData = (T[]) new Comparable[newCapacity];

		for(int j = 0; j < currentSize; j++) {
			newData[j] = data[j];
		}
		data = newData;
	}

	/**
	 * Push elements in LIFO.
	 * 
	 * @param element	the element to be pushed.
	 */
	public void push(T element){
		if(isFull()) {
			ensureCapacity(currentSize * 2);
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

	public boolean isFull(){
		return currentSize == data.length;
	}

	public boolean isEmpty(){
		return currentSize == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		int i;
		for(i = 0; i < currentSize - 1; i++) {
			str.append(data[i]).append(", ");
		}
		str.append(data[i]).append("]");
		return str.toString();
	}
}

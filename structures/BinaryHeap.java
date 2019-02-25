package structures;

/**
 * The binary heap is a data structure that can efficiently support the basic priority-queue operations such as <em>insert</em> and <em>remove</em> operations,
 * along with methods <em>peek</em> which returns the highest-priority element but does not modify the queue, testing if the priority queue is empty, and iterating through the keys.</br></br>
 * 
 * In a heap, the parent of the node in position <em>k</em> is in position <em>k/2</em> and, conversely, the two children of the node in position <em>k</em> are in positions <em>2k</em> and <em>2k+1</em>. 
 * Instead of using explicit links as in the binary tree structures, we can travel up and down by doing simple arithmetic on array indices: 
 * to move up the tree from <em>a[k]</em> we set <em>k</em> to <em>k/2</em>; 
 * to move down the tree we set <em>k</em> to <em>2k</em> or <em>2k+1</em> (the array is supposed to be <em>1</em>-based).
 * 
 * @author Njoyim Peguy
 *
 * @param <E>	the type of elements.
 */
public abstract class BinaryHeap<E extends Comparable<E>> implements Iterable<E>{

	private static final int DEFAULT_SIZE = 16;
	protected int N;
	protected E[] keys;

	public BinaryHeap() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Create a max priority queue with a given initial capacity.
	 * 
	 * @param initialCapacity	the initial capacity of the queue.
	 */
	protected BinaryHeap(int initialCapacity){
		keys = (E[]) new Comparable[initialCapacity];
	}

	/**
	 * Create a max priority queue with an initial given array. It takes O(n) time to build the heap using sink method.
	 * 
	 * @param array		the keys to be turned into a max priority queue.
	 */
	protected BinaryHeap(E[] array) {
		keys = (E[]) new Comparable[array.length + 1];
		N = array.length;

		for(int i = 0; i < N; i++) {
			keys[i+1] = array[i];
		}
		
		for(int k = N/2; k >= 1; k--) {
			topDownReheapify(k);
		}
	}
	
	/**
	 * A copy constructor that creates a max priority queue from another priority queue.
	 * @param heap
	 */
	protected BinaryHeap(BinaryHeap<E> heap) {
		this.keys = heap.keys;
		N = heap.N;
	}

	/***********************************************************************
	 * 		Helper functions to restore the heap invariant.
	 **********************************************************************/

	/**
	 * For Max orientation, if a node's element becomes <em>larger</em> than its node's parent's key, 
	 * then we have to keep fixing the violation by exchanging the node with its parent until we reach a node with a larger key, or the root.</br></br>
	 * 
	 * For Min orientation, if a node's element becomes <em>smaller</em> than its node's parent's key, 
	 * then we have to keep fixing the violation by exchanging the node with its parent until we reach a node with a smaller key, or the root.
	 *
	 * @param k	the starting index.
	 */
	protected abstract void bottomUpReheapify(int k);


	/**
	 * For Max orientation, if a node's element becomes <em>smaller</em> than one or both of its two node's children's key, 
	 * then we have to keep fixing the violation by exchanging the node with the larger of its two children until we reach a node with both children smaller (or equal), or the bottom.</br></br>
	 * 
	 * For Min orientation, if a node's element becomes <em>greater</em> than one or both of its two node's children's key, 
	 * then we have to keep fixing the violation by exchanging the node with the smaller of its two children until we reach a node with both children greater (or equal), or the bottom.
	 *
	 * @param k	the starting index.
	 */
	protected abstract void topDownReheapify(int k);
	
	/**
	 * Create a distinct heap in memory that is to say all fields copied will bear no relation to this object.
	 * @return	a distinct copy;
	 */
	public abstract BinaryHeap deepCopy();
	
	/**
	 * Create a shallow copy that is to say all fields copied will refer to this object in memory hence the name shallow.
	 * 
	 * @return	a shallow copy of this object
	 */
	public abstract BinaryHeap shallowCopy();

	/**
	 * @return the highest-priority element but does not modify the queue.
	 */
	public E peek() {
		return keys[1];
	}

	/**
	 * Add new elements in the priority queue.
	 * @param elem	the element to be added in the queue.
	 */
	public void insert(E elem, E...elts) {

		if(N == keys.length-1) {
			resize(2*keys.length);
		}

		keys[++N] = elem;
		bottomUpReheapify(N);
		
		if(elts.length != 0 && elts != null) {
			for(int i = 0; i < elts.length; i++) {
				keys[++N] = elts[i];
				bottomUpReheapify(N);
			}
		}
	}

	/**
	 * Check whether the priority is empty or not.
	 * @return	true if the priority queue is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return N==0;
	}

	/**
	 * Ensure the capacity of the heap.
	 * 
	 * @param newCapacity	the new capacity of the queue.
	 */
	public void resize(int newCapacity) {

		E[] newData = (E[]) new Comparable[newCapacity];

		for(int j = 1; j <= N; j++) {
			newData[j] = keys[j];
		}

		keys = newData;
		newData = null; //To help GC!!
		return;
	}
	
	/**
	 * Clearing the data structure.
	 */
	public void clear() {
		this.N = 0;
		this.keys = null;
	}

	@Override
	public String toString() {
		String str = "[";
		for(int i = 1; i <= N; i++) {
			str += keys[i];
			if(i < N) {
				str += ", ";
			}
		}
		str += "]";
		return str;
	}
}

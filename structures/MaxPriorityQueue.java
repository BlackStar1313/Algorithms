package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import helper.GenericHelper;

/**
 *  A binary tree is max heap-ordered if the key in each node is larger than or equal to the keys in that node’s two children (if any).</br>
 *  The largest key in a max heap-ordered binary tree is found at the root.</br>
 * 
 * @author Njoyim Peguy
 *
 * @param <E>	the type of elements
 */
public final class MaxPriorityQueue<E extends Comparable<E>> extends BinaryHeap<E> {


	public MaxPriorityQueue(int capacity) {
		super(capacity);
	}

	public MaxPriorityQueue(E[] keys) {
		super(keys);
	}
	
	public MaxPriorityQueue(MaxPriorityQueue<E> heap) {
		super(heap);
	}

	/**
	 * Take the largest key off the top, put the item from the end of the heap at the top, decrement the size of the heap, 
	 * and then sink down through the heap with that key to restore the heap condition.
	 * 
	 * @return	the highest-priority element.
	 */
	public E deleteMax() {
		
		if(isEmpty()) {
			throw new NoSuchElementException("Priority queue is empty!!");
		}
		
		E max = peek();// retrieve the max elt.
		GenericHelper.swap(keys, 1, N--);// Exchange with last item.
		keys[N+1] = null;// avoid loitering
		topDownReheapify(1);// restore heap property.
		
		if(N > 0 && N == (keys.length / 4)) {// resizing the heap
			resize(keys.length / 2);
		}
		assert isMaxHeap();// checking the heap property is not violated.
		return max;
	}

	@Override
	protected void bottomUpReheapify(int k) {

		while(k > 1 && GenericHelper.isGreater(keys[k], keys[k/2])) {
			GenericHelper.swap(keys, k, k/2);
			k /= 2;
		}
	}


	@Override
	protected void topDownReheapify(int k) {

		while(2*k <= N) {

			int j = 2*k;

			if(j < N && GenericHelper.isLess(keys[j], keys[j+1])) j++;

			if(!GenericHelper.isLess(keys[k], keys[j])) break;

			GenericHelper.swap(keys, k, j);
			k = j;
		}
	}
	
	@Override
	/**
	 * With the co-variance of the variables we can re-write this function like so
	 */
	public MaxPriorityQueue<E> deepCopy() {
		MaxPriorityQueue<E> maxPriorityQueue = new MaxPriorityQueue<>(this.N);
		for(int i = 1; i <= this.N; i++) {
			maxPriorityQueue.insert(this.keys[i]);
		}
		return maxPriorityQueue;
	}

	@Override
	/**
	 * With the co-variance of the variables we can re-write this function like so
	 */
	public MaxPriorityQueue<E> shallowCopy() {
		MaxPriorityQueue<E> maxPriorityQueue = new MaxPriorityQueue<>(this);
		return maxPriorityQueue;
	}

	/**
	 * Check whether the heap is max-oriented.
	 * 
	 * @return	{@code true} if the heap is max-oriented at index i, otherwise {@code false}.
	 */
	public boolean isMaxHeap() {
		return isMaxHeap(1);
	}

	private boolean isMaxHeap(int i) {

		/*********************
		 * 1st way of doing  *
		 *********************/

		int left = 0, right = 0;

		for(int k = i; k <= 1 + (int) Math.ceil((double) N/2); k++) {

			left = 2*k;
			right = 2*k + 1;

			// we check whether both children's key at position 2k and 2k+1 is greater than its parent's key at position k.
			if(left <= N && GenericHelper.isGreater(keys[left], keys[k]) || 
					right <= N && GenericHelper.isGreater(keys[right], keys[k])) {
				return false;
			}
		}

		return true;


		/*********************
		 * 2nd way of doing  *
		 *********************/
		/*Queue<Integer> queue = new Queue<>();

		queue.Enqueue(i);//auto-boxing(int -> Integer)
		int left = 0, right = 0, k = 0;

		while(!queue.IsEmpty()) {

			//un-boxing...
			k = queue.Dequeue();//un-boxing(Integer -> int)
			left = 2*k;
			right = 2*k + 1;

			if((left <= N) && isLess(k, left)) {
				return false;
			}else {
				if(left <= N) {
					queue.Enqueue(left);//auto-boxing(int -> Integer)
				}
			}

			if((right <= N) && isLessr(k, right)) {
				return false;
			}else {
				if(right <= N) {
					queue.Enqueue(right);//auto-boxing(int -> Integer)
				}
			}
		}

		return true;*/


		/*********************
		 * 3rd way of doing  *
		 *********************/
		//A recursive version.
		/*if(i > size)return true;
		 int left = 2*i;
		 int right = 2*i+1;
		 if((left <= size) && isLess(i, left))return false;
		 if((right <= size) && isLess(i, right))return false;
		 return isMaxHeap(left) && isMaxHeap(right);*/
	}

	@Override
	public Iterator<E> iterator() {
		MaxHeapIterator it = new MaxHeapIterator();
		return it;
	}


	/**
	 * Create an iterator that iterates over all of the keys on the max priority queue in descending order.
	 * 
	 * @author Njoyim Peguy
	 *
	 */
	private final class MaxHeapIterator implements Iterator<E>{
		
		private final MaxPriorityQueue<E> Copy;
		
		private MaxHeapIterator() {
			Copy = deepCopy();
		}
		
		@Override
		public void remove() {
			Copy.deleteMax();
		}

		@Override
		public boolean hasNext() {
			return !Copy.isEmpty();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Can't find the next element!!");
			}
			return Copy.deleteMax();
		}

	}

}

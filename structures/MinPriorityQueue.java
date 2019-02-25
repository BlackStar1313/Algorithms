package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import helper.GenericHelper;

public final class MinPriorityQueue<E extends Comparable<E>> extends BinaryHeap<E> {


	public MinPriorityQueue() {
		super();
	}
	
	public MinPriorityQueue(int initialCapacity) {
		super(initialCapacity);
	}

	public MinPriorityQueue(E[] array) {
		super(array);
	}

	public MinPriorityQueue(BinaryHeap<E> heap) {
		super(heap);
	}

	public E deleteMin() {
		if(isEmpty()) {
			throw new NoSuchElementException("Priority queue is empty!!");
		}

		E min = peek();// retrieve the max elt.
		GenericHelper.swap(keys, 1, N--);// Exchange with last item.
		keys[N+1] = null;// avoid loitering
		topDownReheapify(1);// restore heap property.

		if(N > 0 && N == (keys.length / 4)) {// resizing the heap
			resize(keys.length / 2);
		}
		return min;
	}

	/**
	 * Check whether the heap is max-oriented.
	 * 
	 * @return	{@code true} if the heap is max-oriented at index i, otherwise {@code false}.
	 */
	public boolean isMinHeap() {
		return isMinHeap(1);
	}

	/**
	 * @see {#link isMinHeap()#this method}.
	 * @param k
	 * @return
	 */
	private boolean isMinHeap(int i) {
		int left = 0, right = 0;

		for(int k = i; k <= 1 + (int) Math.ceil((double) N/2); k++) {

			left = 2*k;
			right = 2*k + 1;

			// we check whether both children's key at position 2k and 2k+1 is lesser than its parent's key at position k.
			if(left <= N && GenericHelper.isLess(keys[left], keys[k]) || 
					right <= N && GenericHelper.isLess(keys[right], keys[k])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Iterator<E> iterator() {
		MinHeapIterator it = new MinHeapIterator();
		return it;
	}

	@Override
	public void bottomUpReheapify(int k) {
		while(k > 1 && GenericHelper.isLess(keys[k], keys[k/2])) {
			GenericHelper.swap(keys, k, k/2);
			k /= 2;
		}
	}

	@Override
	public void topDownReheapify(int k) {
		
		while(2*k <= N) {

			int j = 2*k;

			if(j < N && GenericHelper.isGreater(keys[j], keys[j+1])) j++;

			if(!GenericHelper.isGreater(keys[k], keys[j])) break;

			GenericHelper.swap(keys, k, j);
			k = j;
		}
	}

	@Override
	public MinPriorityQueue<E> deepCopy() {
		MinPriorityQueue<E> deepMinQ = new MinPriorityQueue<>(this.N);
		for(int i = 1; i <= this.N; i++) {
			deepMinQ.insert(this.keys[i]);
		}
		return deepMinQ;
	}

	@Override
	public MinPriorityQueue<E> shallowCopy() {
		MinPriorityQueue<E> shalloMinQ = new MinPriorityQueue<>(this);
		return shalloMinQ;
	}

	/**
	 * Create an iterator that iterates over all of the keys on the min priority queue in descending order.
	 * 
	 * @author Njoyim Peguy
	 *
	 */
	private final class MinHeapIterator implements Iterator<E>{

		private final MinPriorityQueue<E> minQ;

		private MinHeapIterator() {
			minQ = deepCopy();
		}

		@Override
		public void remove() {
			minQ.deleteMin();
		}

		@Override
		public boolean hasNext() {
			return !minQ.isEmpty();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Can't find the next element!!");
			}
			return minQ.deleteMin();
		}

	}

}

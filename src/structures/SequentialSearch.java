package structures;

import helper.GenericHelper;

/**
 * 
 * @author Njoyim Peguy
 *
 * @param <K>
 */
public class SequentialSearch<K extends Comparable<K>> {

	private K keys[];
	private int N;
	private static final int DEFAULT_SIZE = 10;
	private boolean isSorted;

	public SequentialSearch() {
		this(DEFAULT_SIZE);
	}

	public SequentialSearch(int initialCapacity) {
		if(initialCapacity > 0) {
			keys = (K[]) new Comparable[initialCapacity];
		}else {
			throw new IllegalArgumentException("Illegal Capacity: "+
					initialCapacity);
		}
	}

	public SequentialSearch(K...keys) {
		if(keys != null && keys.length > 0) {
			this.keys = keys;
			isSorted = GenericHelper.isSorted(keys);
		}else {
			throw new IllegalArgumentException("Illegal array: "+
					keys);
		}
	}


	public void Put(K key) {

	}

	public K get(K key) {
		if(isEmpty()) {
			return null;
		}

		int greatestIndex = N - 1;
		K tempKey = keys[greatestIndex];
		K targetKey = null;

		// put a sentinel at the end of the array that is greater than the target key.
		keys[greatestIndex] = key;
		int i = 0;

		if(isSorted) {

			while(keys[i++].compareTo(key) < 0);

			// restore original value
			keys[greatestIndex] = tempKey;

			targetKey = (!key.equals(keys[i]) && i == greatestIndex) ? null : keys[i];

		}else {

			while(!key.equals(keys[i++]));

			// restore original value
			keys[greatestIndex] = tempKey;

			targetKey = (!key.equals(keys[i]) && i == greatestIndex) ? null : keys[i];
		}

		return targetKey;
	}

	/**
	 * grow the capacity of the array.
	 * 
	 * @param newCapacity	the new capacity.
	 */
	public void EnsureCapcity(int newCapacity) {
		K[] tempKeys = (K[]) new Comparable[newCapacity];

		for(int i = 0; i < N; i++) {
			tempKeys[i] = keys[i];
		}

		keys = tempKeys;

		tempKeys = null;
		return;
	}

	/**
	 * Check whether the tree is empty or not.
	 * 
	 * @return {@code true} if the tree does not contain anything, otherwise {@code false}.
	 */
	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * 
	 * @return the number of keys within the table.
	 */
	public int size() {
		return N;
	}
}

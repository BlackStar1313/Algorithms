package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.ISymbolTable;

public class SymbolTable<K extends Comparable<K>, V> implements ISymbolTable<K, V> {

	private static final int DEFAULT_SIZE = 10;
	private K[] keys;
	private V[] values;
	private int N;
	
	//fields for software caching.
	private K cacheKey;
	private int cacheIndex;

	public SymbolTable() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public SymbolTable(int initialCapacity) {

		if(initialCapacity > 0) {
			keys = (K[]) new Comparable[initialCapacity];
			values = (V[]) new Object[initialCapacity];
		}else {
			throw new IllegalArgumentException("Illegal Capacity: "+
					initialCapacity);
		}
	}

	@SuppressWarnings("unchecked")
	public void grow(int newCapacity) {
		K[] tempKeys = (K[]) new Comparable[newCapacity];
		V[] tempValues = (V[]) new Object[newCapacity];

		for(int i = 0; i < N; i++) {
			tempKeys[i] = keys[i];
			tempValues[i] = values[i];
		}

		keys = tempKeys;
		values = tempValues;

		tempKeys = null;
		tempValues = null;
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



	@Override
	public void put(K key, V value) {

		if(key == null) {
			throw new IllegalArgumentException("first argument to put() is null");
		}

		if(value == null) {
			delete(key);
			return;
		}

		int bestIndex = findBestIndexFromCache(key);
		
		if(cacheKey != null && key.compareTo(cacheKey) != 0) {
			cacheIndex = -1;
			cacheKey = null;
		}
		
		if(bestIndex < N && key.compareTo(keys[bestIndex])==0) {
			//if so then overwrite the old value.
			values[bestIndex] = value;
			return;
		}

		//Making sure there is enough space within the sorted arrays.
		if(N == keys.length) {
			grow(2 * keys.length);
		}

		//Shifting to the right keys and values
		for(int k = N; k > bestIndex; k--) {
			keys[k] = keys[k-1];
			values[k] = values[k-1];
		}

		//finally, inserting the new key with its value at the best position
		keys[bestIndex] = key;
		values[bestIndex] = value;
		N++;
	}
	
	/**
	 * Compute a the cached index.
	 * @param key
	 * @return
	 */
	private int findBestIndexFromCache(K key) {
		
		int bestIndex = -1;
		
		if(cacheKey != null && key.compareTo(cacheKey) == 0) {
			bestIndex = cacheIndex;
		}else {
			bestIndex = findBestIndex(key);
		}
		
		return bestIndex;
	}

	/**
	 * Find the best position by using binary search to insert the key into the already sorted array.
	 * 
	 * @param key	the key to fetch for.
	 * 
	 * @return either the position of the item if it exists in the list or the position it would fit into to keep the list sorted.
	 */
	private int findBestIndex(K key) {

		if(cacheKey != null && cacheKey.compareTo(key) == 0) {
			//Cache hit;
			return cacheIndex;
		}
		
		//Cache miss;
		
		int min = 0, max = N;

		while(min < max) {

			int mid = (min + max) / 2;

			int comp = key.compareTo(keys[mid]);

			if(comp < 0) {
				max = mid;
			}else if(comp > 0) {
				min = mid + 1;
			}else {
				//Key is already in the table.
				return mid;
			}
		}
		//the best index to insert a given key.
		return min;
	}
	

	/**
	 * 
	 * @return the number of keys within the table.
	 */
	public int size() {
		return N;
	}


	/**
	 * Check whether the key is within the table
	 * @param key	the key to look for.
	 * @return	{@code true} if the table contains the key, otherwise {@code false}.
	 */
	public boolean containsKey(K key) {
		V searchValue = get(key);
		return searchValue != null;
	}
	
	@Override
	public V get(K key) {

		if(isEmpty()) {
			return null;
		}
		
		int bestIndex = findBestIndexFromCache(key);

		if(bestIndex < N && keys[bestIndex].compareTo(key)==0) {
			if(cacheKey == null || cacheKey != key) {
				cacheKey = key;
				cacheIndex = bestIndex;
			}
			return values[bestIndex];
		}

		return null;
	}

	@Override
	public void delete(K key) {
		if(isEmpty()) {
			throw new NoSuchElementException("Cannot delete a key within an empty table!!");
		}

		//the position of the searched key.(Normally)
		int k = findBestIndexFromCache(key);

		//If the key is not in the table
		if(k == N && key.compareTo(keys[k]) != 0) {
			//then we don't go any further..
			return;
		}
		
		if(key.compareTo(cacheKey) <= 0) {
			cacheIndex = -1;
			cacheKey = null;
		}

		//Shifting to the left keys and values if necessary
		for(int n = k; n < N-1; n++) {
			keys[n] = keys[n+1];
			values[n] = values[n+1];
		}

		N--;
		//to help GC.
		keys[N] = null;
		values[N] = null;
		
		if(N > 1 && (N == keys.length/4)) {
			grow(keys.length/2);
		}
	}

	public void clearAll() {
		for(int i = 0; i < N; i++) {
			keys[i] = null;
			values[i] = null;
		}
		return;
	}

	@Override
	public Iterator<K> iterator() {
		return new MyIter();
	}

	@Override
	public String toString() {
		String str = "";

		for(int i = 0; i < N; i++) {
			str += "Key = " + keys[i] + ", Value = " + values[i] + "\n"; 
		}
		return str;
	}

	/**
	 * An version of AbstractList.Itr
	 * 
	 * @author Peguy Calusha
	 *
	 */
	private class MyIter implements Iterator<K>{

		private int nextKeyIndex;// index of next element to return
		private int lastAccessedIndex;// index of last element returned; -1 if no such

		private MyIter() {
			lastAccessedIndex = -1;
		}

		@Override
		public boolean hasNext() {
			return nextKeyIndex != N;
		}

		@Override
		public K next() {
			lastAccessedIndex = nextKeyIndex;
			//Check whether the next key does exist.
			if(lastAccessedIndex >= N) {
				throw new NoSuchElementException("Could not find the next key within the table!");
			}
			nextKeyIndex++;
			return keys[lastAccessedIndex];
		}
		
		@Override
		public void remove() {
			if(lastAccessedIndex < 0) {
				throw new IllegalStateException();
			}
			
			try {
				delete(keys[lastAccessedIndex]);
				nextKeyIndex = lastAccessedIndex;
				lastAccessedIndex = -1;
			}catch(IndexOutOfBoundsException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

}

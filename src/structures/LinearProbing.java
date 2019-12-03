package structures;

import java.util.Iterator;

/**
 *  One approach to implementing hashing is to store N key-value pairs in a hash table of size M > N, relying on empty entries in the table to help with collision resolution.</br>
 *  Such methods are called open-addressing hashing methods.
 *  
 * @author Njoyim Peguy
 *
 * @param <K>	key type
 * @param <V>	value type.
 */
public final class LinearProbing<K, V> extends HashTable<K, V> {

	/**
	 * Internal storage for keys.
	 */
	private K[] keys;

	/**
	 * Internal storage for values.
	 */
	private V[] values;


	public LinearProbing() {
		super();
	}

	public LinearProbing(int initialCapacity) {
		super(initialCapacity);
		keys = (K[]) new Object[M];
		values = (V[]) new Object[M];
	}

	public LinearProbing(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		keys = (K[]) new Object[M];
		values = (V[]) new Object[M];
	}

	@Override
	protected void resize(int newCapacity) {

		LinearProbing<K, V> temp = new LinearProbing<K, V>(newCapacity, loadFactor());

		for(int k = 0; k < M; k++) {
			if(keys[k] != null) {
				temp.put(keys[k], values[k]);
			}
		}

		keys = temp.keys;
		values = temp.values;
		M = temp.M;
		temp = null;
		return;
	}

	@Override
	public void put(K key, V value) {

		if(value == null) {
			delete(key);
		}

		// When the number of entries in the hash table exceeds the product of the load factor and the current capacity, 
		// the hash table is rehashed (i.e., we double the number of slots within the hash table).
		if(N == threshold()) {
			resize(2*M);
		}

		// When there is a collision (i.e., when we hash to a table index that is already occupied with a key different from the search key), 
		// then we just check the next entry in the table (by incrementing the index).
		int i = hash(key);
		while(keys[i] != null) {
			if(keys[i].equals(key)) {// rewriting if there are duplicates.
				values[i] = value;
				return;
			}
			i = (i + 1) % M;
		}

		// from here, we have found a non-occupied entry.
		keys[i] = key;
		values[i] = value;
		N++;
	}

	@Override
	public V get(K key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % M) 
			if (keys[i].equals(key))
				return values[i];
		return null;
	}

	@Override
	public void delete(K key) {

		// Base case.
		if(!contains(key)) return;

		// find position i of key
		int i = hash(key);
		while (!key.equals(keys[i])) {
			i = (i + 1) % M;
		}

		// delete key and associated value
		keys[i] = null;
		values[i] = null;

		// rehash all keys in same cluster
		i = (i + 1) % M;
		while (keys[i] != null) {
			// delete keys[i] an values[i] and reinsert
			K  keyToRehash = keys[i];
			V valueToRehash = values[i];
			keys[i] = null;
			values[i] = null;
			N--;// very important!!!
			put(keyToRehash, valueToRehash);
			i = (i + 1) % M;
		}
		N--;

		if(N > 0 && N <= threshold() / 2) {
			resize(M / 2);
		}
		return;
	}

	@Override
	public String toString() {
		String str =  "";
		for(int i = 0; i < M; i++) {
			if(keys[i] != null) {
				str += "Key = " + keys[i] + ", Value = " + values[i] + "\n"; 
			}
		}
		return str;
	}

	@Override
	public Iterator<K> iterator() {
		Queue<K> queue = new Queue<>();

		for(int i = 0; i < M; i++) {
			if(keys[i] != null) {
				queue.Enqueue(keys[i]);
			}
		}
		return queue;
	}

}

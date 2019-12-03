package structures;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A helper class for storing the key-value pair.
 * It is also an immutable entry.
 * 
 * @author Peguy Calusha
 *
 */
public final class Entry<K, V> implements Iterable<Entry<K, V>> {

	private K key;
	private V value;

	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public void clear() {
		this.key = null;
		this.value = null;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Key = " + key + ", Value = " + value + "\n"; 
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		ArrayList<Entry<K, V>> list = new ArrayList<>();
		return list.iterator();
	}
}

package interfaces;

public interface ISymbolTable<K extends Comparable<K>, V> extends Iterable<K> {

	/**
	 * Associate the specified value with the specified key in this table.
	 * If the table previously contained a mapping for the key, the old
     * value is replaced.
	 * @param key	the key for which the specified value is to be associated.
	 * @param value	the value to be associated with the specified key.
	 */
	void put(K key, V value);
	
	/**
	 * Returns the value corresponding to this entry.
	 * @param key	the value corresponding to this entry
	 * @return
	 */
	V get(K key);
	
	/**
	 * 
	 * @param key
	 */
	void delete(K key);
	
}

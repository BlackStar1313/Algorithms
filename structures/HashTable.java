package structures;

/**
 * 
 * @author Njoyim Peguy
 *
 * @param <K>
 * @param <V>
 */
public abstract class HashTable<K, V> implements Iterable<K> {

	/**
	 * The default number of slots in the hash table.
	 */
	protected static final int DEFAULT_CAPACITY = 1 << 4;// aka 2 to power 4 = 16

	/**
	 * The maximum capacity, used if a higher value is implicitly specified
	 * by either of the constructors with arguments.
	 */
	private static final int MAXIMUM_CAPACITY = 1 << 30;// aka 2 to power 30

	/**
	 * The "perfect" load factor used when none specified in constructor.
	 */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * The load factor used when storing key-value pairs.</br>
	 * A lower load factor means more free slots which implies less chance of collision when computing the hash function.
	 */
	private final float loadFactor;

	/**
	 * Number of slots in the hash table
	 */
	protected int M;

	/**
	 * Number of key-value pairs to be inserted in the hash table
	 */
	protected int N;

	/**
	 * Create a hash table with a default number of slots and a default load factor.
	 */
	public HashTable() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Create a hash table with a default load factor and an initial number of slots.
	 * @param initialCapacity	the number of slots.
	 */
	public HashTable(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		}

		if (initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}

		if(this instanceof LinearProbing) {
			loadFactor = DEFAULT_LOAD_FACTOR;
		}else {
			loadFactor = 1.0f;
		}

		M = initialCapacity;
	}

	public HashTable(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		}

		if (initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}

		if(loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		}

		M = initialCapacity;
		if(this instanceof LinearProbing) {
			this.loadFactor = loadFactor;
		}else {
			this.loadFactor = 1.0f;
		}
	}

	/**
	 * @return	the number of key-value pairs in this hash table.
	 */
	public int size() {
		return this.N;
	}

	/**
	 @return a threshold
	 */
	public int threshold() {
		return (int) (loadFactor * M);
	}

	/**
	 * @return the load factor.
	 */
	protected float loadFactor() {
		return loadFactor;
	}

	/**
	 * Check whether this hash table is empty or not.
	 * @return	true if this table is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * Check whether a given key does exist within the hash table
	 * @param key	the key to be searched for.
	 * @return		true if a given key exists, otherwise false.
	 */
	public boolean contains(K key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys.
	 * @param key	the key to be hashed
	 * @return		a value between 0 and M-1.
	 */
	protected int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	/**
	 * resize the hash table to the given capacity by re-hashing all of the keys
	 * @param newCapacity		the new capacity
	 */
	protected abstract void resize(int newCapacity);

	/**
	 * Insert the key-value pairs within the hash table.
	 * @param key		the key to be inserted.
	 * @param value		the value associated with its key.
	 */
	public abstract void put(K key, V value);

	/**
	 * Search the value associated with the given key.
	 * @param key	the key to fetched for its value.
	 * @return		the value associated with the given key. Null if there is no suck key in the hash table.
	 */
	public abstract V get(K key);

	/**
	 * delete key (and associated value) if key is in the hash table.
	 * 
	 * @param key	the key to be deleted associated with its value.
	 */
	public abstract void delete(K key);
}

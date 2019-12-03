package structures;

import java.util.Iterator;

public final class SeparateChaining<K extends Comparable<K>, V> extends HashTable<K, V> {

	private BinarySearchTree<K, V> bst[];

	public SeparateChaining() {
		super();
	}

	public SeparateChaining(int initialCapacity) {
		
		super(initialCapacity);
		
		bst = new BinarySearchTree[initialCapacity];

		for(int i = 0; i < M; i++) {
			bst[i] = new BinarySearchTree<>();
		}
	}

	@Override
	public Iterator<K> iterator() {
		Queue<K> queue = new Queue<>();
		return queue;
	}

	@Override
	protected void resize(int newCapacity) {
		SeparateChaining<K, V> temp = new SeparateChaining<>();

		for(int i = 0; i < M; i++) {
			for(Entry<K, V> entry : bst[i].LevelOrderArray()) {
				temp.put(entry.getKey(), entry.getValue());
			}
		}
		
		this.M  = temp.M;
        this.N  = temp.N;
        this.bst = temp.bst;
        temp = null;
        return;
	}

	@Override
	public void put(K key, V value) {
		if(value == null) {
			delete(key);
			return;
		}

		if(N >= threshold()) {
			resize(M * 2);
		}

		int i = hash(key);

		if(!bst[i].contains(key)) N++;

		bst[i].put(key, value);
		return;
	}

	@Override
	public V get(K key) {
		int i = hash(key);
		return bst[i].get(key);
	}

	@Override
	public void delete(K key) {
		int i = hash(key);
		if (bst[i].contains(key)) N--;
		bst[i].delete(key);

		// halve table size if average length of list <= 1
		if (M > DEFAULT_CAPACITY && N <= 2*M) resize(M/2);
	}
	
	public void clear() {
		bst = null;
	}
	
	public void display() {
		for(int i = 0; i < M; i++) {
			System.out.print(i);
			for(Entry<K, V> entry : bst[i].LevelOrderArray()) {
				System.out.println(" --> " + entry);
			}
			System.out.println();
		}
	}

}

package interfaces;

public interface ISortedSymbolTable<Key extends Comparable<Key>, Value> extends Iterable<Key> {

	/**
	 * Insert key-value pair into BST, if key already exists, update with its new value.
	 * @param key	the key to be inserted.
	 * @param val	the value to be inserted associated with its key.
	 */
    void put(Key key, Value val);

    /**
     * Get the value associated with the given key, or null if no such key exists.
     * @param key	the key to be searched for.
     * @return		value associated with its key.
     */
    Value get(Key key);

    /**
     * Delete a given key within the binary search tree if it does exist.
     * Otherwise it will throw a NoSuchElementException.
     * @param key	the key to be removed.
     */
    void delete(Key key);

    /**
     * Does there exist a key-value pair with given key?
     * @param key	the key to be tested for
     * @return	true if the given does exist, otherwise false.
     */
    boolean contains(Key key);

    /**
     * Is the binary search tree empty?
     * @return	true if the BST is empty, otherwise false.
     */
    boolean isEmpty();
    
    /**
     * Check if every node on the right subtree is larger than the current node and
     * every node on the left subtree is smaller than the current node.
     * 
     * @return	true if the tree is BST, otherwise false.
     */
    boolean isBST();

    /**
     * Get the number of key-value pairs in BST.
     * 
     * @return the number of key-value pairs in BST.
     */
    int size();
    
    /**
     * Get the height in a binary tree
     * 
     * @return the height of a given Binary tree.
     */
    int height();

    /**
     * Get the minimum element in a BST.
     * 
     * @return the minimum element in a BST.
     */
    Key min();
    
    /**
     * Get the maximum element in a BST.
     * 
     * @return the maximum element in a BST.
     */
    Key max();
    
    /**
     * Get the greatest key less than or equal to a given key.
     * 
     * @param key	a given key.
     * @return		the greatest key less than or equal to a given key.
     */
    Key floor(Key key);
    
    /**
     * Get the least key greater than or equal to a given key.
     * @param key	a given key.
     * @return		the least key greater than or equal to a given key.
     */
    Key ceiling(Key key);
    
    /**
     * Get the number of keys in the subtree less than a given key.
     * @param key	a given key.
     * @return		the number of keys in the subtree less than a given key.
     */
    int rank(Key key);
    
    /**
     * Delete the smallest key-value pairs in BST.
     */
    void deleteMin();
    
    /**
     * Delete the greatest key-value pairs in BST.
     */
    void deleteMax();
}

package structures;

public class BSTNode<Key, Value> extends AbstractNode<Key, Value> {

	/**
	 * Constructor that creates an empty node.
	 */
	protected BSTNode(Key key, Value value) {
		super(key, value);
	}

	/**
	 * Constructor that creates a node with its given fields.
	 * 
	 * @param key		the key of the node.
	 * @param value		the value associated with its key.
	 * @param parent	the reference to the current's parent node.
	 * @param left		the reference to the current's left node.
	 * @param right		the reference to the current's right node.
	 */
	protected BSTNode(Key key, Value value, AbstractNode<Key, Value> parent,
			AbstractNode<Key, Value> left, AbstractNode<Key, Value> right) {
		super(key, value, parent, left, right);
	}

	/**
	 * A static factory method to create any nodes with its all fields set to null.
	 * 
	 * @return	a new node with its respective fields(i.e., all fields set to null).
	 */
	public static <Key, Value> BSTNode<Key, Value> create(Key key, Value value){
		BSTNode<Key, Value> newNode = new BSTNode<>(key, value);
		return newNode;
	}

	/**
	 * A static factory method to create any nodes with its given fields.
	 * @param key		the key of the node.
	 * @param value		the value associated with its key.
	 * @param parent	the reference to the current's parent node.
	 * @param left		the reference to the current's left node.
	 * @param right		the reference to the current's right node.
	 * @return a new node with its respective fields.
	 */
	public static <Key, Value> BSTNode<Key, Value> create(Key key, Value value, AbstractNode<Key, Value> parent,
			AbstractNode<Key, Value> left, AbstractNode<Key, Value> right){

		BSTNode<Key, Value> newNode = new BSTNode<Key, Value>(key, value, parent, left, right);
		return newNode;
	}

	@Override
	protected AbstractNode<Key, Value> deepCopy() {
		BSTNode<Key, Value> newNode = new BSTNode<Key, Value>(this.key, this.value, this.parent, this.left, this.right);
		return newNode;
	}
}

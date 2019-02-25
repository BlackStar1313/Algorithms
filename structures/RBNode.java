package structures;

import enums.Color;

public final class RBNode<Key, Value> extends AbstractNode<Key, Value> {
	
	/**
	 * A constructor that creates a red-black node with its given fields.
	 * @param key		the key of the node.
	 * @param value		the value associated with its key.
	 * @param parent	the reference to the current's parent node.
	 * @param left		the reference to the current's left node.
	 * @param right		the reference to the current's right node.
	 * @param color		the color associated with this node.
	 */
	private RBNode(Key key, Value value, AbstractNode<Key, Value> parent,
			AbstractNode<Key, Value> left, AbstractNode<Key, Value> right, Color color){
		super(key, value, parent, left, right);
		this.color = color;
	}
	
	private RBNode(Key key, Value value) {
		super(key, value);
	}
	
	/**
	 * A static factory method to create any nodes with its all fields set to null.
	 * 
	 * @return	a new node with its respective fields(i.e., all fields set to null).
	 */
	public static <Key, Value> RBNode<Key, Value> create(Key key, Value value){
		RBNode<Key, Value> newNode = new RBNode<>(key, value);
		return newNode;
	}
	
	
	
	/**
	 * A static factory method to create any nodes with its given fields.
	  * @param key		the key of the node.
	 * @param value		the value associated with its key.
	 * @param parent	the reference to the current's parent node.
	 * @param left		the reference to the current's left node.
	 * @param right		the reference to the current's right node.
	 * @param color		the color associated with this node.
	 * @return			a new node with its respective fields.
	 */
	public static <Key, Value> RBNode<Key, Value> build(Key key, Value value, AbstractNode<Key, Value> parent, 
			AbstractNode<Key, Value> left, AbstractNode<Key, Value> right, Color color){
		RBNode<Key, Value> newNode = new RBNode<Key, Value>(key, value, parent, left, right, color);
		return newNode;
	}


	@Override
	public String toString() {
		return super.toString() + ", Color = " + this.color;
	}

	@Override
	protected AbstractNode<Key, Value> deepCopy() {
		RBNode<Key, Value> newNode = new RBNode<>(this.key, this.value, this.parent, this.left, this.right, this.color);
		return newNode;
	}
}

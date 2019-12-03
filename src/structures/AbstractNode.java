package structures;

import java.util.ArrayList;
import java.util.Iterator;

import enums.Color;

public abstract class AbstractNode<Key, Value> implements Iterable<AbstractNode<Key, Value>> {


	/**
	 * A reference to the left node
	 */
	AbstractNode<Key, Value> left;
	
	/**
	 * A reference to the right node
	 */
	AbstractNode<Key, Value> right;
	
	/**
	 * A reference to the parent node
	 */
	AbstractNode<Key, Value> parent;

	/**
	 * The key to insert into a BST.
	 */
	Key key;
	
	/**
	 * The value associated with its key.
	 */
	Value value;
	
	/**
	 * The extra bit color used only for the Red-Black Tree.
	 */
	Color color;
	
	
	protected AbstractNode(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	protected AbstractNode(Key key, Value value, AbstractNode<Key, Value> parent, 
			AbstractNode<Key, Value> left, AbstractNode<Key, Value> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	
	/**
	 * Returns a defensive copy of the field.
	 * The caller of this method can do anything they want with the
	 * returned Node object, without affecting the internals of this
	 * class in any way...
	 * 
	 * @return a defensive copy
	 */
	protected abstract AbstractNode<Key, Value> deepCopy();
	
	@Override
	public Iterator<AbstractNode<Key, Value>> iterator() {
		ArrayList<AbstractNode<Key, Value>> list = new ArrayList<>();
		return list.iterator();
	}
	
	/**
	 * Check whether this node is painted red or black.
	 * @return	true if this node is red, otherwise false.
	 */
	public boolean isRed() {
		return this.color == Color.RED;
	}
	
	@Override
	public String toString() {
		String str = "";

		if(this.key != null) {
			str += ("Key = " + key + ", ");
		}

		if(this.value != null) {
			str += ("Value = " + value);
		}

		return str;
	}
}

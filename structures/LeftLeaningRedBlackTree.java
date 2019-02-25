package structures;

import java.util.NoSuchElementException;

import enums.Color;

/**
 * A left-leaning red–black (LLRB) tree is a type of self-balancing binary search tree. <br/>
 * It is a variant of the red–black tree and guarantees the same asymptotic complexity for operations, 
 * but is designed to be easier to implement. 
 * 
 * @author Peguy Calusha
 *
 */
public class LeftLeaningRedBlackTree<Key extends Comparable<Key>, Value> extends BinarySearchTree<Key, Value> {

	public LeftLeaningRedBlackTree() {
		super();
		end.color = Color.BLACK;
	}

	@Override
	public void put(Key key, Value value) {

		if(key == null) {
			throw new IllegalArgumentException("The first argument from put(key, value) is null");
		}

		if(value == null) {
			delete(key);
			return;
		}

		/*************************************************************************
		 *  1) Inserting nodes in the usual way
		 *************************************************************************/

		//Each node is either red or black.
		AbstractNode<Key, Value> newNode = RBNode.build(key, value, end, end, end, Color.RED);
		if(root == end) {
			root =  newNode;
			root.color = Color.BLACK;//root is black at all times.
		}else {

			AbstractNode<Key, Value> parent = end;
			AbstractNode<Key, Value> current = root;

			while(current != end) {
				//Hold the address of the current parent node
				parent = current;
				int cmp = key.compareTo(current.key);
				//Go left
				if(cmp < 0) {
					current = current.left;
					//Go right
				}else if(cmp > 0) {
					current = current.right;
				}else {
					//Once we are here, then it means we have a duplicated key entry.
					//So we just overwrite its old value.
					current.value = value;
					return;
				}
			}

			//Now the parent of this new node will reference the previous parent node.
			newNode.parent = parent;

			//Wrapping link correctly
			if(key.compareTo(parent.key) < 0) {
				parent.left = newNode;
			}else {
				parent.right = newNode;
			}

			/*************************************************************************
			 *  2) Making sure the insertion does not violate the Red-Black tree property
			 *************************************************************************/
			fixUpRightLeanLinks(newNode);
		}
	}

	@Override
	public void delete(Key key) {

		AbstractNode<Key, Value> nodeTobeRemoved = get(root, key);

		// Checking if such key does exist...
		if(nodeTobeRemoved == end) {
			throw new NoSuchElementException("This key " + key + " does not exist within the red-black tree!!");
		}
	}

	/**
	 * Fix up the violation of the left leaning Red-Black tree properties that may have been caused during the insertion of a given node.<br/>
	 * Those properties are : <br/>
	 * 1. No node has two red links connected to it<br/>
	 * 2. Every path from a given node to any of its descendant NIL nodes contains the same number of black nodes.<br/>
	 * 3. Red links lean left.<br/>
	 * 4. The root is always black.<br/>
	 * @param newNode	A given node that may have caused the violation of the Red-Black tree properties.
	 */
	private void fixUpRightLeanLinks(AbstractNode<Key, Value> newNode) {

		// As long as there is a single violation of the LLRB tree properties...
		while((newNode.parent != end) && (newNode.parent.color == Color.RED)) {

			AbstractNode<Key, Value> P = newNode.parent;// A reference to newNode's parent.
			AbstractNode<Key, Value> G = newNode.parent.parent;// A reference to newNode's grandparent.

			if((newNode == P.right) && (P.right.isRed() && !P.left.isRed())) {
				rotateLeft(P);
				newNode = P;
			}else if((newNode == G.left.left) && (G.left.isRed() && G.left.left.isRed())){
				// Case 2 : the newNode is the left child of the left child of the grandparent G.
				// In this case, re-color and rotate right around the newNode's grandparent G.
				rotateRight(G);
			}else if((P != end) && (P.isRed() && P.isRed())) {
				// then both of them can be repainted black.
				flipColors(P);
			}	
		}

		// Color root black at all times.
		root.color = Color.BLACK;
		return;
	}

	/**
	 * Perform the left rotate on x's parent node, where x is a given node
	 * @param P		the parent node of a given node x.
	 */
	private void rotateLeft(AbstractNode<Key, Value> P){

		// making sure that the right child is red and if it is not the end node.
		assert ((P != end) && P.right.isRed());

		AbstractNode<Key, Value> x = P.right;

		//turning the x's left sub-tree into the P's right sub-tree.
		P.right = x.left;
		if(x.left != end) {
			x.left.parent = P;
		}

		// make x's parent to be the former P's parent
		x.parent = P.parent;

		// Set the former P's parent left referencing x.
		// First check whether it is the root.
		if(P.parent == end) {
			root = x;
		}else {

			if(P == (P.parent.left)) {
				P.parent.left = x;
			}else {
				P.parent.right = x;
			}
		}

		// Finally put P on x's left child and make a right-leaning link lean to x's left
		x.left = P;
		P.parent = x;
		x.color = x.left.color;
		x.left.color = Color.RED;
		return;
	}

	/**
	 * Perform the right rotate on x's grandparent node, where x is a given node
	 * @param G		the parent node of a given node x.
	 */
	private void rotateRight(AbstractNode<Key, Value> G) {

		// making sure that the right child is red and if it is not the end node.
		assert ((G != end) && G.left.isRed());

		AbstractNode<Key, Value> x = G.left;

		//turning the x's right sub-tree into the P's left sub-tree.
		G.left = x.right;
		if(x.right != end) {
			x.right.parent = G;
		}

		// make x's parent to be the former P's parent.
		x.parent = G.parent;

		// Set the former P's parent left referencing x.
		// First check whether it is the root.
		if(G.parent == end) {
			root = x;
		}else {

			if(G == (G.parent.right)) {
				G.parent.right = x;
			}else {
				G.parent.left = x;
			}
		}

		// Finally put P on x's right child and make a left-leaning link lean to x's right.
		x.right = G;
		G.parent = x;
		x.color = x.right.color;
		x.right.color = Color.RED;
		return;
	}

	private void flipColors(AbstractNode<Key, Value> node) {

	}
}

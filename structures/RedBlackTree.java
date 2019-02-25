package structures;

import java.util.NoSuchElementException;

import enums.Color;


public class RedBlackTree<Key extends Comparable<Key>, Value> extends BinarySearchTree<Key, Value> {

	public RedBlackTree() {
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
			fixInsertViolation(newNode);
		}
		return;
	}


	@Override
	public void delete(Key key) {

		AbstractNode<Key, Value> nodeTobeRemoved = get(root, key);

		// Checking if such key does exist...
		if(nodeTobeRemoved == end) {
			throw new NoSuchElementException("This key " + key + " does not exist within the red-black tree!!");
		}

		AbstractNode<Key, Value> currentParentNode = nodeTobeRemoved.parent;

		/**
		 * Unlike to BSTs, there are only two cases to delete a given node in a Red-Black tree.
		 * 1) The node to be deleted has at most one non-leaf child.
		 * 2) The node to be deleted has two non-leaf child.
		 */

		// case 1: the current node to be removed has exactly one child.
		if(nodeTobeRemoved.left == end || nodeTobeRemoved.right == end) {
			AbstractNode<Key, Value> childNode = end;

			// Check whether the left child node does exist.
			// It it exists, then hold its address.
			if(nodeTobeRemoved.left != end) {
				childNode = nodeTobeRemoved.left;
			}else{
				// Otherwise hold the right child node address.
				childNode = nodeTobeRemoved.right;
			}

			if(nodeTobeRemoved.color == Color.BLACK) {
				fixDeleteViolation(nodeTobeRemoved);
			}

			// The node to be removed is root itself.
			if(nodeTobeRemoved == root) {
				root.key = null;
				root.value = null;
				root.color = null;
				root = childNode;
			}else {

				// Referencing correctly the parent child node.
				childNode.parent = currentParentNode;

				// Referencing correctly the current's parent node. 
				if(currentParentNode.left == nodeTobeRemoved) {
					currentParentNode.left = childNode;
				}else{
					currentParentNode.right = childNode;
				}

				//Un-referencing process...
				nodeTobeRemoved.key = null;
				nodeTobeRemoved.value = null;
				nodeTobeRemoved.color = null;
			}

			// case 2: the current node to be removed has exactly two child.
		}else {

			// 1) Find the minimum element/node in the right subtree of the node to be removed.(i.e., in-order successor)
			AbstractNode<Key, Value> temp = super.findMin(nodeTobeRemoved.right);

			// 2) Copying data and not the node!
			nodeTobeRemoved.key = temp.key;
			nodeTobeRemoved.value = temp.value;

			if(temp.color == Color.BLACK) {
				fixDeleteViolation(temp);
			}

			// Holds the address of the successor's parent.
			AbstractNode<Key, Value> parentTempNode = temp.parent;

			if(parentTempNode.left == temp) {
				parentTempNode.left = end;
			}else {
				parentTempNode.right = end;
			}

			//Un-referencing process...
			temp.key = null;
			temp.value = null;
			temp.color = null;
			return;
		}
	}

	@Override
	public void deleteMax() {
		Key key = max();
		delete(key);
		return;
	}

	@Override
	public void deleteMin() {
		Key key = min();
		delete(key);
		return;
	}


	/*************************************************************************
	 *  red-black tree helper functions
	 *************************************************************************/

	/**
	 * Perform the left rotate of a given node
	 * @param current	a given node.
	 */
	private void leftRotate(AbstractNode<Key, Value> current){

		AbstractNode<Key, Value> x = current.right;

		//turn the x's left sub-tree into the current's right sub-tree.
		current.right = x.left;
		if(x.left != end) {
			x.left.parent = current;
		}

		// make x's parent to be the former P's parent
		x.parent = current.parent;

		// Set the parent that references x instead of current.
		// First check whether it is the root.
		if(current.parent == end) {
			root = x;
		}else {

			// Check whether the current node was on the left of its parent.
			if(current == (current.parent.left)) {
				current.parent.left = x;
			}else {
				// Otherwise it must have been on the right.
				current.parent.right = x;
			}
		}

		// Finally put current on x's left.
		x.left = current;
		current.parent = x;
		return;
	}


	/**
	 * Perform the right rotate of a given node.
	 * 
	 * @param current	a given node.
	 */
	private void rightRotate(AbstractNode<Key, Value> current) {

		AbstractNode<Key, Value> x = current.left;

		//turn the x's right sub-tree into the current's left sub-tree.
		current.left = x.right;
		if(x.right != end) {
			x.right.parent = current;
		}

		// x's new parent was current's parent
		x.parent = current.parent;

		// Set the parent that references x instead of current.
		// First check whether it is the root.
		if(current.parent == end) {
			root = x;
		}else {

			// Check whether the current node was on the right of its parent.
			if(current == (current.parent.right)) {
				current.parent.right = x;
			}else {
				current.parent.left = x;
			}
		}

		// Finally put current on x's right.
		x.right = current;
		current.parent = x;
		return;
	}


	/**
	 * Fix up the violation of the Red-Black tree properties that may have been caused during the insertion of a given node.<br/>
	 * Those properties are : <br/>
	 * 1. Each node is either red or black.<br/>
	 * 2. The root is black.<br/>
	 * 3. If a node is red, then both its children are black.<br/>
	 * 4. Every path from a given node to any of its descendant NIL nodes contains the same number of black nodes.<br/>
	 * @param x	A given node that may have caused the violation of the Red-Black tree properties.
	 */
	private void fixInsertViolation(AbstractNode<Key, Value> x) {

		// As long as there is a single violation of the Red-Black tree properties...
		while((x.parent != end) && (x.parent.parent != end) && (x.parent.color == Color.RED)) {

			AbstractNode<Key, Value> P = x.parent;// A reference to x's parent.
			AbstractNode<Key, Value> G = x.parent.parent;// A reference to x's grandparent.
			AbstractNode<Key, Value> U = end;// A reference to x's uncle.

			// If x's parent is the left child of its parent.
			if(P == G.left) {

				//then u is x's right 'uncle' U.
				U = G.right;

				// Case 1 : If both the parent P and the uncle U are red,
				if((U != end) && (U.color == Color.RED) && (P.color == Color.RED)) {
					// then both of them can be repainted black
					// and the grandparent G becomes red to maintain property 4. (i.e., we flip colors from G)
					flipColors(G);

					//Move x up the tree.
					x = G;

					// Case 2 :The parent P is red but Uncle U is black 
					// step 1 : x is the right child of the left child of the grandparent G.
				}else if(x == P.right) {
					// In this case, a left rotation on P that switches the roles of the current node x
					// and its parent P can be performed.
					x = P;
					leftRotate(x);

					// step 2 : x is the left child of the right child of the grandparent G.
				}else {
					// In this case, re-color and rotate right around the x's grandparent G.
					P.color = Color.BLACK;
					G.color = Color.RED;
					rightRotate(G);
				}

				// x's parent is the right child of its parent.
			}else {

				// U is x's left 'uncle'.
				U = G.left;

				// Case 1 : If both the parent P and the uncle U are red,
				if ((U != end) && (U.color == Color.RED)) {
					// then both of them can be repainted black
					// and the grandparent G becomes red to maintain property 4. (i.e., we flip colors from G)
					flipColors(G);

					//Move x up the tree.
					x = G;

					// Case 2 :The parent P is red but Uncle U is black.
					// step 1 : x is the left child of the right child of the grandparent G.
				}else if(x == P.left) {
					
					// In this case, a right rotation on P that switches the roles of the current node x
					// and its parent P can be performed.
					x = P;
					rightRotate(x);
				}else {
					// In this case, re-color and rotate left around the x's grandparent G.
					P.color = Color.BLACK;
					G.color = Color.RED;
					leftRotate(G);
				}
			}
		}
		// Color root black at all times.
		root.color = Color.BLACK;
		return;
	}


	/**
	 * Fix up the violation of the Red-Black tree properties 
	 * that may have been caused during the removal of a given node.<br/>
	 * @param x		A given node that may have caused the violation of the Red-Black tree properties.
	 */
	private void fixDeleteViolation(AbstractNode<Key, Value> x) {

		// As long as we have not fixed the tree completely..
		while(x != root && x.color == Color.BLACK) {

			AbstractNode<Key, Value> P = x.parent;
			AbstractNode<Key, Value> S = end;

			// If x is its parent's left child
			if(x == P.left) {

				// Set S to x's sibling.
				S = P.right;

				// Case 1 : If sibling S is red
				if(S.color == Color.RED) {
					//Re-color the old sibling S's and its parent P colors
					S.color = Color.BLACK;
					P.color = Color.RED;
					// As S is right child of its parent
					// then we left rotate the parent P
					leftRotate(P);
					S = P.right;
				}

				// Case 2 : If sibling S is black and its both children are black.
				if((S.left.color == Color.BLACK) && (S.right.color == Color.BLACK)) {
					//perform re-coloring...
					S.color = Color.RED;
					x = P;
				}else {

					// Case 3 : If sibling S is black
					// and at least of its children is black
					if(S.right.color == Color.BLACK) {
						S.left.color = Color.BLACK;
						S.color = Color.RED;
						rightRotate(S);
						S = P.right;
					}

					// Case 4 : If sibling S is black
					// and at least of its children is red.
					if(S.right.color == Color.RED) {
						S.color = P.color;
						P.color = Color.BLACK;
						S.right.color = Color.BLACK;
						leftRotate(P);
						x = root;
					}

				}
				// otherwise x is its parent's right child.
			}else {

				// Set S to x's sibling.
				S = P.left;

				// Case 1 : If sibling S is red
				if(S.color == Color.RED) {
					//Re-color the old sibling S's and its parent P colors
					S.color = Color.BLACK;
					P.color = Color.RED;
					// As S is right child of its parent
					// then we left rotate the parent P
					rightRotate(P);
					S = P.left;
				}

				// Case 2 : If sibling S is black and its both children are black.
				if((S.right.color == Color.BLACK) && (S.left.color == Color.BLACK)) {
					//perform re-coloring...
					S.color = Color.RED;
					x = P;
				}else {
					// Case 3 : If sibling S is black
					// and at least of its children is black
					if(S.left.color == Color.BLACK) {
						S.right.color = Color.BLACK;
						S.color = Color.RED;
						leftRotate(S);
						S = P.left;
					}

					// Case 4 : If sibling S is black
					// and at least of its children is red.
					if(S.left.color == Color.RED) {
						S.color = P.color;
						P.color = Color.BLACK;
						S.left.color = Color.BLACK;
						rightRotate(P);
						x = root;
					}
				}
			}
		}

		// Set x to black color to ensure
		// there is no violation of Red-Black tree properties.
		x.color = Color.BLACK;
	}

	/**
	 * Flip the colors of a given node and its two children.
	 * 
	 * @param node	a given node.
	 */
	private void flipColors(AbstractNode<Key, Value> node) {

		if((node == end) && (node.left == end) && (node.right == end)) {
			throw new NoSuchElementException("cannot flip the node and its two children");

			// Testing if the given node has an opposite color of its two children.
		}else if((!node.isRed() && node.left.isRed() && node.right.isRed())
				|| ((node.isRed() && !node.left.isRed() && !node.right.isRed()))) {

			node.color = (node.color == Color.BLACK) ? Color.RED : Color.BLACK;
			node.left.color = (node.left.color == Color.BLACK) ? Color.RED : Color.BLACK;
			node.right.color = (node.right.color == Color.BLACK) ? Color.RED : Color.BLACK;
		}
	}

	/**
	 * Put all nodes from the Red-Black Tree in array in in-order traversal.
	 * 
	 * @return		An array of nodes from the Red-Black Tree.
	 */
	public AbstractNode<Key, Value>[] toArrayNodes(){
		if(isEmpty()) {
			throw new NoSuchElementException("The tree is empty!!");
		}

		int size = size();
		AbstractNode<Key, Value> nodes[] = new AbstractNode[size];

		int index = 0;
		AbstractNode<Key, Value> current = root;
		boolean leftdone = false;

		while(current != end && index < size) {
			//Check whether the leftmost node was not visited yet.
			if(!leftdone) {
				while(current.left != end) {
					current = current.left;
				}
			}

			//getting the current node key-value pairs.
			RBNode<Key, Value> entry = RBNode.build(current.key, current.value, current.parent, current.left, current.right, current.color);
			nodes[index++] = entry;
			entry = null;

			//Now we are sure that the leftmost node was visited.
			leftdone = true;

			//Check whether the right node is does exist.
			if(current.right != end) {
				current = current.right;
				//If we have reached a right node
				//Then we have to check if its left child nodes do exist.
				leftdone = false;

				//If the right child node does not exist
				//then go back to its respective parent node.
			}else if(current.parent != end) {

				// If the current node is right child of its parent, 
				// then visit parent's parent node first.
				while((current.parent != end) && (current.parent.right == current)) {
					current = current.parent;
				}

				current = current.parent;
			}
		}
		return nodes;
	}

}

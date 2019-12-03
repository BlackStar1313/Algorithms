package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import interfaces.ISortedSymbolTable;


public class BinarySearchTree<Key extends Comparable<Key>, Value> implements ISortedSymbolTable<Key, Value> {


	/**
	 * A reference to the sentinel node
	 */
	protected AbstractNode<Key, Value> end;

	/**
	 * A reference to the root node which references to the sentinel node 'end' at first.
	 */
	public AbstractNode<Key, Value> root;

	public BinarySearchTree() {
		end = BSTNode.create(null, null);
		end.left = end;
		end.right = end;
		end.parent = end;
		root = end;
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

		BSTNode<Key, Value> newNode = BSTNode.create(key, value, end, end, end);

		//If the tree is empty, i.e., if there is no root then this node will become the root.
		if(root == end) {
			root = newNode;
			return;
		}

		//Otherwise build the BST from the root's node.
		AbstractNode<Key, Value> parent = end;
		AbstractNode<Key, Value> current = root;

		while(current != end) {
			//Hold the address of the current parent node
			parent = current;
			//Go left
			if(key.compareTo(current.key) < 0) {
				current = current.left;
				//Go right
			}else if(key.compareTo(current.key) > 0) {
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
		}else if(key.compareTo(parent.key) > 0) {
			parent.right = newNode;
		}
		return;
	}


	@Override
	public Value get(Key key) {
		AbstractNode<Key, Value> node = get(root, key);
		if(node != end) {
			return node.value;
		}
		return null;
	}

	protected AbstractNode<Key, Value> get(AbstractNode<Key, Value> root, Key key) {
		//no need to search if the wanted key is null..
		if(key == null) {
			return end;
		}

		AbstractNode<Key, Value> current = root;
		end.key = key;//A small trick of programming. The sentinel node contains the key to be looked for.

		//Now as long as we did not find the node containing the key to be searched for.
		while(!current.key.equals(key)) {
			int cmp = key.compareTo(current.key);

			if(cmp < 0) {
				current = current.left;
			}else if(cmp > 0) {
				current = current.right;
			}
		}

		//If we have found the node containing the key, i.e., if we did not reach the sentinel node
		if(current != end) {

			//resetting the key within the sentinel node
			end.key = null;

			//and returning the node containing the wanted key.
			return current;
		}

		//otherwise it means the tree did not contain the key
		//which means the variable 'current' equals to the sentinel node 'end'
		end.key = null;
		return end;
	}

	/**
	 * Create a distinct copy of a given binary search tree by using the pre-order traversal.
	 * The algorithm uses the {@code Stack} structure which is a LIFO data structure.
	 * Since we want to visit the tree in order of node-left-right, we {@link Stack#Push Push} right node first and left node afterward,
	 * so that in the next iteration when we {@link Stack#pop() Pop} from Stack we get the left sub-tree.
	 * 
	 * @return A deep copy of this BST.
	 */
	public BinarySearchTree<Key, Value> deepCopy(){

		BinarySearchTree<Key, Value> originalBST = this;
		BinarySearchTree<Key, Value> copy = new BinarySearchTree<>();

		Stack<AbstractNode<Key, Value>> stack = new Stack<>();

		if(originalBST.root == end) {
			throw new NoSuchElementException("The tree to copy of is empty!!");
		}else {

			stack.push(originalBST.root);

			while(!stack.isEmpty()) {

				AbstractNode<Key, Value> current = stack.pop();
				copy.put(current.key, current.value);

				//pushing first the right child node if exists!
				if(current.right != end) {
					stack.push(current.right);
				}

				//and then the left child node if exists!
				if(current.left != end) {
					stack.push(current.left);
				}
			}
			return copy;
		}
	}

	@Override
	public void delete(Key key) {

		AbstractNode<Key, Value> nodeTobeRemoved = get(root, key);

		// Checking if such key does exist...
		if(nodeTobeRemoved == end) {
			throw new NoSuchElementException("This key " + key + " does not exist within the binary search tree!!");
		}

		AbstractNode<Key, Value> currentParentNode = nodeTobeRemoved.parent;

		// case 1: no children
		if(nodeTobeRemoved.left == end && nodeTobeRemoved.right == end) {

			// The node to be deleted is root itself.
			if(nodeTobeRemoved == root) {
				root.key = null;
				root.value = null;
				root = null;
				end = null;
			}else {

				// Otherwise the node is a child node to be deleted
				if(currentParentNode.left == nodeTobeRemoved) {
					currentParentNode.left = end;
				}else {
					currentParentNode.right = end;
				}

				//Un-referencing process...
				nodeTobeRemoved.key = null;
				nodeTobeRemoved.value = null;
				nodeTobeRemoved = null;
			}

			// case 2: the current node to be removed has exactly one child.
		}else if(nodeTobeRemoved.left == end || nodeTobeRemoved.right == end) {

			AbstractNode<Key, Value> childNode = end;

			// Check whether the left child node does exist.
			// It it exists, then hold its address.
			if(nodeTobeRemoved.left != end) {
				childNode = nodeTobeRemoved.left;
			}else{
				// Otherwise hold the right child node address.
				childNode = nodeTobeRemoved.right;
			}

			// The node to be removed is root itself.
			if(nodeTobeRemoved == root) {
				root.key = null;
				root.value = null;
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
				nodeTobeRemoved = null;
			}

			// case 4: The node to be deleted has two children.
		}else {

			// 1) Find the minimum element/node in the right subtree of the node to be removed.(i.e., in-order successor)
			AbstractNode<Key, Value> successor = findMin(nodeTobeRemoved.right);

			// 2) Copying data and not the node!
			nodeTobeRemoved.key = successor.key;
			nodeTobeRemoved.value = successor.value;

			// Holds the address of the successor parent's node.
			AbstractNode<Key, Value> parentSucessorNode = successor.parent;

			if(parentSucessorNode.left == successor) {
				// Since successor is always left child of its parent 
		        // we can safely make successor's right 
		        // right child as left of its parent. 
				parentSucessorNode.left = successor.right;
			}
			
			//Un-referencing process...
			successor.key = null;
			successor.value = null;
			successor = null;
		}
		return;
	}

	/**
	 * Clearing the BST.
	 */
	public void clear() {
		this.root = null;
		this.end = null;
		return;
	}


	@Override
	public boolean contains(Key key) {
		return get(key) != null;
	}

	@Override
	public boolean isBST() {

		//Create and enqueue the root of the BST.
		Queue<AbstractNode<Key, Value>> queue = new Queue<>();
		queue.Enqueue(this.root);

		while(!queue.IsEmpty()) {

			AbstractNode<Key, Value> currentParent = queue.Dequeue();

			if(currentParent != null) {

				if(currentParent.left != end) {
					// If the node is the left child of its parent, 
					// then it must be smaller than (or equal to) the parent 
					int parentLeftCmp = currentParent.left.key.compareTo(currentParent.key);
					if(parentLeftCmp > 0) {
						return false;
					}else {
						// and it must pass down the value from its parent to its right subtree
						// to make sure none of the nodes in that subtree is greater than the parent
						AbstractNode<Key, Value> current = currentParent.left;

						while(current.right != end) {
							current = current.right;
							int rightCmp = current.key.compareTo(currentParent.key);
							if(rightCmp > 0) {
								return false;
							}
						}
					}
				}

				if(currentParent.right != end) {
					// If the node is the right child of its parent,
					// then it must be larger than the parent
					int parentRightCmp = currentParent.right.key.compareTo(currentParent.key);
					if(parentRightCmp < 0) {
						return false;
					}else {

						// and it must pass down the value from its parent to its left subtree
						// to make sure none of the nodes in that subtree is lesser than the parent.
						AbstractNode<Key, Value> current = currentParent.right;

						while(current.left != end) {
							current = current.left;
							int leftCmp = current.key.compareTo(currentParent.key);
							if(leftCmp < 0) {
								return false;
							}
						}
					}
				}


				// taking the next node if exist..
				if(currentParent.left != end) {
					queue.Enqueue(currentParent.left);
				}

				if(currentParent.right != end) {
					queue.Enqueue(currentParent.right);
				}
			}
		}
		return true;
	}

	/**
	 * Get the farthest left node in a BST (i.e., get the node with the smallest key).
	 * @param root	the started node.
	 * @return	the farthest left node in a BST.
	 */
	protected AbstractNode<Key, Value> findMin(AbstractNode<Key, Value> root){
		AbstractNode<Key, Value> current = root;

		//As long as the next left node of this current's node is not the sentinel node
		while(current.left != end) {
			//keep going on the left.
			current = current.left;
		}
		return current;
	}

	/**
	 * Get the farthest right node in a BST (i.e., get the node with greatest key).
	 * @param root	the started node.
	 * @return		the farthest right node in a BST.
	 */
	protected AbstractNode<Key, Value> findMax(AbstractNode<Key, Value> root){

		AbstractNode<Key, Value> current = root;

		//As long as the next right node of this current's node is not the sentinel node.
		while(current.right != end) {
			//keep going on the right.
			current = current.right;
		}
		return current;
	}

	@Override
	public boolean isEmpty() {
		return root == end;
	}

	@Override
	public int size() {
		return size(this.root);
	}

	@Override
	public int height() {

		// Base case
		if(this.isEmpty()) {
			return 0;
		}

		//Create and enqueue the root of the BST.
		Queue<AbstractNode<Key, Value>> queue = new Queue<>();
		queue.Enqueue(this.root);
		int height = 0;

		// As long as there are nodes in the queue
		while(!queue.IsEmpty()) {

			// Take the number of nodes at this current level.
			int nodeCounter = queue.size();

			//increments the height of the tree.
			height++;


			// Now as long as there nodes left.
			// Dequeue all nodes of this current level and
			// enqueue all nodes of the next level
			while(nodeCounter > 0) {

				AbstractNode<Key, Value> current = queue.Dequeue();

				if(current != null) {
					if(current.left != end) {
						queue.Enqueue(current.left);
					}

					if(current.right != end) {
						queue.Enqueue(current.right);
					}
				}

				nodeCounter--;
			}
		}

		return height;
	}

	/**
	 * Perform the same algorithm as {@link #height() height} from a given node.
	 * @param nodeStart		a given node
	 * @return		the height of this tree
	 */
	public int height(AbstractNode<Key, Value> nodeStart) {
		if((nodeStart == end) || (nodeStart == null)) {
			return 0;
		}else {

			Queue<AbstractNode<Key, Value>> queue = new Queue<>();
			queue.Enqueue(nodeStart);
			int height = 0;

			while(!queue.IsEmpty()) {

				int nodeCounter = queue.size();

				height++;

				while(nodeCounter > 0) {

					AbstractNode<Key, Value> current = queue.Dequeue();

					if(current != null) {
						if(current.left != end) {
							queue.Enqueue(current.left);
						}

						if(current.right != end) {
							queue.Enqueue(current.right);
						}
					}

					nodeCounter--;
				}
			}
			return height;
		}
	}

	private int size(AbstractNode<Key, Value> root) {

		int size = 0;

		if(!isEmpty() && root != end) {
			//Create a queue
			Queue<AbstractNode<Key, Value>> queue = new Queue<>();

			//enqueue the root
			queue.Enqueue(root);

			while(!queue.IsEmpty()) {

				AbstractNode<Key, Value> current = queue.Dequeue();

				//incrementing the size whenever we dequeue.
				size++;

				//adding children to the queue
				if(current.left != end) {
					queue.Enqueue(current.left);
				}

				if(current.right != end) {
					queue.Enqueue(current.right);
				}
			}
		}

		return size;
	}

	@Override
	public Key min() {
		if (isEmpty()) return null;
		AbstractNode<Key, Value> farthestleftNode = findMin(root);
		return farthestleftNode.key;
	}

	@Override
	public Key max() {
		if (isEmpty()) return null;
		AbstractNode<Key, Value> farthestRighttNode = findMax(root);
		return farthestRighttNode.key;
	}

	@Override
	public Key floor(Key key) {
		AbstractNode<Key, Value> node = floor(root, key);

		if(node == end) {
			System.err.println("the floor of " + key + " does not exist!!");
			return null;
		}

		return node.key;
	}

	private AbstractNode<Key, Value> floor(AbstractNode<Key, Value> root, Key key){
		AbstractNode<Key, Value> current = root;
		AbstractNode<Key, Value> smallestKeyNode = end;

		while(current != end) {
			int cmp = key.compareTo(current.key);

			if(cmp == 0) {
				return current;

				//If a given key is less than the current's key node of a BST, 
				//then the floor of key must be in the left subtree.
			}else if(cmp < 0) {
				current = current.left;

				// If key is greater than the current's key node,
				//then the floor of key could be in the right subtree,
			}else if(cmp > 0) {
				//hold the reference of the current node.
				smallestKeyNode = current;
				current = current.right;
			}
		}

		return smallestKeyNode;
	}

	@Override
	public Key ceiling(Key key) {
		AbstractNode<Key, Value> node = ceiling(root, key);

		if(node == end) {
			System.err.println("the ceiling of " + key + " does not exist!!");
			return null;
		}
		return node.key;
	}

	private AbstractNode<Key, Value> ceiling(AbstractNode<Key, Value> root, Key key){
		AbstractNode<Key, Value> current = root;
		AbstractNode<Key, Value> largestKeyNode = end;

		while(current != end) {
			int cmp = key.compareTo(current.key);

			if(cmp == 0) {
				return current;

				//If a given key is greater than the current's key node of a BST, 
				//then the ceiling of key must be in the right subtree.
			}else if(cmp > 0) {
				current = current.right;

				// If key is smaller than the current's key node,
				// then the ceiling of key could be in the left subtree,
			}else if(cmp < 0) {
				//hold the reference of the current node.
				largestKeyNode = current;
				current = current.left;
			}
		}

		return largestKeyNode;
	}

	@Override
	public int rank(Key key) {
		int rank = rank(root, key);
		return rank;
	}

	private int rank(AbstractNode<Key, Value> root, Key key) {
		int rank = 0;

		AbstractNode<Key, Value> current = root;

		while(current != end) {

			int cmp = key.compareTo(current.key);

			// key is smaller than the current node, then the rank in the whole tree
			// is equal to the rank in the left subtree of this current node.
			if(cmp < 0) {
				current = current.left;

				//Key is larger than the key at the root, we return 'rank' plus one (to count the key at the root).
				//plus the rank of the key in the right subtree.
			}else if(cmp > 0) {
				rank += (1 + size(current.left));
				current = current.right;

			}else {
				return rank + size(current.left);
			}
		}
		return rank;
	}

	@Override
	public void deleteMin() {
		Key key = min();
		delete(key);
		return;
	}

	@Override
	public void deleteMax() {
		Key key = max();
		delete(key);
		return;
	}

	@Override
	public Iterator<Key> iterator() {
		MyIter it = new MyIter();
		return it;
	}

	/**
	 * Put all nodes from the BST in array in in-order traversal.
	 * 
	 * @return		An array of nodes from the BST.
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
			BSTNode<Key, Value> entry = BSTNode.create(current.key, current.value, current.parent, current.left, current.right);
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

	/**
	 * Put all the key-value pairs from the BST in array in in-order traversal.<br/>
	 * 
	 * Iterating the tree in in-order ensures that the retrieval of key-value pairs will be in sorted order (only in a BST).
	 * 
	 * @return an array of key-value pairs in sorted order.
	 */
	@SuppressWarnings("unchecked")
	public Entry<Key, Value>[] InOrderArray(){

		if(isEmpty()) {
			throw new NoSuchElementException("The tree is empty!!");
		}

		int size = size();
		Entry<Key, Value> entries[] = new Entry[size];

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
			Entry<Key, Value> entry = new Entry<Key, Value>(current.key, current.value);
			entries[index++] = entry;
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
		return entries;
	}

	/**
	 * Put all the key-value pairs from the BST in array in level order traversal.<br/>
	 * @return		an array of key-value pairs in level order.
	 */
	public Entry<Key, Value>[] LevelOrderArray(){

		if(isEmpty()) {
			throw new NoSuchElementException("The tree is empty!!");
		}

		Entry<Key, Value> entries[] = new Entry[size()];

		int index = 0;
		//Create and enqueue the root of the BST.
		Queue<AbstractNode<Key, Value>> queue = new Queue<>();
		queue.Enqueue(this.root);

		// As long as there are nodes in the queue
		while(!queue.IsEmpty()) {

			AbstractNode<Key, Value> current = queue.Dequeue();

			//getting the current node key-value pairs.
			Entry<Key, Value> entry = new Entry<Key, Value>(current.key, current.value);
			entries[index++] = entry;
			entry = null;

			if(current != null) {
				if(current.left != end) {
					queue.Enqueue(current.left);
				}

				if(current.right != end) {
					queue.Enqueue(current.right);
				}
			}
		}
		queue = null;
		return entries;
	}

	/**
	 * print data at each level of the three.
	 */
	public void printLevelOrder() {
		// Base case
		if(this.isEmpty()) {
			return;
		}

		//Create and enqueue the root of the BST.
		Queue<AbstractNode<Key, Value>> queue = new Queue<>();
		queue.Enqueue(this.root);

		// As long as there are nodes in the queue
		while(!queue.IsEmpty()) {

			AbstractNode<Key, Value> current = queue.Dequeue();
			System.out.println(current);

			if(current != null) {
				if(current.left != end) {
					queue.Enqueue(current.left);
				}

				if(current.right != end) {
					queue.Enqueue(current.right);
				}
			}
		}

		queue = null;
		return;
	}

	/**
	 * print each and every data of this tree in in-order traversal.
	 */
	public void printInOrder() {

		AbstractNode<Key, Value> current = root;
		boolean leftdone = false;

		while(current != end) {
			//Check whether the leftmost node was not visited yet.
			if(!leftdone) {
				while(current.left != end) {
					current = current.left;
				}
			}

			// visit the current node
			System.out.println(current);

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
		return;
	}



	/**
	 * An iterator that iterates over a BST in sorted order (i.e., inorder traversal)
	 * The algorithm used is the same as {@link BinarySearchTree#InOrderArray() toArray}
	 * 
	 * @author Peguy calusha
	 *
	 */
	private class MyIter implements Iterator<Key>{

		private AbstractNode<Key, Value> nextNode, lastAccessed = end;
		private boolean leftdone;

		private MyIter() {
			nextNode = root;

			if(nextNode == end) {
				throw new NoSuchElementException("The tree to iterate over is empty!");
			}

			//The first element of a subtree is always the leftmost one in inorder traversal.
			while(nextNode.left != end) {
				nextNode = nextNode.left;
			}
			leftdone = true;
		}

		@Override
		public boolean hasNext() {
			return nextNode != end;
		}

		@Override
		public Key next() {
			boolean hasFoundLastAccessed = false;

			//Get the node in inorder traversal
			//this will print a key in sorted order.
			while(nextNode != end && !hasFoundLastAccessed) {

				if(!leftdone) {
					while(nextNode.left != end) {
						nextNode = nextNode.left;
					}
				}

				lastAccessed = nextNode;
				hasFoundLastAccessed = true;
				leftdone = true;

				if(nextNode.right != end) {
					nextNode = nextNode.right;
					leftdone = false;

				}else if(nextNode.parent != end) {

					while((nextNode.parent != end) && (nextNode.parent.right == nextNode)) {
						nextNode = nextNode.parent;
					}

					nextNode = nextNode.parent;
				}
			}
			return lastAccessed.key;
		}
	}
}

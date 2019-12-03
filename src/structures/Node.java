package structures;


/**
 * A node class that will be used for a simply linked list stack.
 * 
 * @author Peguy Calusha
 *
 * @param <T>	Type of component elements
 */
public class Node<T> {

	T data;					// Data or a reference to data
	Node<T> next;			// A reference to the next node

	private Node(T data) {
		this(data, null);
	}

	/**
	 * A constructor which creates a node with data and a pointer to the next node
	 * @param data	the data of the node
	 * @param next	the pointer to the next node
	 */
	private Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}

	/**
	 * A static factory method which perform the creation of a new node.
	 * @param data	the element of the node.
	 * @param next	the pointer to the next node.
	 * @return		a new node.
	 */
	public static <T> Node<T> Build(T data, Node<T> next){
		Node<T> newNode = new Node<T>(data, next);
		return newNode;
	}

	public static <T> Node<T> Build(T data){
		Node<T> newNode = new Node<T>(data);
		return newNode;
	}

	/**
	 * Create a distinct copy of this node.
	 * 
	 * @return	a distinct copy of this node.
	 */
	public Node<T> deepCopy(){
		Node<T> newNode = new Node<T>(this.data, this.next);
		return newNode;
	}

	@Override
	public String toString(){
		return data.toString();
	}
}

package structures;

/**
 * Embody the Sequential Search node.
 * 
 * @author Peguy Calusha
 *
 * @param <K>	type of key components.
 * @param <V>	type of value components.
 */
public class SSNode<K, V> {

	K key;		// the key component.
	V value;	// the value component.
	SSNode<K, V> next;	// a reference to the next sequential search node.
	
	private SSNode(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	private SSNode(K key, V value, SSNode<K, V> next) {
		this(key, value);
		this.next = next;
	}
	
	public static <K, V> SSNode<K, V> build(K key, V value){
		SSNode<K, V> newNode = new SSNode<K, V>(key, value);
		return newNode;
	}
	
	public static <K, V> SSNode build(K key, V value, SSNode<K, V> next) {
		SSNode<K, V> newNode = new SSNode<K, V>(key, value, next);
		return newNode;
	}
	
	public SSNode<K, V> deepCopy(){
		SSNode<K, V> newNode = new SSNode<K, V>(this.key, this.value, this.next);
		return newNode;
	}
	
	@Override
	public String toString() {
		return "Key = " + key + ", Value = " + value;
	}
}

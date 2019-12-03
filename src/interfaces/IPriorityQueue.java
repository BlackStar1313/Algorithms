package interfaces;

public interface IPriorityQueue<E extends Comparable<E>> extends Iterable<E>{
    E remove();
    void insert(E elem);
    boolean isEmpty();
}

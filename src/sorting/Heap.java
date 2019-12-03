package sorting;

import structures.MinPriorityQueue;

public class Heap {

	public static <E extends Comparable<E>> void sort(E[] array) {
		
		MinPriorityQueue<E> heap = new MinPriorityQueue<>();
		
		for (int i = 0; i < array.length; i++){
            heap.insert(array[i]);
        }
		
        for (int i = 0; i < array.length; i++){
            array[i] = heap.deleteMin();
        }
	}
	
}

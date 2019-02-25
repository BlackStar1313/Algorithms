package sorting;

import structures.Heap;

public class HeapSort {

	public static <T extends Comparable<T>> void sort(T[] array) {
		
		Heap<T> heap = new Heap<>();
		
		for (int i = 0; i < array.length; i++){
            heap.insert(array[i]);
        }
        for (int i = 0; i < array.length; i++){
            array[i] = heap.remove();
        }
	}
	
}

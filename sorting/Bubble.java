package sorting;

import helper.GenericHelper;

public class Bubble {

	public static <T extends Comparable<? super T>> void sort(T[] array) {

		// first way to implement this using flag..
		boolean anotherSwap;
		int N = array.length;

		do {
			anotherSwap = false;// set flag to false to wait for a possible swap.

			for(int i = 0; i < N - 1; i++) {
				if(GenericHelper.isLess(array[i+1], array[i])) {// ascending sorting...
					GenericHelper.swap(array, i, i + 1);
					anotherSwap = true;// a swap occurred!
				}
			}

		}while(anotherSwap);

		/*
		// a second way to implement...
		for(int i = 0; i < N - 1; i++) {
			for(int j = N - 1; j < i; j--) {
				if(GenericHelper.isLess(array[j], array[j - 1])) {// descending sorting...
					GenericHelper.swap(array, j, j - 1);
				}
			}
		}*/
	}

	public <T extends Comparable<? super T>> void optimizedSort(T[] array) {
		// first way to implement this using flag..
		boolean anotherSwap;
		int N = array.length;
		do {
			anotherSwap = false;// set flag to false to wait for a possible swap.

			for(int i = 0; i < N - 1; i++) {
				if(GenericHelper.isLess(array[i+1], array[i])) {// ascending sorting...
					GenericHelper.swap(array, i, i + 1);
					anotherSwap = true;// a swap occurred!
				}
			}
			N -= 1;

		}while(anotherSwap);
	}
}

package sorting;

import helper.GenericHelper;

public class InsertionSort {
	
	/**
	 * Insertion sort is a simple sorting algorithm that builds the final sorted array (or list) one item at a time.
	 * It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.
	 * 
	 * @param array
	 */
	public static <T extends Comparable<? super T>> void sort(T[] array) {
		for(int i = 1; i < array.length; i++) {
			int j = i;
			while((j > 0) && (GenericHelper.isLess(array[j], array[j-1])) ) {
				GenericHelper.swap(array, j, j-1);
				j--;
			}
			 assert GenericHelper.isSorted(array, 0, i);
		}
		assert  GenericHelper.isSorted(array);
	}
	
	/**
	 * After expanding the swap operation in-place as x ← array[j]; array[j] ← array[j-1]; array[j-1] ← x (where x is a temporary variable),<br>
	 * a slightly faster version can be produced that moves array[i] to its position in one go and only performs one assignment in the inner loop body.
	 * @param array
	 */
	public static <T extends Comparable<? super T>> void optimizedSort(T[] array) {
		for(int i = 1; i < array.length; i++) {
			T key = array[i];
			int j = i;
			while((j > 0) && (GenericHelper.isLess(key, array[j-1])) ) {
				array[j] = array[j-1];
				j--;
			}
			array[j] = key;
			assert GenericHelper.isSorted(array, 0, i);
		}
		assert  GenericHelper.isSorted(array);
	}
	
	
	public static <T extends Comparable<? super T>> void SortWithSentinel(T[] array) {
		
		//A static Sentinel will be a very low value which will be placed as the first element of the array I.e. in array [0]. 
		//This gives the advantage of not checking the condition "j>0" in your code.
		//The j>0 condition is added so that the program doesn't give an ArrayIndexOutOfBounds Exception.
		//By adding a padded element at array[0], and running the loop from array[1....n], we save the overhead of checking the j>0 condition.
		
		//So step 1, we run through array and find the index of the smallest element.
		int iMinIndex = 0;
		int k = 0;
		while(k < array.length) {
			if(GenericHelper.isLess(array[k], array[iMinIndex])) {
				iMinIndex = k;
			}
			k++;
		}
		
		//Step 2, we shift it to the 0-th position.
		GenericHelper.swap(array, 0, iMinIndex);
		
		//Now the minimal element at index iMinIndex works as sentinel.
		//The rest of the array can be sorted without border checking.
		for(int i = 1; i < array.length; i++) {
			int j = i;
			while((GenericHelper.isLess(array[j], array[j-1])) ) {
				GenericHelper.swap(array, j, j-1);
				j--;
			}
			assert GenericHelper.isSorted(array, 0, i);
			for(T elt : array) {
				System.out.print(elt + " ");
			}
			System.out.println();
		}
		assert  GenericHelper.isSorted(array);
	}

}

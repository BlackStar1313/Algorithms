package sorting;

import helper.GenericHelper;

public final class Merge {
	
	
	/**
	 * the minimum size of an array.
	 */
	private static final int INSERTIONSORT_THRESHOLD = 6;

	/**
	 * See this -> 
	 * {@link #topDownSplit(T[] originalArray, T[] tempArray, int startIndex, int endIndex) mergeSort}.
	 * @param array	the array to sort of.
	 */
	public static <T extends Comparable<? super T>> void sort(T[] array) {

		T[] tempArray = (T[]) new Comparable[array.length];

		topDownSplit(array, tempArray, 0, array.length-1);
		
		return;
	}
	
	/**
	 * 
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 */
	public static <T extends Comparable<? super T>> void sort(T[] array, int startIndex, int endIndex) {
		topDownSplit(array, (T[]) new Comparable[array.length], startIndex, endIndex);
		return;
	}
	

	/**
	 * Perform the merge sort algorithm on the given array.
	 * 
	 * @param originalArray	the original array.
	 * @param tempArray		the temporary array.
	 * @param startIndex	the index of the first element.
	 * @param endIndex		the index of the last element.
	 */
	private static <T extends Comparable<? super T>> void topDownSplit(T[] originalArray, T[] tempArray, int startIndex, int endIndex) {

		// If the array's size is 1
		// then it is considered sorted.
		if((endIndex - startIndex) < 1) {
			return;
			//If the array's size is smaller than limit
			//then do the insertion sort.
		}else if((endIndex - startIndex) < INSERTIONSORT_THRESHOLD) {
			
			Insertion.optimizedSort(originalArray, startIndex, endIndex);

			//otherwise, split the array as long as its size is greater than 1
		}else {
			int middle = (startIndex + endIndex) / 2;
			topDownSplit(originalArray, tempArray, startIndex, middle);
			topDownSplit(originalArray, tempArray, middle+1, endIndex);
			topDownMerge(originalArray, tempArray, startIndex, middle, endIndex);
		}
	}

	/**
	 * Merges two sublists.
	 * 
	 * @param originalArray	the original array.
	 * @param tempArray		the temporary array.
	 * @param startIndex	the index of the first element.
	 * @param endIndex		the index of the last element.
	 */
	public static <T extends Comparable<? super T>> void topDownMerge(T[] array, T[] temp, int startIndex, int middle, int endIndex) {

		// precondition: array[start .. middle] and a[middle+1 .. end] are sorted sub-arrays
		//assert GenericHelper.isSorted(array, startIndex, middle);
		//assert GenericHelper.isSorted(array, middle+1, endIndex);
		
		// Copying in the temporary array, all values from start to end indexes.
		for(int i = startIndex; i <= endIndex; i++) {
			temp[i] = array[i];
		}
		
		//Merging back to the original array...
		int leftStart = startIndex, k = startIndex;
		int rightStart = middle+1;
		
		while(leftStart <= middle && rightStart <= endIndex) {
			if(GenericHelper.isLess(temp[rightStart], temp[leftStart])){
				array[k++] = temp[rightStart++];
			}else {
				array[k++] = temp[leftStart++];
			}
		}
		
		// Only one of the two loops below will actually be executed.
		// Copy any remaining entries of the first half
		while(leftStart <= middle) {
			array[k++] = temp[leftStart++];
		}
		
		// Copy any remaining entries of the second half
		while(rightStart <= endIndex) {
			array[k++] = temp[rightStart++];
		}
		
		// postcondition: array[start .. end] is sorted
		//assert GenericHelper.isSorted(array, startIndex, endIndex);
		return;
	}

}

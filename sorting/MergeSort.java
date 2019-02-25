package sorting;

import helper.GenericHelper;

public final class MergeSort {

	//the minimum size of an array.
	private static final int THRESHOLD = 6;

	/**
	 * See this -> 
	 * {@link #topDownMergeSort(T[] originalArray, T[] tempArray, int startIndex, int endIndex) mergeSort}.
	 * @param array	the array to sort of.
	 */
	public static <T extends Comparable<? super T>> void sort(T[] array) {

		T[] tempArray = (T[]) new Comparable[array.length];

		topDownMergeSort(array, tempArray, 0, array.length-1);
	}

	/**
	 * Perform the merge sort algorithm on the given array.
	 * 
	 * @param originalArray	the original array.
	 * @param tempArray		the temporary array.
	 * @param startIndex	the index of the first element.
	 * @param endIndex		the index of the last element.
	 */
	private static <T extends Comparable<? super T>> void topDownMergeSort(T[] originalArray, T[] tempArray, int startIndex, int endIndex) {

		if((endIndex - startIndex) < 1) {
			return;
			//If the array's size is smaller than limit
			//then do the insertion sort.
		}else if((endIndex - startIndex + 1) <= THRESHOLD) {
			doInsertionSort(originalArray, startIndex, endIndex);

			//otherwise, split the array as long as its size is greater than 1
		}else {
			int middle = (startIndex + endIndex) / 2;
			topDownMergeSort(originalArray, tempArray, startIndex, middle);
			topDownMergeSort(originalArray, tempArray, middle+1, endIndex);
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
	private static <T extends Comparable<? super T>> void topDownMerge(T[] array, T[] temp, int startIndex, int middle, int endIndex) {

		// precondition: array[start .. middle] and a[middle+1 .. end] are sorted sub-arrays
		assert GenericHelper.isSorted(array, startIndex, middle);
		assert GenericHelper.isSorted(array, middle+1, endIndex);
		
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
	}

	/**
	 * Using the insertion to merge elements of the array.
	 * @param originalArray		the very original array.
	 * @param endIndex			the end index of the current array.
	 */
	private static <T extends Comparable<? super T>> void doInsertionSort(T[] originalArray, int startIndex, int endIndex) {

		for(var i = startIndex; i <= endIndex; i++) {
			T key = originalArray[i];
			var j = i;
			while((j > 0) && key.compareTo(originalArray[j-1]) < 0){
				originalArray[j] = originalArray[j-1];
				j--;
			}
			originalArray[j] = key;
		}
	}

}

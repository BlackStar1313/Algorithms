package helper;

import java.util.NoSuchElementException;
import java.util.Random;


public final class GenericHelper {

	private static Random random;    // pseudo-random number generator
	private static long seed;        // pseudo-random number generator seed

	// static initializer
	static {
		// this is how the seed was set iJava 1.4
		seed = System.nanoTime();
		random = new Random(seed);
	}

	private GenericHelper() {
		super();
	}

	public static <T extends Number > T[] Subset(T array[], int start, int end) {

		if(start < 0 || end < 0 || array == null) {
			throw new IllegalArgumentException("Could not take the subset of the given array!");
		}

		final int size = end - start;
		T[] subset = (T[]) new Number[size+1];
		int index = 0;
		for(int i = start; i <= end; i++) {
			subset[index++] = array[i];
		}

		return subset;
	}

	/**
	 * Check whether value1 is smaller than value2 that is to say {@code value1 < value2}
	 * @param value1	the first value to compare to.
	 * @param value2	the second value to compare to.
	 * @return true if value1 is smaller than value2, otherwise false.
	 */
	public static <T extends Comparable<? super T>> boolean isLess(T value1, T value2) {
		if(value1 == null || value2 == null) {
			throw new IllegalArgumentException("Can't compare null values!!");
		}
		return value1.compareTo(value2) < 0;
	}

	/**
	 * Check whether value1 is greater than value2 that is to say {@code value1 > value2}
	 * @param value1	the first value to compare to.
	 * @param value2	the second value to compare to.
	 * @return	true if value1 is greater than value2, otherwise false.
	 */
	public static <T extends Comparable<? super T>> boolean isGreater(T value1, T value2) {
		if(value1 == null || value2 == null) {
			throw new IllegalArgumentException("Can't compare null values!!");
		}
		return value1.compareTo(value2) > 0;
	}

	/**
	 * Perform the swapping of two elements within an array.
	 * @param array		the array in which two elements is swapped
	 * @param first		the first index.
	 * @param second	the second index.
	 */
	public static <T> void swap(T[] array, int first, int second) {

		if(first < 0 || first >= array.length || second < 0 || second >= array.length) {
			throw new ArrayIndexOutOfBoundsException("\nYour index are out of bounds!!");
		}

		T temp = array[first];
		array[first] = array[second];
		array[second] = temp;
	}

	/**
	 * Check whether a given array is sorted.
	 * @param array		the array to check whether it is sorted or not.
	 * @return	true if the given array is sorted, otherwise false.
	 */
	public static <T extends Comparable<? super T>> boolean isSorted(T[] array) {

		if(array == null) {
			throw new NoSuchElementException("There is not any array to check!");
		}

		for(int i = 1; i < array.length; i++) {
			if(isLess(array[i], array[i-1])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Works like {@code isSorted} except it checks whether this array is sorted from array[from] to array[to].
	 * @param array		the array to check.
	 * @param from		the starting index.
	 * @param to		the ending index.
	 * @return	true if the array is from-to-sorted, otherwise false.
	 */
	public static <T extends Comparable<? super T>> boolean isSorted(T[] array, int from, int to) {

		if(from < 0 || from >= array.length || to < 0 || to >= array.length) {
			throw new ArrayIndexOutOfBoundsException("The indices are not convenient!");
		}

		for(int i = from + 1; i <= to; i++) {
			if(isLess(array[i], array[i-1])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Work like {@code isSorted} except it checks whether this array is gap-sorted.
	 * @param array
	 * @param gap
	 * @return	true if the array is gap-sorted, otherwise false
	 */
	public static <T extends Comparable<? super T>> boolean isSorted(T[] array, int gap) {
		for (int i = gap; i < array.length; i++)
			if (isLess(array[i], array[i-gap])) return false;
		return true;
	}

	/**
	 * Rearrange the elements of an array in random order.
	 * 
	 * @param array the array to shuffle.
	 */
	public static <T> void shuffle(T[] array) {
		int length = array.length;
		for(int i = 0; i < length; i++) {
			int k = uniform(0, i);
			//No need to swap if both k and i are the same.
			if(k != i) {
				swap(array, i, k);
			}
		}
	}

	public static Integer[] createRandomArray(int size, long seed) {

		Integer[] arr = new Integer[size];

		Random rand = new Random(seed);

		for(int i = 0; i < size; i++) {
			arr[i] = rand.nextInt();
		}

		return arr;
	}

	public static Integer[] createRandomArray(int size) {

		Integer[] arr = new Integer[size];

		for(int i = 0; i < size; i++) {
			arr[i] = random.nextInt(20);
		}

		return arr;
	}

	/**
	 * Split an array into n parts.
	 * @param source	the original array.
	 * @param parts		n parts to divide from.
	 * @return
	 */
	public static <T extends Comparable<? super T>> T[][] splitChunks(T[] source, int parts){

		if(source == null) {
			throw new NoSuchElementException("There is not any array to check!");
		}

		if(parts == 0) {
			throw new IllegalArgumentException("can't split into 0 part!!");
		}

		int chunkSize = (int) (Math.ceil(source.length / (double)parts));

		T[][] results = (T[][]) new Comparable[parts][];

		int sourceLength = source.length;

		for(int i = 0, start = 0; i < results.length; i++) {

			if((start + chunkSize) > sourceLength) {
				results[i] = (T[]) new Comparable[sourceLength - start];
				System.arraycopy(source, start, results[i], 0, results[i].length);
			}else {
				results[i] = (T[]) new Comparable[chunkSize];
				System.arraycopy(source, start, results[i], 0, chunkSize);
			}

			start += chunkSize;
		}

		return results;
	}

	/**
	 * Perform the merge sort on multiple sorted arrays.
	 * @param arrays	the multiple arrays to be merged into one sorted array.
	 * @return			the final array sorted.
	 */
	public static <T extends Comparable<? super T>> T[] mergeArrays(T[]...arrays) {

		if(arrays == null || arrays.length == 0) {
			throw new IllegalArgumentException("Can't merge empty arrays!");
		}

		// checking whether the arrays are sorted or not.
		for(T[] arr : arrays) {
			if(!isSorted(arr)) {
				throw new IllegalArgumentException("Arrays are not sorted!");
			}
		}

		T[] result = null;

		if(arrays.length >= 2) {
			result = arrays[0];
			for(int i = 1; i < arrays.length; i++) {
				result = merge(result, arrays[i]);
			}
		}else {
			result = arrays[0];
		}

		return result;
	}

	/**
	 * Perform the merge on two sorted arrays.
	 * @param leftArray		the left-sorted array.
	 * @param rightArray	the right-sorted array.
	 * @return				one sorted array.
	 */
	public static <T extends Comparable<? super T>> T[] merge(T[] leftArray, T[] rightArray) {

		// Creating the corresponding temporary array...
		int N = leftArray.length + rightArray.length;
		T[] result = (T[]) new Comparable[N];

		//Merging back to the original array...
		int leftStart = 0, k = 0, rightStart = 0;

		// Merging both halves in the result array.
		while(k < N) {
			if(leftStart >= leftArray.length) {
				result[k++] = rightArray[rightStart++];

			}else if(rightStart >= rightArray.length) {
				result[k++] = leftArray[leftStart++];

			}else if(rightArray[rightStart].compareTo(leftArray[leftStart]) < 0) {
				result[k++] = rightArray[rightStart++];

			}else {
				result[k++] = leftArray[leftStart++];
			}
		}

		return result;
	}

	/**
	 * Generate a random number between minimum number(inclusive) and maximum number(inclusive).
	 * @param a
	 * @param b
	 * @return
	 */
	public static int uniform(int a, int b) {

		if(a > b) {
			throw new IllegalStateException("the max number must be grater than the min");
		}else if(a == b) {
			return a;
		}
		return (random.nextInt(b - a) + 1) + a;
	}

	/**
	 * Prints a 1D-array
	 * @param array	the array to be printed.
	 */
	public static<T> void print1DArray(T[] array) {

		if(array == null) {
			throw new NoSuchElementException("There is not any array to check!");
		}

		for(T elt : array) {
			System.out.print(elt + " ");
		}
	}

	/**
	 * Prints a 2D-array
	 * @param array	the array to be printed.
	 */
	public static<T> void print2DArray(T[][] array) {

		if(array == null) {
			throw new NoSuchElementException("There is not any array to check!");
		}

		for(T[] rows : array) {
			for(T cells : rows) {
				System.out.print(cells + " ");
			}
			System.out.println();
		}
	}


}

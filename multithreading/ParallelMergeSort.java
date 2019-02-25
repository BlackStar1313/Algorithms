package multithreading;

import sorting.MergeSort;

public final class ParallelMergeSort<T extends Comparable<? super T>> implements Runnable {

	/**
	 * Internal storage of the original array.
	 */
	private T[] originalArray;

	/**
	 * The first index within the array.
	 */
	private int from;

	/**
	 * The last index within the array.
	 */
	private int to;

	/**
	 * The number of threads.
	 */
	private int nbThreads;


	/**
	 * 
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @param threads
	 */
	private ParallelMergeSort(T[] array, int startIndex, int endIndex, int threads) {
		originalArray = array;
		from = startIndex;
		to = endIndex;
		nbThreads = threads;
	}

	public static <T extends Comparable<? super T>> ParallelMergeSort<T> newInstance(T[] arr, int startIndex, int endIndex, int threads) {
		ParallelMergeSort<T> sort = new ParallelMergeSort<>(arr, startIndex, endIndex, threads);
		return sort;
	}

	@Override
	public void run() {
		parallelTopDownSplit(originalArray, from, to, nbThreads);
	}

	/**
	 * 
	 * @param a
	 * @param startIndex
	 * @param endIndex
	 * @param availableThreads
	 */
	private void parallelTopDownSplit(T[] a , int startIndex, int endIndex, int availableThreads) {

		if((endIndex - startIndex) < 2) {
			return;
		}else {

			// Checking whether the number of threads is smaller or equal to 1.
			// In this case we do the normal merge sort.
			if(availableThreads <= 1) {
				MergeSort.sort(a, startIndex, endIndex);
				return;
			}else {
				int midpoint = startIndex + (endIndex - startIndex) / 2;// avoiding overflow for large startIndex and endIndex...

				Thread thread1 = new Thread(newInstance(a, startIndex, midpoint, availableThreads - 1));
				thread1.start();

				Thread thread2 = new Thread(newInstance(a, midpoint + 1, endIndex, availableThreads - 1));
				thread2.start();

				try {
					thread1.join();
					thread2.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				parallelTopDownMerge(startIndex, midpoint, endIndex);
			}
		}
	}

	/**
	 * Perform the top down merge
	 * @param startIndex
	 * @param midpoint
	 * @param endIndex
	 */
	@SuppressWarnings("unchecked")
	private void parallelTopDownMerge(int startIndex, int midpoint, int endIndex) {

		// Creating the corresponding temporary array...
		int N = endIndex - startIndex + 1;
		T[] temp = (T[]) new Comparable[N];

		//Merging back to the original array...
		int leftStart = startIndex, k = 0;
		int rightStart = midpoint+1;

		// Merging both halves in the temporary array.
		while(k < N) {
			if(leftStart > midpoint) {
				temp[k++] = originalArray[rightStart++];

			}else if(rightStart > endIndex) {
				temp[k++] = originalArray[leftStart++];

			}else if(originalArray[rightStart].compareTo(originalArray[leftStart]) < 0) {
				temp[k++] = originalArray[rightStart++];

			}else {
				temp[k++] = originalArray[leftStart++];
			}
		}

		// Copying in the original array, all values from start to end indexes from the temporary array.
		for(int i = 0; i < N; i++) {
			originalArray[i + startIndex] = temp[i];
		}
		return;
	}
}

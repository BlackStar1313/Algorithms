package multithreading;

import java.util.concurrent.CountDownLatch;
import sorting.Insertion;

public final class ParallelMergeSort2<T extends Comparable<? super T>> extends Thread {

	/**
	 * Internal storage of the splitted array.
	 */
	private T[] array;

	/**
	 * Internal storage of the temporary array.
	 */
	private T[] temp;
	
	/**
	 * for explicit synchronization between threads.
	 */
	private CountDownLatch start, done;

	/**
	 * the minimum size of an array.
	 */
	private static final int INSERTIONSORT_THRESHOLD = 6;

	/**
	 * 
	 * @param originalArray
	 */
	private ParallelMergeSort2(T[] originalArray, CountDownLatch startSignal, CountDownLatch doneSignal) {
		array = originalArray;
		temp = (T[]) new Comparable[array.length];
		start = startSignal;
		done = doneSignal;
	}
	
	/**
	 * Create the thread with fields.
	 * @param originalArray
	 * @param startSignal
	 * @param doneSignal
	 * @return
	 */
	public static <T extends Comparable<? super T>> ParallelMergeSort2 newInstance(T[] originalArray, CountDownLatch startSignal, CountDownLatch doneSignal) {
		ParallelMergeSort2<T> thread = new ParallelMergeSort2<>(originalArray, startSignal, doneSignal);
		return thread;
	}

	@Override
	public void run() {
		try {
			// keep waiting until the main thread counts down the latch(until the main thread informs this thread to start...)
			start.await();
			parallelTopDownSplit(array, 0, array.length - 1);
			done.countDown();// the work is done.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Perform the top down merge sort on the given array-
	 * @param a				the original array.
	 * @param startIndex	the index of the first element.
	 * @param endIndex		the index of the flast element.
	 */
	private void parallelTopDownSplit(T[] a, int startIndex, int endIndex) {

		if((endIndex - startIndex) < 1) {
			return;

		}else if((endIndex - startIndex) < INSERTIONSORT_THRESHOLD) {
			Insertion.optimizedSort(array, startIndex, endIndex);

		}else {

			int midPoint = startIndex + (endIndex - startIndex) / 2;
			parallelTopDownSplit(a, startIndex, midPoint);
			parallelTopDownSplit(a, midPoint + 1, endIndex);
			parallelTopDownMerge(startIndex, midPoint, endIndex);
		}
	}

	/**
	 * Merge two sublists.
	 * 
	 * @param start		the index of the first element.
	 * @param middle	the index of the middle element.
	 * @param endIndex	the index of the last element.
	 */
	private void parallelTopDownMerge(int startIndex, int midPoint, int endIndex) {

		// Copying in the temporary array, all values from start to end indexes.
		for(int i = startIndex; i <= endIndex; i++) {
			temp[i] = array[i];
		}

		//Merging back to the original array...
		int leftStart = startIndex, k = startIndex;
		int rightStart = midPoint+1;
		while(k <= endIndex) {
			if(leftStart > midPoint) {
				array[k++] = temp[rightStart++];
				
			}else if(rightStart > endIndex){
				array[k++] = temp[leftStart++];
			
			}else if(temp[rightStart].compareTo(temp[leftStart]) < 0) {
				array[k++] = temp[rightStart++];
			
			}else {
				array[k++] = temp[leftStart++];
			}
		}
		return;
	}
	

	/**
	 * @return the internal of the original array.
	 */
	public T[] storage() {
		return array;
	}

}

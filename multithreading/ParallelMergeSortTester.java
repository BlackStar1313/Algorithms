package multithreading;

import static java.lang.System.nanoTime;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import helper.GenericHelper;

public class ParallelMergeSortTester {

	/**
	 * Processing the parallel merge sort.
	 */
	public static void process() {

		// Creating a random array
		Comparable[] originalArray = GenericHelper.createRandomArray(10000000);

		// Taking the number of threads
		int nbThreads = ThreadHelper.threads();

		// A pool of threads for processing the results.
		ParallelMergeSort2[] threads = new ParallelMergeSort2[nbThreads];

		// Splitting the original array.
		Comparable[][] arrays = GenericHelper.splitChunks(originalArray, nbThreads);

		// creating the main latch...
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(nbThreads);

		// initializing threads...
		for(int k = 0; k < threads.length; k++) {
			// distributing the work load and adding to the pool...
			threads[k] = ParallelMergeSort2.newInstance(arrays[k], startSignal, doneSignal);;
		}
		
		
		// starting time...
		long startTime = nanoTime();
		
		/**Uncomment this to test the second parallel Merge Sort**/
		/*Thread thread = new Thread(ParallelMergeSort1.newInstance(originalArray, 0, originalArray.length - 1, nbThreads));
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*********************************************************/

		// running each thread...
		for(ParallelMergeSort2<Integer> thread : threads) {
			thread.start();
		}
		
		// informing all threads to start sorting at the same time...
		startSignal.countDown();

		// waiting for each thread to finish their job...
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// merging all sorted array...
		for(int i = 0; i < threads.length; i++) {
			arrays[i] = threads[i].storage();
		}
		originalArray = GenericHelper.mergeArrays(arrays);

		// stopping time...
		long endTime = nanoTime();
		
		

		if(GenericHelper.isSorted(originalArray)) {
			System.out.println("the array is fully sorted!!");
		}else {
			System.out.println("the array is not sorted!!");
		}

		System.out.println("Execution Time: " + (double) (endTime - startTime)/1000000000.0 + " seconds");

		for(ParallelMergeSort2<Integer> thread : threads) {
			thread = null;
		}
		
		threads = null;
		return;
	}

	/**
	 * Split an array into n parts.
	 * @param source	the original array.
	 * @param parts		n parts to divide from.
	 * @return
	 */
	private static Integer[][] splitChunks(Integer[] source, int parts) {

		int chunkSize = (int) (Math.ceil(source.length / (double)parts));

		Integer[][] results = new Integer[parts][];

		int sourceLength = source.length;

		for(int i = 0, start = 0; i < results.length; i++) {

			if((start + chunkSize) > sourceLength) {
				results[i] = new Integer[sourceLength - start];
				System.arraycopy(source, start, results[i], 0, results[i].length);
			}else {
				results[i] = new Integer[chunkSize];
				System.arraycopy(source, start, results[i], 0, chunkSize);
			}

			start += chunkSize;
		}

		return results;
	}
}

package multithreading;

import static java.lang.System.nanoTime;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import structures.Queue;

public class MultiThreadedPrimeTester {

	public static void process() {

		// Taking the user input
		BigInteger number = ThreadHelper.input();

		// Taking the number of threads
		int nbThreads = ThreadHelper.threads();

		// Taking all possible divisors for the number to test.
		Queue<BigInteger> bigInts = new Queue<>();
		BigInteger limit = number.sqrt();
		BigInteger currNumber = BigInteger.valueOf(5);
		bigInts.Enqueue(currNumber);
		while(currNumber.compareTo(limit) <= 0) {
			currNumber = currNumber.add(BigInteger.valueOf(6));
			bigInts.Enqueue(currNumber);
		}

		// Creating the divisors' array...
		BigInteger arr[] = new BigInteger[bigInts.size()];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = bigInts.Dequeue();
		}

		// creating the main and a secondary latches...
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(nbThreads);

		// A pool of threads for processing the results.
		MultiThreadedPrimeNumber[] threadsPool = new MultiThreadedPrimeNumber[nbThreads];


		BigInteger[][] divisors = splitDivisors(arr, nbThreads);

		// initializing threads...
		for(int k = 0; k < threadsPool.length; k++) {
			// distributing the work load and adding to the pool...
			threadsPool[k] = new MultiThreadedPrimeNumber(divisors[k], number, startSignal, doneSignal);;
		}

		// starting time...
		long startTime = nanoTime();

		// running each thread...
		for(MultiThreadedPrimeNumber primeThread : threadsPool) {
			primeThread.start();
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

		// stopping time...
		long endTime = nanoTime();

		// Checking whether the number is prime or not.
		// we first suppose the number is prime, 
		// i.e., all of the threads identify it as a prime.
		boolean prime = true;
		for(int i = 0; prime && i < threadsPool.length; i++) {

			// If any thread identifies the number as non-prime, then it is not a prime number.
			if(!threadsPool[i].isPrime) {
				prime = false;
			}
		}

		if(prime) {
			System.out.println("Your number is prime");
		}else {
			System.out.println("Your number is not prime");
		}

		System.out.println("Execution Time: " + (double) (endTime - startTime)/1000000000.0 + " seconds");

		bigInts = null;
		arr = null;
		threadsPool = null;
		divisors = null;
		return;
	}


	public static BigInteger[][] splitDivisors(BigInteger[] source, int parts) {
		if(source == null) {
			throw new NoSuchElementException("There is not any array to check!");
		}

		if(parts == 0) {
			throw new IllegalArgumentException("can't split into 0 part!!");
		}

		int chunkSize = (int) (Math.ceil(source.length / (double)parts));

		BigInteger[][] results = new BigInteger[parts][];

		int sourceLength = source.length;

		for(int i = 0, start = 0; i < results.length; i++) {

			if((start + chunkSize) > sourceLength) {
				results[i] = new BigInteger[sourceLength - start];
				System.arraycopy(source, start, results[i], 0, results[i].length);
			}else {
				results[i] = new BigInteger[chunkSize];
				System.arraycopy(source, start, results[i], 0, chunkSize);
			}

			start += chunkSize;
		}

		return results;
	}

}

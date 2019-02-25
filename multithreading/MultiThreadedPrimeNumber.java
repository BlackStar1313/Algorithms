package multithreading;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;

public final class MultiThreadedPrimeNumber extends Thread {

	/**
	 * A boolean to check whether the original number is divisible by an array of divisors
	 */
	public boolean isPrime;
	
	/**
	 * Internal storage of divisors.
	 */
	private BigInteger[] divisors;
	
	/**
	 * The original number
	 */
	private BigInteger input;
	
	/**
	 * for explicit synchronization between threads.
	 */
	private CountDownLatch start, done;
	
	/**
	 * Create a thread with its initial attributes.
	 * @param range
	 * @param originalNumber
	 * @param startSignal
	 * @param doneSignal
	 */
	public MultiThreadedPrimeNumber(BigInteger[] range, BigInteger originalNumber, CountDownLatch startSignal, CountDownLatch doneSignal) {
		divisors = range;
		input = originalNumber;
		start = startSignal;
		done = doneSignal;
	}
	
	
	@Override
	public void run() {
		try {
			start.await();
			isPrime = isPrime(input);
			done.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether the original is divisible by one of the possible divisors.
	 * @param originalNumber
	 * @return	true if none of these divisors can't be divisible by the original number, otherwise false.
	 */
	private boolean isPrime(BigInteger originalNumber) {
		BigInteger mod1 = null, mod2 = null;
		for(BigInteger divisor : divisors) {
			mod1 = divisor.mod(originalNumber);
			mod2 = divisor.add(BigInteger.TWO).mod(originalNumber);
			if(mod1.equals(BigInteger.ZERO) || mod2.equals(BigInteger.ZERO)) {
				return false;
			}
		}
		return true;
	}
}

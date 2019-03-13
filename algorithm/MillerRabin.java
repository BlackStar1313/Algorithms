package algorithm;

import java.math.BigInteger;
import java.util.Random;

/**
 * The Miller–Rabin primality test or Rabin–Miller primality test is a primality test: 
 * an algorithm which determines whether a given number is prime, similar to the Fermat primality test and the Solovay–Strassen primality test.
 * 
 * @author Peguy Njoyim
 *
 */
public final class MillerRabin {
	
	/**
	 * The number to test the primality.
	 */
	private BigInteger n;

	/**
	 * The parameter k determines the accuracy of the test.<br/>
	 * The greater the number of rounds, the more accurate the result. 
	 */
	private int k;
	
	/**
	 * Creates the Miller-Rabin with the appropriate fields.
	 * @param input
	 * @param accuracy
	 */
	public MillerRabin(BigInteger input, int accuracy) {
		n = input;
		k = accuracy;
	}
	
	/**
	 * 
	 * @return true if the number is a strong probable prime to base a, otherwise false.
	 */
	public boolean isProbablePrime() {
		
		if(n.compareTo(BigInteger.ONE) <= 0) {
			return false;
		}else if(n.compareTo(BigInteger.valueOf(3)) <= 0) {
			return true;
		}else {
			int s = 0;
			BigInteger d = n.subtract(BigInteger.ONE);
			BigInteger temp = d;
			while(d.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
				s++;
				d = d.shiftRight(1);
			}
			
			for(int i = 0; i < k; i++) {
				BigInteger a = uniform(BigInteger.TWO, temp);
				BigInteger x = a.modPow(d, n);
				
				if(!(x.equals(BigInteger.ONE)) && !(x.equals(temp))){
					boolean isStrongLiar = false;
					int r = 0;
					for(; r < s && !isStrongLiar; r++) {
						x = x.modPow(BigInteger.TWO, n);
						if(x.equals(BigInteger.ONE)) {
							return false;//	composite for sure
						}else if(x.equals(temp)) {
							isStrongLiar = true;//	could be a strong liar, try another a
						}
					}
					
					if (r == s) {
						return false;// None of the steps made x equal n-1.
					}
				
				}
			}
		}
		return true;
	}
	
	/**
	 * Generate a random BigInteger between minimum number(inclusive) and maximum number(inclusive).
	 * @param bottom	The minimum number
	 * @param top		the maximum number.
	 * @return
	 */
	private static BigInteger uniform(BigInteger bottom, BigInteger top) {
		Random rand = new Random();
		BigInteger res;
		do {
			res = new BigInteger(top.bitLength(), rand);
		} while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
		rand = null;
		return res;
	}

}

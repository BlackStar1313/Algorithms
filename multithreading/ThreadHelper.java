package multithreading;

import java.math.BigInteger;
import java.util.Scanner;

public final class ThreadHelper {


	/**
	 * Takes from the standard input the number of threads.
	 * 
	 * @return	the number of threads if entered correctly.
	 */
	public static int threads(){

		Scanner scan = new Scanner(System.in);
		int nThread = 0;

		System.out.print("How many threads do you wish to create? ");
		do{
			while(!scan.hasNextInt()) {
				if(!scan.hasNextInt()) {
					System.err.println("It's not an integer!...Try again please!");
				}
				scan.next();
			}
			nThread = scan.nextInt();
			if(nThread < 0) {
				System.out.println("A negative number of threads is not feasible...Try again please!");
				System.out.print("number of threads : ");
			}
		}while(nThread < 0);

		System.out.println();
		scan.close();
		return nThread;
	}
	
	/**
	 * Takes from the standard input a big-integer.
	 * 
	 * @return	a big-integer if entered correctly.
	 */
	public static BigInteger input() {
		Scanner scan = new Scanner(System.in);
		BigInteger input = null;
		
		System.out.print("Please enter a natural number to check whether it is prime or not: ");
		
		while(!scan.hasNextBigInteger()) {
			if(!scan.hasNextBigInteger()) {
				System.err.println("It's not an natural number!...Try again please!");
			}
			scan.next();
		}
		input = scan.nextBigInteger();
		return input;
	}

}

package multithreading.matrixmultiplication;

import java.util.concurrent.CountDownLatch;

import StdLib.StopWatch;
import multithreading.ThreadHelper;

public final class ParallelMatrixMultiplication{

	/**
	 * The first matrix
	 */
	private Matrix A;

	/**
	 * The second matrix
	 */
	private Matrix B;

	/**
	 * The result matrix
	 */
	private Matrix C;

	/**
	 * This is the number of threads to be created.
	 */
	private int nbThread;

	/**
	 * A pool of threads for processing the results.
	 */
	private Worker[] workers;

	private CountDownLatch start;

	private CountDownLatch done;

	public ParallelMatrixMultiplication(Matrix firstMatrix, Matrix seconMatrix) {
		A = firstMatrix;
		B = seconMatrix;
		C = new Matrix(A.rows, B.cols);
		nbThread = ThreadHelper.threads();
		workers = new Worker[nbThread];
		start = new CountDownLatch(1);
		done = new CountDownLatch(nbThread);
	}

	public void startProcessing() {

		/**Assigning roles....**/
		int rowThread = 0, startRow = 0, chunkSize = (int) (Math.ceil(A.rows / (double)nbThread));;

		// initializing threads and distributing the work load and adding to the pool...
		for(int i = 0; i < nbThread - 1; i++) {
			workers[i] = new Worker(startRow, startRow + chunkSize, A, B, start, done);
			startRow += chunkSize;
		}
		workers[nbThread - 1] = new Worker(startRow, A.rows, A, B, start, done);

		// starting time...
		StopWatch.startTime();

		for(Worker worker : workers) {
			// running each thread...
			worker.start();
		}

		// informing all threads to start doing their job...
		start.countDown();

		// waiting for each thread to finish their job...
		try {
			done.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Execution Time: " + StopWatch.elapsedTime() + " seconds");
	}
	
	
	public void clear() {
		A = B = C = null;
		start = done = null;
		for(Worker worker : workers) {
			worker = null;
		}
		return;
	}


	private final class Worker extends Thread{

		/**
		 * The starting row
		 */
		private final int startRow;

		/**
		 * The stopping row
		 */
		private final int stopRow;

		/**
		 * for explicit synchronization between threads.
		 */
		private CountDownLatch startSignal, doneSignal;

		public Worker(int startRow, int stopRow, Matrix first, Matrix second, CountDownLatch startSignal, CountDownLatch doneSignal) {

			this.startRow = startRow;
			this.stopRow = stopRow;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		@Override
		public void run() {
			try {

				startSignal.await();

				if(!isMultiplicationPossible()){
					throw new IllegalStateException("The matrices are not the same dimension !");
				}

				for(int i = startRow; i < stopRow; i++){
					for(int j = 0; j < B.cols; j++){
						double sum = 0D;
						for(int k = 0; k < A.cols; k++){
							sum += A.get(i, k) * B.get(k, j);
						}
						C.set(i, j, sum);
					}
				}
				doneSignal.countDown();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Check whether the multiplication of two matrix is possible that is to say if the number of columns for a given matrix A
		 * is the same number of rows of a given matrix B.
		 * @param B		the other matrix to check to
		 * @return		true if the multiplication is doable, otherwise false.
		 */
		public boolean isMultiplicationPossible() {
			if(A == B) {
				return true;
			}else if(B == null) {
				return false;
			}

			return (this != null && A.cols == B.rows);
		}
	}
}

package multithreading.matrixmultiplication;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public final class Matrix {

	private final double arr[][];
	public final int rows, cols;

	public Matrix(int rows, int cols) {
		if(rows < 1 || cols < 1) {
			throw new IndexOutOfBoundsException("You cannot create a matrix smaller than 1 X 1");
		}
		this.rows = rows;
		this.cols = cols;
		arr = new double[this.rows][this.cols];
	}
	
	/**
	 * Here we fill an empty matrix with random number between the minimum number(inclusive) and the max number(exclusive).
	 * @param min	the minimum number
	 * @param max	the maximum number
	 * @return
	 */
	public Matrix fill(double min, double max){
		Matrix fill = this;

		for(int i = 0; i < fill.rows; i++){
			for(int j = 0; j < fill.cols; j++){
				fill.arr[i][j] = ThreadLocalRandom.current().nextDouble(min, max);
			}
		}
		return fill;
	}
	
	public void set(int i, int j, double value) {
		arr[i][j] = value;
	}

	public double get(int i, int j) {
		return arr[i][j];
	}
}

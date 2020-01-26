package main;

import algorithm.RpnEvaluationAlgorithm;
import algorithm.ShuntingYardAlgorithm;
import enums.OperatorType;
import helper.GenericHelper;
import multithreading.ParallelMergeSortTester;
import multithreading.matrixmultiplication.Matrix;
import multithreading.matrixmultiplication.ParallelMatrixMultiplication;
import sorting.Quick;
import sorting.Selection;
import sorting.Shell;
import structures.*;

public class Main{

	public static void main(String[] args){

		//new Calculator(new RpnEvaluationAlgorithm(), null);

		Matrix m1 = new Matrix(4000, 4000);
		Matrix m2 = new Matrix(4000, 4000);

		m1.fill(10, 20);
		m2.fill(10, 20);

		ParallelMatrixMultiplication pl = new ParallelMatrixMultiplication(m1, m2);
		pl.startProcessing();
	}
}

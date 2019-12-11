package main;

import algorithm.RpnEvaluationAlgorithm;
import algorithm.ShuntingYardAlgorithm;
import enums.OperatorType;
import helper.GenericHelper;
import multithreading.ParallelMergeSortTester;
import sorting.Quick;
import sorting.Selection;
import sorting.Shell;
import structures.*;

public class Main{

	public static void main(String[] args){
		new Calculator(new RpnEvaluationAlgorithm(), null);
	}
}

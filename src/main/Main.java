package main;

import helper.GenericHelper;
import multithreading.ParallelMergeSortTester;
import sorting.Quick;
import sorting.Selection;
import sorting.Shell;
import structures.LinearProbing;
import structures.MaxPriorityQueue;
import structures.MinPriorityQueue;
import structures.Stack;

public class Main{

	public static void main(String[] args){
		
		//ParallelMergeSortTester.process();
		
		/*Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(-1);
		stack.push(3);
		stack.push(2);
		System.out.println(stack + "\t and the mid is " + stack.min());
		System.out.println("verifying the stack is not altered... " + stack);
		stack = null;*/
		/*Integer arr [] = {64, 25, 12, 22, 113, 2, 1, 7, 5, 6, 8, 4, 11, 10, 16, 12};
		
		System.out.println("before sorting...");
		GenericHelper.print1DArray(arr);
		System.out.println();
		Selection.sort(arr);
		System.out.println("after sorting...");
		GenericHelper.print1DArray(arr);*/		

		/*System.out.print("Original Array : ");
		GenericHelper.print1DArray(arr);
		System.out.println("\n");

		MaxPriorityQueue<Integer> maxQueue = new MaxPriorityQueue<>(arr);
		System.out.print("Max-Priority Queue : ");
		System.out.println(maxQueue);

		//testing the iterator
		System.out.print("Testing the iterator that removes the max element : ");
		for(Integer key : maxQueue) {
			System.out.print(key + " ");
		}

		System.out.print("\n" + "Checking whether the queue is not altered : ");
		System.out.println(maxQueue);

		maxQueue.clear();
		maxQueue = null;

		System.out.println();


		MinPriorityQueue<Integer> minQueue = new MinPriorityQueue<>(arr);
		System.out.print("Min-Priority Queue : ");
		System.out.println(minQueue);

		//testing the iterator
		System.out.print("Testing the iterator that removes the min element : ");
		for(Integer key : minQueue) {
			System.out.print(key + " ");
		}

		System.out.print("\n" + "Checking whether the queue is not altered : ");
		System.out.println(minQueue);
		
		minQueue.clear();
		minQueue = null;*/

		/*Integer arr1 [] = {10, 15, 17, 20};
		Integer arr2 [] = {5, 7, 11, 19, 22};
		Integer arr3 [] = {1, 8, 12, 23, 26, 78};

		System.out.print("the first sorted array : ");
		GenericHelper.print1DArray(arr1);
		System.out.println();

		System.out.print("the second sorted array : ");
		GenericHelper.print1DArray(arr2);
		System.out.println();

		System.out.print("the third sorted array : ");
		GenericHelper.print1DArray(arr3);
		System.out.println();

		System.out.println("merging the 2-sorted arrays...");
		Comparable[] result = GenericHelper.mergeArrays(arr1, arr2, arr3);
		GenericHelper.print1DArray(result);*/
	}
}

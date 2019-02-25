package sorting;

import java.util.HashMap;

import helper.GenericHelper;

public final class Quick {

	/**
	 * the minimum size of an array.
	 */
	private static final int INSERTIONSORT_THRESHOLD = 6;

	public static <T extends Comparable<? super T>> void sort(T[] a) {
		quicksort3Way(a, 0, a.length - 1);
	}

	/**
	 * 
	 * @param array
	 */
	private static <T extends Comparable<? super T>> void quicksort(T[] array, int startIndex, int endIndex) {

		if(endIndex<=startIndex) {
			return;
			
			//If the array's size is smaller than limit
			//then do the insertion sort.
		}else if((endIndex - startIndex) < INSERTIONSORT_THRESHOLD) {

			Insertion.optimizedSort(array, startIndex, endIndex);
		}else {
			int p = LomutoPartition(array, startIndex, endIndex);
			quicksort(array, startIndex, p-1);
			quicksort(array, p+1, endIndex);
		}
	}

	/**
	 * Sort the sub-array a[startIndex...endIndex] using 3-way partitioning
	 * @param a				the sub-array to be sorted
	 * @param startIndex	the first index of this sub-array.
	 * @param endIndex		the last index of this sub-array.
	 */
	private static <T extends Comparable<? super T>> void quicksort3Way(T[] a, int startIndex, int endIndex) {
		if (endIndex <= startIndex) {
			return;
		}else {
			int lt = startIndex;
			int gt = endIndex;
			T pivot = a[startIndex];
			int i = startIndex;
			while (i <= gt) {
				int cmp = a[i].compareTo(pivot);
				if(cmp < 0) {
					GenericHelper.swap(a, lt++, i++);
				}else if (cmp > 0) {
					GenericHelper.swap(a, i, gt--);
				}else {
					i++;
				}
			}
			System.out.println(pivot);
			quicksort3Way(a, startIndex, lt-1);
			quicksort3Way(a, gt+1, endIndex);
		}
	}

	/**
	 * Partition the sub-array a[startIndex...endIndex] using the Hoare Partition Scheme, 
	 * so that a [startIndex..p], and a[p+1..endIndex].</br>
	 * @see <a href="https://en.wikipedia.org/wiki/Quicksort#Hoare_partition_scheme">Hoare Partition Scheme</a>
	 * @param a				the array to partition to.
	 * @param startIndex	the first index in this sub-array.
	 * @param endIndex		the last index in this sub-array.
	 * @return				an index j.
	 */
	private static<T extends Comparable<? super T>> int HoarePartition(T[] a, int startIndex, int endIndex) {

		//Partitioning into a[startIndex..p], and a[p+1..endIndex].
		int i = startIndex - 1;
		int j = endIndex + 1;

		T pivot = a[startIndex + (endIndex - startIndex) / 2];

		while(true) {

			//find item on startIndex to swap.
			do {
				i++;
			}while(GenericHelper.isLess(a[i], pivot));

			//find item on endIndex to swap.
			do {
				j--;
			}while(GenericHelper.isGreater(a[j], pivot));

			//check whether pointer cross.
			if(i >= j) {
				return j;
			}

			GenericHelper.swap(a, i, j);
		}
	}
	
	/**
	 * Partition the sub-array a[startIndex...endIndex] using the Lomuto Partition Scheme, 
	 * so that a [startIndex..p-1], and a[p+1..endIndex]
	 * @param a				the array to partition to.
	 * @param startIndex	the first index in this sub-array.
	 * @param endIndex		the last index in this sub-array.
	 * @see <a href="https://en.wikipedia.org/wiki/Quicksort#Lomuto_partition_scheme">Lomuto Partition Scheme</a>
	 * @return				an index i.
	 */
	private static<T extends Comparable<? super T>> int LomutoPartition(T[] a, int startIndex, int endIndex) {
		
		T pivot = a[endIndex];
		int i = startIndex;
		
		for(int j = startIndex; j < endIndex - 1; j++) {
			if(GenericHelper.isLess(a[j], pivot)) {
				GenericHelper.swap(a, i++, j);
			}
		}
		GenericHelper.swap(a, i, endIndex);
		return i;
	}
}

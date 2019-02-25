package sorting;

import helper.GenericHelper;

public class Selection{

	public static <T extends Comparable<? super T>> void sort(T[] array) {
		
		/* a[0] to a[n-1] is the array to sort */
		int i,j;

		int N = array.length; // initialize to array's length

		/* advance the position through the entire array */
		/* (could do j < n-1 because single element is also min element) */
		for (j = 0; j < N-1; j++){

		    /* find the min element in the unsorted a[j .. n-1] */
		    /* assume the min is the first element */
		    int iMin = j;

		    /* test against elements after j to find the smallest */
		    for (i = j+1; i < N; i++){
		        /* if this element is less, then it is the new minimum */
		        if (GenericHelper.isLess(array[i], array[iMin])){
		            /* found new minimum; remember its index */
		            iMin = i;
		        }
		    }

		    if (iMin != j){
		        GenericHelper.swap(array, j, iMin);
		    }
		    
		    assert GenericHelper.isSorted(array, 0, j);
		}
		
		assert  GenericHelper.isSorted(array);
		
	}
}

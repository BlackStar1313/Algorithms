package sorting;

import java.util.ArrayList;
import java.util.Collections;

import helper.GenericHelper;

public class Shell {

	public static <T extends Comparable<? super T>> void sort(T[] array) {
		int N = array.length;
		ArrayList<Integer> gaps = new ArrayList<>();

		// 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
		int h = 1;
		gaps.add(h);
		while (h < N/3) {
			h = 3*h + 1;
			gaps.add(h);
		}
		
		Collections.reverse(gaps);

		//Start with the largest gap and work down to a gap of 1
		for(int gap : gaps) {
			//Do a gapped insertion sort for this gap size.
			//The first gap elements a[0..gap-1] are already in gapped order
			//keep adding one more element until the entire array is gap sorted
			for(int i = gap; i < N; i++) {
				//shift earlier gap-sorted elements up until the correct location for a[i] is found
				for(int j = i; j >= gap && GenericHelper.isLess(array[j], array[j-gap]); j-=gap) {
					System.out.println(j);
					GenericHelper.swap(array, j, j-gap);
				}
			}
			assert GenericHelper.isSorted(array, gap);
		}
		assert GenericHelper.isSorted(array);
		return;
	}


	public static <T extends Comparable<? super T>> void optimizedSort(T[] array) {
		int N = array.length;
		ArrayList<Integer> gaps = new ArrayList<>();

		// 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
		int h = 1;
		while (h < N/3) {
			h = 3*h + 1;
			gaps.add(h);
		}
		gaps.add(1);

		//Start with the largest gap and work down to a gap of 1
		for(int gap : gaps) {

			//Do a gapped insertion sort for this gap size.
			//The first gap elements a[0..gap-1] are already in gapped order
			//keep adding one more element until the entire array is gap sorted
			for(int i = gap; i < N; i++) {

				//add a[i] to the elements that have been gap sorted
				//save a[i] in temp and make a hole at position i
				T temp = array[i];

				//shift earlier gap-sorted elements up until the correct location for a[i] is found
				int j = i;
				while(j >= gap && GenericHelper.isLess(temp, array[j-gap])) {
					array[j] = array[j-gap];;
					j-=gap;
				}

				//put temp (the original a[i]) in its correct location
				array[j] = temp;
			}
			assert GenericHelper.isSorted(array, gap);
		}
		assert GenericHelper.isSorted(array);

		return;
	}
}

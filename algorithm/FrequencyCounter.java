package algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import StdLib.ReadIn;
import structures.BinarySearchTree;
import structures.Entry;
import structures.RedBlackTree;
import structures.SymbolTable;

public class FrequencyCounter {

	/**
	 * Minimal length of a given word.
	 */
	private static final int MIN_LENGTH = 2;


	/**
	 * Compute the top K most frequent words with their respective frequencies within a file
	 * @param filename	the file to look for frequency.
	 * @param k			the first k frequent words.
	 * @return			the k most frequent words with their frequencies.
	 */
	public static Entry<String, Integer>[] countFrequentWords(String filename, int k) {
		String[] words = ReadIn.readStrings(filename);
		Entry<String, Integer> entries[] = RBTreeMostFrequentWords(k, words);
		return entries;
	}

	/**
	 * Compute the top K most frequent words with their respective frequencies from the standard input.
	 * @param k			the first k frequent words.
	 * @return			the k most frequent words with their frequencies.
	 */
	public static Entry<String, Integer>[] countFrequentWords(int k) {
		String[] words = ReadIn.readStrings();
		Entry<String, Integer> entries[] = BSTMostFrequentWords(k, words);
		return entries;
	}
	
	/**
	 * Count the <i>k</i> most frequent words with a red-black tree as its underlying structure.<br/>
	 * It works exactly like {@link #BSTMostFrequentWords(int, String...) BSTMostFrequentWords} below.
	 * @param k			the number of frequent words
	 * @param words		the array of string
	 * @return			the <i>k</i> most frequent words.
	 */
	private static Entry<String, Integer>[] RBTreeMostFrequentWords(int k, String...words){
		
		if(words != null && words.length > 0) {
			
			RedBlackTree<String, Integer> RBT1 = new RedBlackTree<>();
			for(String word : words) {
				if(word.length() > MIN_LENGTH) {
					Integer value = RBT1.get(word);
					RBT1.put(word, (value == null) ? 1 : value + 1);
				}
			}
			
			RedBlackTree<Integer, String> RBT2 = new RedBlackTree<>();
			for(String keyWord : RBT1) {
				Integer key = RBT1.get(keyWord);
				RBT2.put(key, keyWord);
			}
			
			
			Entry<String, Integer> entries[] = new Entry[k];
			
			for(int i = 0; i < entries.length; i++) {
				Integer freq = RBT2.max();
				String key = RBT2.get(freq);
				Entry<String, Integer> entry = new Entry<String, Integer>(key, freq);
				entries[i] = entry;
				entry = null;
				RBT2.deleteMax();
			}
			
			RBT1.clear();
			RBT2.clear();
			RBT1 = null;
			RBT2 = null;
			return entries;
		}
		
		throw new IllegalArgumentException("There are not any words !");
	}

	/**
	 * Count the <i>k</i> most frequent words with a binary search tree as its underlying structure.
	 * @param k			the number of frequent words
	 * @param words		the array of string
	 * @return			the <i>k</i> most frequent words.
	 */
	private static Entry<String, Integer>[] BSTMostFrequentWords(int k, String...words){

		if(words != null && words.length > 0) {

			// Using a Binary Search Tree to store the a given word and its occurrence.
			BinarySearchTree<String, Integer> BST1 = new BinarySearchTree<>();
			for(String word : words) {
				if(word.length() > MIN_LENGTH) {
					Integer value = BST1.get(word);
					BST1.put(word, (value == null) ? 1 : value + 1);
				}
			}

			//For better performance over searching for the n-th frequent word and inserting
			//one method would be to create one more BST but with frequency as its key and the word as its value.
			BinarySearchTree<Integer, String> BST2 = new BinarySearchTree<>();
			for(String keyWord : BST1) {
				Integer key = BST1.get(keyWord);
				BST2.put(key, keyWord);
			}

			Entry<Integer, String> array[] = BST2.InOrderArray();

			if(array.length >= k) {

				//Array of entries to store the top K frequent Swords with their respective frequencies.
				Entry<String, Integer> entries[] = new Entry[k];


				//iterating over the array
				for(int i = array.length - 1, counter = 0;(counter < k); i--, counter++) {
					Integer freq = array[i].getKey();
					String key = array[i].getValue();
					Entry<String, Integer> entry = new Entry<String, Integer>(key, freq);
					entries[counter] = entry;
					entry = null;
				}

				//un-referencing process...
				BST1.clear();
				BST2.clear();
				BST1 = null;
				BST2 = null;
				return entries;
				
			}else {
				throw new IllegalArgumentException("The number of frequent words exceeds the numbers of words!");
			}
		}

		throw new IllegalArgumentException("There are not any words !");
	}

	/**
	 * Count the <i>k</i> most frequent words with a symbol table as its underlying structure.
	 * @param k			the number of frequent words
	 * @param words		the array of string
	 * @return			the <i>k</i> most frequent words.
	 */
	private static Entry<String, Integer>[] STMostFrequentWords(int k, String...words){

		if(words != null && words.length > 0) {

			SymbolTable<String, Integer> St = new SymbolTable<>();
			int maxFrequency = 0;

			// Using a table to store the element(String) and its occurrences.
			for(String word : words) {
				if(word.length() > MIN_LENGTH) {
					if(St.containsKey(word)) {
						St.put(word, St.get(word) + 1);
					}else {
						St.put(word, 1);
					}

					maxFrequency = Math.max(maxFrequency, St.get(word));
				}
			}

			Iterator<String> it = St.iterator();

			// corner case: if there is only string in words, we need the bucket has index 1.
			List<String> bucket[] = new ArrayList[maxFrequency+1];

			//To be in O(n) complexity for sorting, the bucket sort is used here.
			//Iterate over the symbol table
			//the frequency of a given word will be treated as the index of the bucket.
			//And the given key as list of keys.
			while(it.hasNext()) {
				String key = it.next();
				Integer frequency = St.get(key);
				//Allocate space for keys if necessary
				if(bucket[frequency] == null) {
					bucket[frequency] = new ArrayList<>();
				}
				//Store keys at their respective index.
				bucket[frequency].add(key);
			}


			//Array of entries to store the top K frequent words with their respective frequencies.
			Entry<String, Integer> entries[] = new Entry[k];

			//iterate over the bucket elements and keep a counter to match with the input value k.
			for(int freq = bucket.length-1, counter = 0; freq >= 0; freq--) {
				if(bucket[freq] != null) {
					List<String> currentList = bucket[freq];
					for(String key : currentList) {
						if(counter < k) {
							Entry<String, Integer> entry = new Entry<String, Integer>(key, freq);
							entries[counter] = entry;
							entry = null;
							counter++;
						}else {
							return entries;
						}
					}
					bucket[freq].clear();
				}
			}

			//un-referencing process...
			St.clearAll();
			St = null;
			bucket = null;

			return entries;
		}

		throw new IllegalArgumentException("There are not any words !");
	}

}

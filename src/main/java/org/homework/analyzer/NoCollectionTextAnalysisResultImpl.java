package org.homework.analyzer;

import java.util.*;

/**
 * Implementation of text analysis that maintains lists of words sorted alphabetically, and uses binary search
 * to find or add words.
 */
class NoCollectionTextAnalysisResultImpl implements TextAnalysisResult {

	private static final int MAX_WORD_LENGTH = 1000;

	private WordCounts wordCounts = new WordCounts(0);

	NoCollectionTextAnalysisResultImpl() {
	}

	void addWord(String word) {
		// Retrieve word -> count map
		int wordLength = word.length();
		if (wordLength > MAX_WORD_LENGTH) {
			// Silently ignore words that are too long
			return;
		}
		int wordIndex = wordLength - 1;
		WordCountArray wordCountArray = wordCounts.expandingGet(wordIndex);
		if (wordCountArray == null) {
			wordCountArray = new WordCountArray(0);
			wordCounts.set(wordIndex, wordCountArray);
		}
		wordCountArray.incrementWordCount(word);
	}

	@Override
	public Collection<Integer> wordLengths() {
		// Collect indexes of non-null entries
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < wordCounts.length(); i++) {
			if (wordCounts.get(i) != null) {
				result.add(i + 1);
			}
		}
		return result;
	}

	@Override
	public Collection<WordCount> wordCounts(int length) {
		WordCountArray wordCountArray = wordCounts.expandingGet(length - 1);
		if (wordCountArray == null) {
			return Collections.emptyList();
		} else {
			return Arrays.asList(wordCountArray.trim());
		}
	}

	/**
	 * Root class of expandable arrays.
	 */
	private static abstract class ExpandableArray<T> {

		protected T[] array;
		protected int length = 0;

		private static double EXPANSION_FACTOR = 5;

		public ExpandableArray(int initialLength) {
			this.array = newArray(initialLength);
			this.length = initialLength;
		}

		T get(int index) {
			checkIndex(index);
			return array[index];
		}

		T expandingGet(int index) {
			if (index < length) {
				return array[index];
			}
			T[] newArray = enlargedArray(index);
			System.arraycopy(array, 0, newArray, 0, length);
			array = newArray;
			length = index + 1;
			return null;
		}

		void set(int index, T element) {
			checkIndex(index);
			array[index] = element;
		}

		int length() {
			return length;
		}

		T[] trim() {
			if (length == array.length) {
				return array;
			} else {
				T[] result = newArray(length);
				System.arraycopy(array, 0, result, 0, length);
				return result;
			}
		}

		private void checkIndex(int index) {
			if ((index < 0) || (index > length)) {
				throw new IllegalArgumentException("out of bounds: " + index);
			}
		}

		private T[] enlargedArray(int index) {
			int newLength = Math.max(index + 1, (int) (length * EXPANSION_FACTOR));
			return newArray(newLength);
		}

		void insertAt(int index, T element) {
			if (index >= length + 1) {
				throw new IllegalArgumentException("bad insert: " + index);
			}
			if (length < array.length) {
				// There's still room left in the array for one more element.
				System.arraycopy(array, index, array, index + 1, length - index);
			} else {
				// Make room for the inserted element.
				T[] newArray = enlargedArray(length);
				System.arraycopy(array, 0, newArray, 0, index);
				if (index < length) {
					System.arraycopy(array, index, newArray, index + 1, length - index);
				}
				array = newArray;
			}
			array[index] = element;
			length = length + 1;
		}

		protected abstract T[] newArray(int length);

	}

	/**
	 * Expandable array, indexed by word length. Null value at index means that no words with length index + 1 has
	 * been found. Otherwise, contains a WordCountArray representing the count for words of length index + 1.
	 */
	private static class WordCounts extends ExpandableArray<WordCountArray> {

		public WordCounts(int initialLength) {
			super(initialLength);
		}

		@Override
		protected WordCountArray[] newArray(int length) {
			return new WordCountArray[length];
		}

	}

	/**
	 * Expandable array of words, sorted alphabetically.
	 */
	private static class WordCountArray extends ExpandableArray<WordCount> {

		public WordCountArray(int initialLength) {
			super(initialLength);
		}

		@Override
		protected WordCount[] newArray(int length) {
			return new WordCount[length];
		}

		/**
		 * Use binary search to find or insert a word, and increment its count.
		 */
		void incrementWordCount(String word) {
			// Binary search for the word (copied and adapted from Arrays.binarySearch)
			int low = 0;
			int high = length - 1;
			while (low <= high) {
				int mid = (low + high) >>> 1;
				WordCount midWord = array[mid];
				int comparison = midWord.getWord().compareTo(word);
				if (comparison < 0) {
					low = mid + 1;
				} else if (comparison > 0) {
					high = mid - 1;
				} else {
					// Word found
					midWord.increment();
					return;
				}
			}
			// Word not found. Insert it.
			insertAt(low, new WordCount(word, 1));
		}

	}

}

package org.homework.analyzer;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the result of processing text to analyze word contents.
 *
 * @see TextAnalyzer
 */
public class TextAnalysisResult {

	/**
	 * Maps word length -> word -> word count.
	 * Word length and words are sorted in natural order.
	 */
	private TreeMap<Integer, TreeMap<String, WordCount>> lengthMap = new TreeMap<>(Comparator.naturalOrder());

	public TextAnalysisResult() {
	}

	void addWord(String word) {
		// Retrieve word -> count map
		int wordLength = word.length();
		Map<String, WordCount> wordMap = lengthMap.computeIfAbsent(wordLength,
				l -> new TreeMap<>(Comparator.naturalOrder())
		);
		// Increment word count
		WordCount wordCount = wordMap.computeIfAbsent(word,
				w -> new WordCount(w, 0)
		);
		wordCount.increment();
	}

	/**
	 * @return read-only collection of word length sorted by increasing order
	 */
	public Collection<Integer> wordLengths() {
		// The keyset is ordered since this is a TreeMap
		return lengthMap.keySet();
	}

	/**
	 * @return read-only collection of word counts, where the words are sorted alphabetically
	 */
	public Collection<WordCount> wordCounts(int length) {
		return lengthMap.getOrDefault(length, new TreeMap<>()).values();
	}

	/**
	 * Generates a simple report for the analysis result. Sorted by word length then alphabetic order.
	 *
	 * @param output stream to print the report to
	 */
	public void report(PrintStream output) {
		for (int wordLength : wordLengths()) {
			for (WordCount wordCount : wordCounts(wordLength)) {
				output.println(wordCount.getCount() + " " + wordCount.getWord());
			}
		}
	}

}

package org.homework.analyzer;

import java.io.PrintStream;
import java.util.Collection;

/**
 * Represents the result of processing text to analyze word contents.
 *
 * @see TextAnalyzer
 */
public interface TextAnalysisResult {

	/**
	 * @return read-only collection of word length sorted by increasing order
	 */
	Collection<Integer> wordLengths();

	/**
	 * @return read-only collection of word counts, where the words are sorted alphabetically
	 */
	Collection<WordCount> wordCounts(int length);

	/**
	 * Generates a simple report for the analysis result. Sorted by word length then alphabetic order.
	 *
	 * @param output stream to print the report to
	 */
	default void report(PrintStream output) {
		for (int wordLength : wordLengths()) {
			for (WordCount wordCount : wordCounts(wordLength)) {
				output.println(wordCount.getCount() + " " + wordCount.getWord());
			}
		}
	}


}

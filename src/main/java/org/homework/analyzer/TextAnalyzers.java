package org.homework.analyzer;

/**
 * Class for building text analyzers.
 */
public class TextAnalyzers {

	/**
	 * @return a text analyzer whose implementation uses Java collections
	 */
	public static TextAnalyzer makeUseCollections() {
		return new CollectionTextAnalyzerImpl();
	}

	/**
	 * @return a text analyzer whose implementation doesn't use Java collections
	 */
	public static TextAnalyzer makeDontUseCollections() {
		return new NoCollectionTextAnalyzerImpl();
	}

}

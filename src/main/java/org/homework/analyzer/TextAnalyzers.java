package org.homework.analyzer;

/**
 * Class for building text analyzers.
 */
public class TextAnalyzers {

	public static TextAnalyzer makeUseCollections() {
		return new CollectionTextAnalyzerImpl();
	}

	public static TextAnalyzer makeDontUseCollections() {
		return new NoCollectionTextAnalyzerImpl();
	}

}

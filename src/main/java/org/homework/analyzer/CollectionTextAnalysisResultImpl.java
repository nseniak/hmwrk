package org.homework.analyzer;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

class CollectionTextAnalysisResultImpl implements TextAnalysisResult {

	/**
	 * Maps word length -> word -> word count.
	 * Word length and words are sorted in natural order.
	 */
	private TreeMap<Integer, TreeMap<String, WordCount>> lengthMap = new TreeMap<>(Comparator.naturalOrder());

	CollectionTextAnalysisResultImpl() {
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

	@Override
	public Collection<Integer> wordLengths() {
		// The keyset is ordered since this is a TreeMap
		return lengthMap.keySet();
	}

	@Override
	public Collection<WordCount> wordCounts(int length) {
		return lengthMap.getOrDefault(length, new TreeMap<>()).values();
	}

}

package org.homework.analyzer;

import java.io.Reader;
import java.util.Scanner;

/**
 * The text analyzer class processes text and provides information about its word content.
 */
class CollectionTextAnalyzerImpl implements TextAnalyzer {

	CollectionTextAnalyzerImpl() {
	}

	@Override
	public TextAnalysisResult analyze(Reader reader) {
		return analyze(new Scanner(reader));
	}

	@Override
	public TextAnalysisResult analyze(String input) {
		return analyze(new Scanner(input));
	}

	private CollectionTextAnalysisResultImpl analyze(Scanner scanner) {
		CollectionTextAnalysisResultImpl result = new CollectionTextAnalysisResultImpl();
		while (scanner.hasNext()) {
			String word = scanner.next();
			result.addWord(word);
		}
		return result;
	}

}

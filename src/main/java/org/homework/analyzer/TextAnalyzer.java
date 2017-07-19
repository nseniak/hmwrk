package org.homework.analyzer;

import java.io.Reader;
import java.util.Scanner;

/**
 * The text analyzer class processes text and provides information about its word content.
 */
public class TextAnalyzer {

	/**
	 * Constructs a text analyzer.
	 */
	public TextAnalyzer() {
	}

	/**
	 * Analyzes text read from a reader.
	 *
	 * @return analysis result
	 */
	public TextAnalysisResult analyze(Reader reader) {
		return analyze(new Scanner(reader));
	}

	/**
	 * Analyzes a string.
	 *
	 * @return analysis result
	 */
	public TextAnalysisResult analyze(String input) {
		return analyze(new Scanner(input));
	}

	private TextAnalysisResult analyze(Scanner scanner) {
		TextAnalysisResult result = new TextAnalysisResult();
		while (scanner.hasNext()) {
			String word = scanner.next();
			result.addWord(word);
		}
		return result;
	}

}

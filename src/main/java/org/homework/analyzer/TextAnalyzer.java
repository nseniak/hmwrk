package org.homework.analyzer;

import java.io.Reader;

/**
 * The text analyzer class processes text and provides information about its word content.
 */
interface TextAnalyzer {

	/**
	 * Analyzes text read from a reader.
	 *
	 * @return analysis result
	 */
	TextAnalysisResult analyze(Reader reader);

	/**
	 * Analyzes a string.
	 *
	 * @return analysis result
	 */
	TextAnalysisResult analyze(String input);

}

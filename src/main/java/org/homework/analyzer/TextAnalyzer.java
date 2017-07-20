package org.homework.analyzer;

import java.io.Reader;

/**
 * Processes text and provides information about its word content.
 */
public interface TextAnalyzer {

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

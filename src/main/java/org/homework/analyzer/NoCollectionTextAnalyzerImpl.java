package org.homework.analyzer;

import java.io.Reader;
import java.util.Scanner;

class NoCollectionTextAnalyzerImpl implements TextAnalyzer {

	NoCollectionTextAnalyzerImpl() {
	}

	@Override
	public TextAnalysisResult analyze(Reader reader) {
		return analyze(new Scanner(reader));
	}

	@Override
	public TextAnalysisResult analyze(String input) {
		return analyze(new Scanner(input));
	}

	private NoCollectionTextAnalysisResultImpl analyze(Scanner scanner) {
		NoCollectionTextAnalysisResultImpl result = new NoCollectionTextAnalysisResultImpl();
		while (scanner.hasNext()) {
			String word = scanner.next();
			result.addWord(word);
		}
		return result;
	}

}

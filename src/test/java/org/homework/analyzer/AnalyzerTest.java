package org.homework.analyzer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnalyzerTest {

	@Test
	public void test() {
		testImpl(TextAnalyzers.makeUseCollections());
		testImpl(TextAnalyzers.makeDontUseCollections());
	}

	private void testImpl(TextAnalyzer analyzer) {
		TextAnalysisResult result = analyzer.analyze("The quick brown fox jumped over the lazy brown dog's back");
		assertThat(result.wordLengths(), contains(3, 4, 5, 6));
		assertThat(result.wordCounts(3), contains(
				new WordCount("The", 1),
				new WordCount("fox", 1),
				new WordCount("the", 1)
		));
		assertThat(result.wordCounts(4), contains(
				new WordCount("back", 1),
				new WordCount("lazy", 1),
				new WordCount("over", 1)
		));
		assertThat(result.wordCounts(5), contains(
				new WordCount("brown", 2),
				new WordCount("dog's", 1),
				new WordCount("quick", 1)
		));
		assertThat(result.wordCounts(6), contains(
				new WordCount("jumped", 1)
		));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		result.report(printStream);
		String report = outputStream.toString();
		assertThat(report, is(
				"1 The\n"
						+ "1 fox\n"
						+ "1 the\n"
						+ "1 back\n"
						+ "1 lazy\n"
						+ "1 over\n"
						+ "2 brown\n"
						+ "1 dog's\n"
						+ "1 quick\n"
						+ "1 jumped\n"));
	}

	@Test
	public void testEmpty() {
		testEmptyImpl(TextAnalyzers.makeUseCollections());
		testEmptyImpl(TextAnalyzers.makeDontUseCollections());
	}

	private void testEmptyImpl(TextAnalyzer analyzer) {
		assertThat(analyzer.analyze("").wordLengths(), empty());
		assertThat(analyzer.analyze("  \n  ").wordLengths(), empty());
	}

	@Test
	public void testMultipleSpaces() {
		testMultipleSpacesImpl(TextAnalyzers.makeUseCollections());
		testMultipleSpacesImpl(TextAnalyzers.makeDontUseCollections());
	}

	private void testMultipleSpacesImpl(TextAnalyzer analyzer) {
		TextAnalysisResult result = analyzer.analyze("  The  fox\n  \n jumped  \n");
		assertThat(result.wordLengths(), contains(3, 6));
		assertThat(result.wordCounts(3), contains(
				new WordCount("The", 1),
				new WordCount("fox", 1)
		));
		assertThat(result.wordCounts(6), contains(
				new WordCount("jumped", 1)
		));
	}

	@Test
	public void testReader() {
		testReaderimpl(TextAnalyzers.makeUseCollections());
		testReaderimpl(TextAnalyzers.makeDontUseCollections());
	}

	private void testReaderimpl(TextAnalyzer analyzer) {
		Reader reader = new StringReader("jumped the");
		TextAnalysisResult result = analyzer.analyze(reader);
		assertThat(result.wordLengths(), contains(3, 6));
	}

	@Test
	public void testDiacritics() {
		testDiacriticsImpl(TextAnalyzers.makeUseCollections());
		testDiacriticsImpl(TextAnalyzers.makeDontUseCollections());
	}

	private void testDiacriticsImpl(TextAnalyzer analyzer) {
		TextAnalysisResult result = analyzer.analyze("hélô à toût çon toût");
		assertThat(result.wordLengths(), contains(1, 3, 4));
		assertThat(result.wordCounts(1), contains(
				new WordCount("à", 1)
		));
		assertThat(result.wordCounts(3), contains(
				new WordCount("çon", 1)
		));
		assertThat(result.wordCounts(4), contains(
				new WordCount("hélô", 1),
				new WordCount("toût", 2)
		));
	}

}

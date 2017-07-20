package org.homework.analyzer;

import java.io.*;

public class PerformanceTestMain {

	public static void main(String[] args) {
		// Warmup
		perfTest(true);
		// Real test
		perfTest(false);
	}

	private static void perfTest(boolean warmup) {
		PrintStream output = warmup ? new PrintStream(new NullOutputStream()) : System.out;
		perfTestImpl("Using collections", TextAnalyzers.makeUseCollections(), output);
		perfTestImpl("NOT using collections", TextAnalyzers.makeDontUseCollections(), output);
	}

	private static void perfTestImpl(String message, TextAnalyzer analyzer, PrintStream output) {
		output.println("========== " + message);
		long start = System.currentTimeMillis();
		Reader mobyDickReader = new InputStreamReader(PerformanceTestMain.class.getResourceAsStream("/moby-dick.txt"));
		TextAnalysisResult result = analyzer.analyze(mobyDickReader);
		long end = System.currentTimeMillis();
		output.println("Analyzed Moby Dick in " + (end - start) + "ms");
		result.report(output);
	}

	/**
	 * Null output stream, for warmup
	 */
	static class NullOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
		}

	}

}

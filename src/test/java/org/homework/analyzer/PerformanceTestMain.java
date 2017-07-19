package org.homework.analyzer;

import java.io.InputStreamReader;
import java.io.Reader;

public class PerformanceTestMain {

	public static void main(String[] args) {
		TextAnalyzer analyzer = new TextAnalyzer();
		long start = System.currentTimeMillis();
		Reader mobyDickReader = new InputStreamReader(PerformanceTestMain.class.getResourceAsStream("/moby-dick.txt"));
		TextAnalysisResult result = analyzer.analyze(mobyDickReader);
		long end = System.currentTimeMillis();
		System.out.println("Analyzed Moby Dick in " + (end - start) + "ms");
		result.report(System.out);
	}

}

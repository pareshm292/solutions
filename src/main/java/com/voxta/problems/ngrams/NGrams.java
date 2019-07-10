package com.voxta.problems.ngrams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NGrams {

	public static void main(String[] args) throws IOException {

		String text =	Files.lines(Paths.get(new File("src/main/resources/war-of-the-worlds.txt").toURI()))
				.filter(line -> !line.isEmpty())
				.collect(Collectors.joining(" "));

		/*
		 * Map<String,Long> map = Arrays.stream(text.split("[.]")) .peek(line
		 * ->System.out.println(line)) .map(str -> str.trim()) .flatMap(str ->
		 * Arrays.stream(str.split(" "))) .map(str -> str.toLowerCase()) //.map(s ->
		 * s.replaceAll("[\\.\\_\\?\\(\\)\\']", "")) .filter(s -> !s.isEmpty())
		 * .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		 * //.forEach(s -> System.out.print(s + " "));
		 * System.out.println(map.entrySet().size());
		 * System.out.println(map.entrySet().stream().sorted(Entry.comparingByValue()).
		 * collect(Collectors.toMap(Entry::getKey , Entry::getValue)));
		 */

		System.out.println("Sentences in this text are : " + countSentences(text));

		characterCount(text);

		averageSentenceLength(text);

		numberOfSentenceHavingNumbers(text);

		meanWordFreqAndStandardDeviation(text);
		
		
	}

	private static void meanWordFreqAndStandardDeviation(String text) {
			
		Map<String,Long> wordFreq = Arrays.stream(text.split("[\\.\\?\\!]"))
				.flatMap(sentence -> Arrays.stream(sentence.split(" ")))
				.filter(word -> !word.isEmpty())
				.map(String::toLowerCase)
				.collect(Collectors.groupingBy(Function.identity() , Collectors.counting()));
		
	//	System.out.println(wordFreq);
		
		double average = wordFreq.values().stream().collect(Collectors.averagingDouble(d -> d));
		
		System.out.println("Mean word frequency : " + average);
		
		Double variance = 0.0;
		for (Long frequency : wordFreq.values()) {
			//System.out.println("Frequency : " + frequency);
			variance += Math.pow((frequency - average), 2);
		}
		
		variance = variance / wordFreq.values().size();
		
		Double stdDev = Math.sqrt(variance);
		
		System.out.println("Standard Deviation : " + stdDev);
	
	}

	private static void numberOfSentenceHavingNumbers(String text) {
			
		Long count = Arrays.stream(text.split("[\\.\\?\\!]"))
				.filter(NGrams::containsNumber)
				//.peek(System.out::println)
				.count();
		
		System.out.println("Number of sentences with numbers : " + count);
	}

	private static boolean containsNumber(String str) {
		
		for(String s : str.split("")) {
			if(s.matches("[0-9]+")) return true;
		}
		return false;
	}

	private static void averageSentenceLength(String text) {

		double average = Arrays.stream(text.split("[\\.\\?\\!]"))
				.map(String::length)
				.collect(Collectors.averagingInt(i -> i));

		System.out.println("Average sentence length : " + average + " characters");
	}

	private static void characterCount(String text) {

		Map<String,Long> map = Arrays.stream(text.split(""))
				.filter(s -> !s.isEmpty())
				.filter(s -> s.matches("[a-z0-9]"))
				.map(s -> s.toLowerCase())
				.collect(Collectors.groupingBy(Function.identity() , Collectors.counting()));

		map.entrySet()
			.forEach(entry -> System.out.println("Character : " +  entry.getKey() + " Frequency : " + entry.getValue()));

	}

	private static int countSentences(String text) {

		String[] sentences = text.split("[\\?\\.\\!]");
		Set<String> uniqueSentences = new HashSet<>();
		for (String string : sentences) {
			uniqueSentences.add(string);
		}
		return uniqueSentences.size();
	}

}

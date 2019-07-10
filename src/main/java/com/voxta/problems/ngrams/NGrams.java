package com.voxta.problems.ngrams;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NGrams {

	public static void main(String[] args) throws IOException, URISyntaxException {

		String filename = args[0];
	//	String text = Files.lines(Paths.get(NGrams.class.getClassLoader().getResource("war-of-the-worlds.txt").toURI()))
		String text =	Files.lines(Paths.get(new File(filename + ".txt").toURI()))
				.filter(line -> !line.isEmpty())
				.collect(Collectors.joining(" "));
		
		FileWriter writer = new FileWriter(new File(filename + "-output.txt"));
		//writer.append("abc");
		
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

		Integer n = 3;
		
		ngrams(text,n);
		
		System.out.println("Sentences in this text are : " + countSentences(text));
		
		characterCount(text);

		averageSentenceLength(text);

		numberOfSentenceHavingNumbers(text);

		meanWordFreqAndStandardDeviation(text,writer);
		
		//writer.flush();
		writer.close();
	}

	private static void ngrams(String text,Integer n) {
		
		Arrays.stream(text.split("[\\.\\?\\!]"))
			.map(sentence -> sentence.trim())
			.forEach(sentence -> printNGrams(sentence,n));
	}

	private static void printNGrams(String sentence,Integer n) {
		
		System.out.println("For sentence :: " + sentence);
		
		String[] words = sentence.split(" ");
		
		for(int i = 1 ; i <= n ; i++) {
			
			printNthGram(words , i);
		
		}
	}

	private static void printNthGram(String[] words , int i) {
		
		List<String> result = new ArrayList<>();
		
		for(int j = 0 ; j < words.length - i + 1 ; j++) {
			StringBuilder sb = new StringBuilder();
			for(int k = 0 ; k < i ; k++) {
				if(k > 0) sb.append(" ");
				sb.append(words[j+k]);
			}
			result.add(sb.toString());
		}
		System.out.println(i + " gram " + result);
		
	}

	private static void meanWordFreqAndStandardDeviation(String text, FileWriter writer ) throws IOException {
			
		Map<String,Long> wordFreq = Arrays.stream(text.split("[\\.\\?\\!]"))
				.flatMap(sentence -> Arrays.stream(sentence.split(" ")))
				.filter(word -> !word.isEmpty())
				.map(String::toLowerCase)
				.collect(Collectors.groupingBy(Function.identity() , Collectors.counting()));
		
		
		Map<String,Long> sortedMap = wordFreq.entrySet()
							.stream()
							.sorted(Collections.reverseOrder(Entry.comparingByValue()))
							.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1,e2) -> e2 , LinkedHashMap::new));
	//	System.out.println(wordFreq);
		writer.append(sortedMap.toString());
		writer.flush();
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
	
		System.out.println("Difference between mean and std. deviation : " + (average - stdDev));
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

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private int wordLength = DEFAULT_WORD_LENGTH;
    private List<String> wordList;
    HashSet<String> wordSet;
    HashMap<String, ArrayList<String>> lettersToWords;
    HashMap<Integer, ArrayList<String>> sizeToWords;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        wordList = new ArrayList();
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
        }

        //Populate the set and anagram map
        wordSet = new HashSet<>();
        wordSet.addAll(wordList);
        populateMaps(wordSet);
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        String sortedLetters = sortLetters(targetWord);
        if(!lettersToWords.containsKey(sortedLetters))
            return new ArrayList<>();

        ArrayList<String> anagrams = lettersToWords.get(sortedLetters);
//        ArrayList<String> result = new ArrayList<String>();
//
//        String sortedTargetLetters = sortLetters(targetWord);
//        for (String word : wordList){
//            String sortedWord = sortLetters(word);
//            if(sortedWord.equals(sortedTargetLetters)){
//                result.add(word);
//            }
//        }
        return anagrams;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        //Find all anagrams with one more letter
        ArrayList<String> anagrams = new ArrayList<>();
        for(char c = 'a'; c <= 'z'; c++){
            String key = sortLetters(word + c);
            if(lettersToWords.containsKey(key)){
                anagrams.addAll(lettersToWords.get(key));
            }
        }
        return anagrams;
    }

    public String pickGoodStarterWord() {
        if(!sizeToWords.containsKey(wordLength)){
            if(wordLength < MAX_WORD_LENGTH)
                wordLength++;
            return "stop";
        }

        ArrayList<String> possibleWords = sizeToWords.get(wordLength);

        if(wordLength < MAX_WORD_LENGTH)
            wordLength++;

        for(String word : possibleWords){
            ArrayList<String> anagrams = getAnagramsWithOneMoreLetter(word);
            if(anagrams.size() >= MIN_NUM_ANAGRAMS)
                return word;
        }

        return "stop";

        //Pick from random point
//        int startingPoint = random.nextInt(wordList.size());
//
//        int i = startingPoint;
//
//        do {
//            String word = wordList.get(i);
//            ArrayList<String> anagrams = getAnagramsWithOneMoreLetter(word);
//            if(anagrams.size() >= MIN_NUM_ANAGRAMS)
//                return word;
//
//            if(++i > wordList.size())
//                i = 0;
//        }while(i != startingPoint);
//
//        return "stop";
    }

    private String sortLetters(String string) {
        char[] characters = string.toCharArray();
        Arrays.sort(characters);

        StringBuilder sb = new StringBuilder();
        for (char c : characters) {
            sb.append(c);
        }

        return sb.toString();
    }

    private void populateMaps(Set<String> wordSet){
        lettersToWords = new HashMap<>();
        sizeToWords = new HashMap<>();

        for(String word : wordSet){
            //Add to letterstoWords map
            String sortedLetters = sortLetters(word);
            if(!lettersToWords.containsKey(sortedLetters))
                lettersToWords.put(sortedLetters, new ArrayList<String>());

            lettersToWords.get(sortedLetters).add(word);

            //Add to Size To Words map
            final int size = word.length();
            if(!sizeToWords.containsKey(size))
                sizeToWords.put(size, new ArrayList<String>());

            sizeToWords.get(size).add(word);
        }
    }
}

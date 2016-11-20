package com.google.engedu.ghost;

import android.provider.UserDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }

        Collections.sort(words);
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix == null || prefix.isEmpty()){
            int i = (int)(Math.random() * words.size());
            return words.get(i);
        }

        return binarySearchContains(prefix, words, 0, words.size() - 1);
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        if(prefix != null){
            for(String word : words){
                if(word.toLowerCase().contains(prefix))
                    return word;
            }
        }
        return selected;
    }

    public String binarySearchContains(String value, ArrayList<String> list, int startIndex, int endIndex){
        if(startIndex == endIndex){
            if(list.get(startIndex).startsWith(value))
                return list.get(startIndex);

            return null;
        }

        int midpoint = (endIndex + startIndex) / 2;

        String middleValue = list.get(midpoint);

        if(middleValue.startsWith(value))
            return middleValue;

        if(middleValue.compareTo(value) > 0){
            return binarySearchContains(value, list, startIndex, midpoint - 1);
        }
        return binarySearchContains(value, list, midpoint, endIndex);
    }

//   //Search based on even and odd length
//    public boolean isEven(String word){
//        if (word.length() % 2 == 0){
//            return true;
//        }
//        return false;
//    }
//
//    public ArrayList<String> evenList(){
//        ArrayList<String> evenList = new ArrayList<>();
//        for(String word: words){
//            if (isEven(word)){
//                evenList.add(word);
//            }return evenList;
//        }
//    }
//
//    public ArrayList<String> oddList(){
//        ArrayList<String> oddList = new ArrayList<>();
//        for(String word: words){
//            if(!isEven(word)){
//                oddList.add(word);
//            }return oddList;
//        }
//    }
//
//    public String evenBinarySearchContains(String word, ArrayList<String> evenList,
//                                              int startIndex, int endIndex){
//        if(startIndex == endIndex){
//            if(evenList.get(startIndex).startsWith(word))
//                return evenList.get(startIndex);
//
//            return null;
//        }
//
//        int midpoint = (endIndex + startIndex) / 2;
//
//        String middleValue = evenList.get(midpoint);
//
//        if(middleValue.startsWith(word))
//            return middleValue;
//
//        if(middleValue.compareTo(word) > 0){
//            return binarySearchContains(word, evenList, startIndex, midpoint - 1);
//        }
//        return binarySearchContains(word, evenList, midpoint, endIndex);
//    }
//
//    public String oddBinarySearchContains(String word, ArrayList<String> oddList,
//                                           int startIndex, int endIndex){
//        if(startIndex == endIndex){
//            if(oddList.get(startIndex).startsWith(word))
//                return oddList.get(startIndex);
//
//            return null;
//        }
//
//        int midpoint = (endIndex + startIndex) / 2;
//
//        String middleValue = oddList.get(midpoint);
//
//        if(middleValue.startsWith(word))
//            return middleValue;
//
//        if(middleValue.compareTo(word) > 0){
//            return binarySearchContains(word, oddList, startIndex, midpoint - 1);
//        }
//        return binarySearchContains(word, oddList, midpoint, endIndex);
//    }
}

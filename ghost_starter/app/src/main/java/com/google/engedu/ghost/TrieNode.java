package com.google.engedu.ghost;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class TrieNode {
    HashMap<Character, TrieNode> children;
    boolean isWordEnd;
    TrieNode root = new TrieNode();

    public TrieNode(){
        isWordEnd = false;
        children = new HashMap<>();

    }

    public void add(String s){
        if(s.isEmpty()){
            isWordEnd = true;
            return;
        }

        char c = s.charAt(0);

        if(!children.containsKey(c)){
            children.put(c, new TrieNode());
        }

        if(s.length() > 1)
            children.get(c).add(s.substring(1));

        children.get(c).add("");
    }

    public boolean isWord(String s) {
        if(s.isEmpty()){
            return isWordEnd;
        }

        char letter = s.charAt(0);

        if(!children.containsKey(letter)){
            return false;
        }

        TrieNode nextNode = children.get(letter);

        if(s.length() > 1)
            return nextNode.isWord(s.substring(1));

        return nextNode.isWord("");
    }

    //return if there is any word in the trie that starts with the given prefix
    public String getAnyWordStartingWith(String s){
        if(!(searchNode(s) == null)){

        }else
        return null;
    }
    public String getGoodWordStartingWith (String s){
        return null;
    }
    public TrieNode searchNode(String s){
        Map<Character, TrieNode> children = root.children;
        TrieNode trieNode = null;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(children.containsKey(c)){
                trieNode = children.get(c);
                children = trieNode.children;
            }else{
                return null;
            }
        }
        return trieNode;
    }

}



















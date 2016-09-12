package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class AnagramDictionary<T> implements Comparable {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    HashSet<String> wordList = new HashSet<>();
    HashMap<String,ArrayList> anagramMap= new HashMap<>();
    HashMap<String,HashSet> addedWordAnagram=new HashMap<>();
    HashMap<String,ArrayList> addedAnagramWordsInDictonary=new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add((String) word);
        }
    }

    public boolean isGoodWord(String word, String base) {

        boolean indexPresent=wordList.contains(word); //check word is present in hashset
        if(indexPresent)
        {
            Log.i("test", "isGoodWord: "+(base.toLowerCase())+" "+(word.toLowerCase())+" "+(word.toLowerCase()).contains(base.toLowerCase()));
            if((word.toLowerCase()).contains(base.toLowerCase())) //check for it is not formed using subset
            {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        Iterator iterator = wordList.iterator();
        String sortedWord=sortLetter(targetWord);
        ArrayList<String> result=anagramMap.get(sortedWord);
        if(result==null) {
            result=new ArrayList<>();
            String sortedTarget = sortedWord;
            while (iterator.hasNext()) {
                String currentWord = (String) iterator.next();
                if ((currentWord.length() == sortedTarget.length()) && sortedTarget.compareTo(sortLetter(currentWord)) == 0) {
                    result.add(currentWord);
                }
            }
            anagramMap.put(sortedWord,result);
        }
        return result;
    }

    public String sortLetter(String wordToSort)
    {
        char[] temp=wordToSort.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result;

        Iterator iterator=wordList.iterator();
        String sortedWord=sortLetter(word);

        result=addedAnagramWordsInDictonary.get(sortedWord);

        if(result==null) {
            result=new ArrayList<>();
            //get the sorted version of given word and check if already has valid sorted anagrams
            HashSet<String> valid_anagram = addedWordAnagram.get(sortedWord);
            if (valid_anagram == null) {
                valid_anagram = new HashSet<>();
                word = word.toLowerCase();
                for (int i = 0; i < 26; i++) {
                    String new_word = word + (char )(97 + i); //add a word to the given word
                    new_word = sortLetter(new_word); //sort the word
                    valid_anagram.add(new_word);  //add to list
                }
                addedWordAnagram.put(sortedWord, valid_anagram);
            }

            for(String x: valid_anagram)
            {
                HashSet<String> s=new HashSet<>(getAnagrams(x));
                result.addAll(s);
            }
            addedAnagramWordsInDictonary.put(sortedWord,result);
        }

        ArrayList results=new ArrayList<>();
        for(String x: result)
        {
            if(isGoodWord(x,word))
            {
                results.add(x);
            }
        }
        return results;
    }

    public String pickGoodStarterWord() {
        Random r=new Random();
        ArrayList ar=new ArrayList(wordList);
        while(true) {
            int index = r.nextInt(wordList.size());
            String tempWord=(String)ar.get(index);
            int lengthWord=tempWord.length();
            if(lengthWord<=MAX_WORD_LENGTH)
            {
                return tempWord;
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        String target = (String) o;
        String source = this.toString();
        if (target.equalsIgnoreCase(source))
            return 0;
        else
            return -1;
    }
}

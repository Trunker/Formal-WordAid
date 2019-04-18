package dev.goodjob.wordmemorizationaid;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDataProvider {
    public static ArrayList<Word> wordList =  new ArrayList<>();
    public  static HashMap<String, Word> wordMap = new HashMap<>();

    public static void addWord(String itemId, String ognF, String epS, String epn){
        Word item = new Word(itemId, ognF, epS, epn);
        wordList.add(item);
        wordMap.put(itemId, item);
    }

    public static List<String> getWordOF(){
        ArrayList<String> list = new ArrayList<>();
        for(Word word:wordList){
            list.add(word.getWordOriginalForm());
        }
        return list;
    }

    public static void clear(){
        wordList.clear();
        wordMap.clear();
    }
}

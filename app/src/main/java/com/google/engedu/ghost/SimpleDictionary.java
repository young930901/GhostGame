package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    Random random = new Random();
    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override

    public String getAnyWordStartingWith(String prefix) {

        int index= random.nextInt(words.size());
        String s="";
        int low =0;
        int high = words.size()-1;
        int mid = (low + high) /2;


        if(prefix=="") {
            words.get(index);
            return s;
        }
        else if(prefix.length()>0)
        {
            String bword= "";
            while(low<=high)
            {
                if(words.get(mid).startsWith(prefix))
                {
                    bword = words.get(mid);
                    return bword;
                }
                else if (words.get(mid).compareTo(prefix)<0)
                {
                    low = mid +1;
                }
                else if(words.get(mid).compareTo(prefix)>0)
                {
                    high = mid-1;
                }
                mid = (low+high)/2;
            }
            return null;

        }
        return null;

    }


    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}

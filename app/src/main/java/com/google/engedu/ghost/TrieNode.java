package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

        if (s == null || s.isEmpty()) {
            return;
        }

        String firstChar = s.substring(0,1);
        TrieNode child = children.get(firstChar);
        if (child == null) {
            child = new TrieNode();
            children.put(firstChar, child);
        }

        if (s.length() > 1) {
            child.add(s.substring(1));
        } else {
            child.isWord = true;
        }
    }

    public boolean isWord(String s) {
        TrieNode childNode = this;


        for (int i = 0; i < s.length(); i++) {
            if (childNode.children.containsKey(s.substring(i, i + 1))) {
                childNode = childNode.children.get(s.substring(i, i + 1));
                if(childNode.isWord==true)
                {
                    return true;
                }
            } else {
                return false;
            }
        }
            return false;
}


    public String getAnyWordStartingWith(String s) {
        String word = s;
        TrieNode childNode = this;
        for(int i=0; i<s.length();i++)
        {
            String s2 = s.substring(i,i+1);
            childNode = childNode.children.get(s2);
            if(childNode==null)
            {
                return null;
            }
        }

        while(childNode.isWord==false)
        {
            String n = childNode.children.keySet().iterator().next();
            word+=n;
            childNode = childNode.children.get(n);
        }

        return word;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}

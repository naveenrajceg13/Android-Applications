package com.google.engedu.ghost;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;
    private int number;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
        int number=0;
    }

    public void add(String s) {

        char[] ss=s.toCharArray();
        TrieNode temp=this;
        for(int i=0;i<ss.length;i++)
        {
            if(temp.children.get(String.valueOf(ss[i]))==null)    //if word is already there just skip and go to next letter node
            {
                TrieNode tire = new TrieNode();             // if letter is not there create an index for that letter
                tire.number=this.number+i+1;
                if (i!=ss.length-1) {
                    tire.isWord = false;
                } else {                                      // marking end
                    tire.isWord = true;
                }
                temp.children.put(String.valueOf(ss[i]), tire);     //put the word in the node and point to the next node
            }
            temp=temp.children.get(String.valueOf(ss[i]));
        }
    }

    public boolean isWord(String s) {
        char[] temp=s.toCharArray();
        TrieNode tempNode=this;
        for(int i=0;i<temp.length;i++) {
            try
            {
                tempNode = tempNode.children.get(String.valueOf(temp[i]));
                if (tempNode == null)
                    return false;
            }
            catch(Exception e)
            {
                return false;
            }
        }

        return tempNode.isWord;

    }

    public String getAnyWordStartingWith(String s) {

        char[] temp=s.toCharArray();
        TrieNode tempNode=this;
        for(int i=0;i<temp.length;i++)
        {
            try{
                tempNode=tempNode.children.get(String.valueOf(temp[i]));
                if(tempNode==null)
                    break;
            }
            catch(Exception e)
            {
                break;
            }


        }
        if(tempNode!=null)
        {
            for(String ss: tempNode.children.keySet())
                return s + ss;
        }
        return null;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }


    public static void main(String args[])
    {
        TrieNode trynode=new TrieNode();
        trynode.add("abcd");
        trynode.add("abced");
        boolean value=trynode.isWord("abced");
    }
}

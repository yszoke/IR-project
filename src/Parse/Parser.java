package Parse;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static sun.tools.jar.CommandLine.parse;

/**
 * this class receive doc, go over each word and parser it. save the new word in a list.
 */

public class Parser {

    private int indexDoc;
    private String path;
    private HashMap<String,Integer> newWords;
    private HashSet<String> entities;

    public Parser(int indexDoc,String path) {
        this.indexDoc = indexDoc;
        this.path = path;
        this.entities=new HashSet<>();
    }



    /**
     * this function recieve doc of a doc and split to list of sentences.
     * @param
     */


    public String[] splitTextToSentence(String doc) {
        return doc.split("\\. |, |\\?|\\!");
    }

    /**
     * this function recieve sentence and split it to array of words.
     * send the array to parse method
     * @param sentence
     * @throws IOException
     */
    private String[] splitToWords(String sentence) {
        return sentence.split(" +");

    }

    /**
     * this function will go over each word and parse it to number/term/ entity etc..save each term in a list of new word
     * @param words
     * @throws IOException
     */


    public void preparationToPaser(String doc) throws IOException {
        String sentences[] = splitTextToSentence(doc);
        for (String sentence : sentences) {
            String lineOfWords[] = splitToWords(sentence);
            parse(lineOfWords);
        }
    }

    private void parse(String[] words) throws IOException {
        int index = 0;
        newWords=new HashMap<>();
        StopWords stopWords=new StopWords(path);
        Number number= new Number();

        while(index<words.length)
        {
            //if word is part of yeshut
            //yesut(newWords,words,index);
            if (Character.isUpperCase(words[index].charAt(0))) {
                String term = words[index];
                index++;
                while(index<words.length && Character.isUpperCase(words[index].charAt(0))){
                    term += " " + words[index];
                    index++;
                }
                //add the new term to new words list and entities list
                insertToWordsList(term);
                entities.add(term);
                //send to check if yeshut (new words, words, index)

            }
            //if word is stop word
            else if(stopWords.check(words[index])){
                index++;
                //dont insert to new words

            }
            //if the word is a number
            else if(number.check(words,index)){
                if(index<words.length-1) {
                    String nextWord = words[index + 1];
                    if (nextWord == "percent" || nextWord == "percentage") {
                        newWords = number.change2(newWords, words, index);
                        index+=2;

                    }
                }
                //send to numbers
                newWords=number.change(newWords,words,index);
                index++;
            }
            //the word is a term
            else
            {
                insertToWordsList(words[index]);
                index++;
            }
        }

        //dictionary.send(newWords,docnum);

    }


    /**
     * this method will insert a word to the list, if the word is already there, then the counter will increase by 1.
     * @param word
     */
    public void insertToWordsList(String word){
        //if the word is in the list - value++
        if (newWords.containsKey(word)){
            newWords.put(word,newWords.get(word)+1);
        }else{
            newWords.put(word,1);
        }
    }

}

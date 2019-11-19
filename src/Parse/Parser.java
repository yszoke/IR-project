package Parse;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * this class receive doc, go over each word and parser it. save the new word in a list.
 */

public class Parser {

    private int indexDoc;
    private String path;
    private HashMap<String,Integer> newWords;
    private HashSet<String> entities;
    private StopWords stopWords;
    private Number number;
    private int indexInSentence;

    public Parser(int indexDoc,String path) throws IOException {
        this.indexDoc = indexDoc;
        this.path = path;
        this.entities=new HashSet<>();
        this.newWords=new HashMap<>();
        this.stopWords=new StopWords(path);
        this.number= new Number();
    }






    public void preparationToPaser(String doc) throws IOException {
        String sentences[] = splitTextToSentence(doc);
        for (String sentence : sentences) {
            String lineOfWords[] = splitToWords(sentence);
            parse(lineOfWords);
        }
    }

    /**
     * this function recieve doc of a doc and split to list of sentences.
     * @param
     */


    public String[] splitTextToSentence(String doc) {
        return doc.split("\\. |, |- |\\?|\\!");
    }

    /**
     * this function recieve sentence and split it to array of words.
     * send the array to parse method
     * @param sentence
     * @throws IOException
     */
    public String[] splitToWords(String sentence) {
        return sentence.split(" +");

    }

    /**
     * this function will go over each word and parse it to number/term/ entity etc..save each term in a list of new word
     * @param words
     * @throws IOException
     */

    public void parse(String[] words) throws IOException {
        indexInSentence = 0;
        while(indexInSentence <words.length)
        {
            //if word is part of yeshut
            //yesut(newWords,words,index);
            if (Character.isUpperCase(words[indexInSentence].charAt(0))) {
                addToEntity(words);


            }
            //if word is stop word
            else if(stopWords.check(words[indexInSentence])){
                indexInSentence++;
                //dont insert to new words

            }
            //if the word is a number
            else if(number.check(words, indexInSentence)){
                if(indexInSentence <words.length-1) {
                    String nextWord = words[indexInSentence + 1];
                    if (nextWord == "percent" || nextWord == "percentage") {
                        newWords = number.change2(newWords, words, indexInSentence);
                        indexInSentence +=2;

                    }
                }
                //send to numbers
                newWords=number.change(newWords,words, indexInSentence);
                indexInSentence++;
            }
            //the word is a term
            else
            {
                insertToWordsList(words[indexInSentence]);
                indexInSentence++;
            }
        }

        //dictionary.send(newWords,docnum);

    }

    /**
     * this method is a sub method of parse in case of the term is entity, it will add the term to entites data base and check for the next word recursively.
     * @param words
     */

    private void addToEntity(String[] words) {
        String term = words[indexInSentence];
        indexInSentence++;
        while(indexInSentence<words.length && Character.isUpperCase(words[indexInSentence].charAt(0))){
            term += " " + words[indexInSentence];
            indexInSentence++;
        }
        //add the new term to new words list and entities list
        insertToWordsList(term);
        entities.add(term);
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

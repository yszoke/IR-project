package Parse;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Parser {

    private int indexDoc;
    private String path;
    private HashMap<String,Integer> newWords;
    private HashSet<String> entities;

    public Parser(int indexDoc,String path) {
        this.indexDoc = indexDoc;
        this.path = path;
    }



    /**
     * this function take a line and split to list of words, and send each word to a parse function
     * @param
     */


    public void splitTextToSentence(String text) throws IOException {
        System.out.println(text);
        String sentences[] = text.split("\\. |, |\\?|\\!");
        for (String sentence:sentences) {
            splitToWords(sentence);

        }
    }

    private void splitToWords(String sentence) throws IOException {
        String lineOfWords[] = sentence.split(" +");
        for (String parseWord : lineOfWords) {
            /*
            if(parseWord.equals("")){
                continue;
            }
             */
            System.out.println(parseWord);
            parse(lineOfWords);

            //parseWord = Parse(parseWord);
            //add parseWord to index
        }
    }


    public void parse(String[] words) throws IOException {
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
                while(Character.isUpperCase(words[index].charAt(0))){
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
                //dont insert to new words
                index++;
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

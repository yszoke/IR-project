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
    private String tempWord;
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

        //replace percent or percentage to %
        doc = doc.replaceAll("\\%|\\s\\bpercent\\b|\\s\\bpercentage\\b", "%");
        doc = doc.replaceAll(",|\\.", "");

        //replace Thousand to K
        doc = doc.replaceAll("\\s\\bThousand\\b|\\s\\bthousand\\b","K");

        doc = doc.replaceAll("\\s\\bMillion\\b|\\s\\bmillion\\b|\\s\\bm\\b\\s","M");
        //System.out.println(text);

        //replace Billion to B
        doc = doc.replaceAll("\\s\\bBillion\\b|\\s\\bbillion\\b|\\s\\bbn\\b\\s|\\s\\bb\\b\\s","B");

        //replace U.S. dollars to Dollars
        doc = doc.replaceAll("\\s\\bU.S. dollars\\b| \\s\\bU.S. Dollars\\b "," Dollars");
        /*

        String sentences[] = splitTextToSentence(doc);
        for (String sentence : sentences) {
            String lineOfWords[] = splitToWords(sentence);
            parse(lineOfWords);
        }

         */
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
            //if word is between - between 1 and 7 insert 4 words after is as a term
            if(wordIsBetween(words)){
            String term ="";
            for (int i = 0; i < 4; i++) {
                term+=words[indexInSentence]+ " ";
                indexInSentence++;
            }
            insertToWordsList(term);
            }

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
            else if(number.check(words[indexInSentence])){
                words[indexInSentence]=number.change(words,indexInSentence);
                //if the number is'nt the last term in the sentence
                if(indexInSentence<words.length-1) {
                    tempWord=number.changeWords(words,indexInSentence);

                    if(!tempWord.equals(words[indexInSentence])&&indexInSentence<words.length-2){


                        indexInSentence+=3;
                    }
                    else{
                        indexInSentence+=2;
                        insertToWordsList(tempWord);
                    }

                }
                //the number is the last term in the sentence
                else
                {
                    insertToWordsList(words[indexInSentence]);
                    indexInSentence++;
                }
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

    private boolean wordIsBetween(String[] words) {
        if (words[indexInSentence].equals("between") || words[indexInSentence].equals("Between")) {
            if (words[indexInSentence + 1] != null && number.check(words[indexInSentence + 1])) {
                return true;
            }
        }
        return false;
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

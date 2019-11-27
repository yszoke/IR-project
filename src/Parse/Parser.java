package Parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this class receive doc, go over each word and parser it. save the new word in a list.
 */

public class Parser {

    private int indexDoc;
    private String path;
    private String doc;
    private HashMap<String,Integer> newWords;
    private HashMap<String,Term> entities;
    private StopWords stopWords;
    private Number number;
    private String tempWord;
    private int indexInSentence;

    public Parser(int indexDoc, String doc,String path) throws IOException {
        this.indexDoc = indexDoc;
        this.doc = doc;
        this.path = path;
        this.entities=new HashMap<>();
        this.newWords=new HashMap<>();
        this.stopWords=new StopWords(path);
        this.number= new Number();
    }






    public void parse() throws IOException {

        doc = addToEntity();

        //remove stop words
        doc = removeStopWords();


        //remove dots and commas
        doc = doc.replaceAll(",\\s|,|\\.\\s|\\)|\\(|\\W\\bs\\b", " ");

        //replace percent or percentage to %
        doc = doc.replaceAll("\\%|\\s\\bpercent\\b|\\s\\bpercentage\\b", "%");

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



            //if the word is a number
            else if (number.check(words[indexInSentence])) {
                words[indexInSentence] = number.change(words, indexInSentence);
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

    private String removeStopWords() throws IOException {

        List stopwords = Files.readAllLines(Paths.get(path+ "\\05 stop_words.txt"));
        ArrayList<String> allWords = Stream.of(doc.toLowerCase()
                .split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);
        return allWords.stream().collect(Collectors.joining(" "));
    }


    public String addToEntity(){
        ArrayList<String> allWords = new ArrayList<String>(Arrays.asList(doc.split(" +")));
        for (int i=0;i<allWords.size();i++){
            if (Character.isUpperCase(allWords.get(i).charAt(0))){
                String entity = allWords.get(i);
                //check with stop words todo
                allWords.set(i,allWords.get(i)+ "*");
                i++;
                while(i<allWords.size()&& Character.isUpperCase(allWords.get(i).charAt(0))){
                    entity = entity + " " + allWords.get(i);
                    allWords.set(i,allWords.get(i)+ "*");
                    i++;
                }

                if (entities.containsKey(entity)){

                    Term term = entities.get(entity);
                    //update exist term
                    term = term.add(indexDoc);
                    entities.put(entity,term);
                }else {
                    Term term = new Term(entity,indexDoc);
                    entities.put(entity,term);
                }
            }
        }

        return allWords.stream().collect(Collectors.joining(" "));
    }


    public void numberCheck(){

    }


}

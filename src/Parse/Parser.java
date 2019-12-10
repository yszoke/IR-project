package Parse;

import invertedIndex.Dictionary;
import invertedIndex.step1;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this class receive doc, go over each word and parser it. save the new word in a list.
 */

public class Parser {

    private int indexDoc;
    private String path;
    private String doc;
    private HashMap<String,Integer> wordsList;
    private HashMap<String,Integer> stemmingList;
    private static HashMap<String,ArrayList<Integer>> entities = new HashMap<>();
    private Dictionary dictionary;
    private step1 step1;

    //private StopWords stopWords;
    private Number number;
    private date date;
    private List<String> stopWords;
    private String tempWord;
    private int indexInText;
    private Stemmer stemmer;

    private static HashMap<String,ArrayList<Integer>> bigWordList = new HashMap<>();

    public Parser(int indexDoc, String doc, String path, Dictionary dictionary) throws IOException {
        this.indexDoc = indexDoc;
        this.doc = doc;
        this.path = path;
        this.date= new date();
        //this.entities=new HashMap<>();
        this.wordsList =new HashMap<>();
        this.stemmingList = new HashMap<>();
        this.stopWords = Files.readAllLines(Paths.get(path+ "\\05 stop_words.txt"));
        //this.stopWords=new StopWords(path);
        this.number= new Number();
        this.dictionary = dictionary;
        this.stemmer=new Stemmer();
        this.step1=new step1();
    }




    /**
     * this function will go over each word and parse it to number/term/ entity etc..save each term in a list of new word
     * @throws IOException
     */


    public void parse() throws IOException {

        //remove dots and commas
        doc = doc.replaceAll(",\\s|\\?|\\!|\\.,|\\)|\\(|\\-{2,}|\\:|\\;|\\]|\\[|[\"]|\\W\\bs\\b|[a-zA-Z]+\\s\\bpercent\\b|[a-zA-Z]+\\s\\bpercentage\\b", " ");

        //entity and date removal
        doc = addToEntity();

        //remove stop words
        doc = removeStopWords();

        //regex tranforms
        regexTransforms();

        //change text to array of words
        ArrayList<String> textWords = new ArrayList<String>(Arrays.asList(doc.split("\\s+")));
        textWords.removeIf(s -> s.matches("\\s+"));
        textWords.remove("");
        indexInText = 0;

        /* iterate over the array and create terms */
        for (indexInText =0; indexInText < textWords.size(); indexInText++){
            //System.out.println(textWords.get(indexInText).toString());
            if (textWords.get(indexInText).charAt(0)=='('||textWords.get(indexInText).charAt(textWords.get(indexInText).length()-1)=='*'){
                wordIsCalculated(textWords.get(indexInText));
            }
            //check between
            else if (textWords.get(indexInText).equals("between")){
                wordIsBetween(textWords);
            }
            else if(textWords.get(indexInText).contains("-")){
                insertToWordsList(textWords.get(indexInText),indexInText);
            }
            //if the word is a price
            else if(checkPrice(textWords)){
                insertToWordsList(changePrice(textWords),indexInText);
            }

            //if the word is a number
            else if (number.check(textWords.get(indexInText))) {
                //textWords.get(indexInText) = number.change(words, indexInText);
                insertToWordsList(number.change(textWords,indexInText),indexInText);
            //if the number contains digit insert to wordsLIst
            } else if(textWords.get(indexInText).matches(".*\\d.*")){
                insertToWordsList(textWords.get(indexInText),indexInText);
            }

            //the word is a term
            else
            {
                //remove dot
                if(textWords.get(indexInText).charAt(textWords.get(indexInText).length()-1)=='.'){
                    textWords.set(indexInText, textWords.get(indexInText).substring(0,textWords.get(indexInText).length()-1));
                }
                //if the user want stemming todo
                    insertToStemmingList(textWords.get(indexInText), indexInText);
            }
        }
        System.out.println("");
    }

    /**
     * this function check if its a price
     * @param textWords
     * @return
     */
    private boolean checkPrice(ArrayList<String> textWords)
    {

        String word=textWords.get(indexInText);
        if (((word.length() > 0) && (word.charAt(0) == '$')) || (Character.isDigit(word.charAt(0))&&(indexInText < textWords.size() - 1) && (textWords.get(indexInText+1).equals("(dollars)")||textWords.get(indexInText+1).contains("/")))){
            return true;
        }
        return false;
    }

    private String changePrice(ArrayList<String> textWords) {
        String result = "";
        String word = textWords.get(indexInText);
        if ((word.length() > 0) && (word.charAt(0) == '$' || (indexInText < textWords.size() - 1 && textWords.get(indexInText + 1).equals("(dollars)")))) {
            if (indexInText < textWords.size() - 1 &&textWords.get(indexInText + 1).equals("(dollars)")) {
                result = price.change(textWords, indexInText) + " Dollars";
                indexInText++;
            } else {
                result = price.change(textWords, indexInText);

            }
        } else if (indexInText < textWords.size() - 1 &&textWords.get(indexInText + 1).contains("/")) {
            result = price.change(textWords, indexInText) + " " + textWords.get(indexInText + 1);
            indexInText++;
            if (indexInText < textWords.size() - 1 && textWords.get(indexInText + 1).equals("(dollars)")) {
                result = result + " Dollars";
                indexInText++;
            }
        }
        //System.out.println(result);
        return result;
    }

        /**
         * this func tranforms the text with regex
         */

    public void regexTransforms (){


        //replace percent or percentage to %
        doc = doc.replaceAll("\\%|\\s\\bpercent\\b|\\s\\bpercentage\\b", "%**");

        //replace Thousand to K
        doc = doc.replaceAll("\\s\\(\\bthousand\\b\\)|\\s\\bthousand\\b","K");

        //replace million to M
        doc = doc.replaceAll("\\s\\(\\bmillion\\b\\)|\\s\\bmillion\\b|\\s\\bm\\b\\sd","M");

        //replace Billion to B
        doc = doc.replaceAll("\\s\\(\\bbillion\\b\\)|\\s\\bbillion\\b|\\s\\bbn\\b\\s|\\s\\bb\\b\\s","B");

        //replace U.S. dollars to Dollars
        doc = doc.replaceAll("\\s\\(\\bu\\b\\.\\bs\\b\\.\\)\\s\\bdollars\\b|\\s\\(\\bu\\b\\.\\bs\\b\\.\\)\\s\\(\\bdollars\\b\\) "," (dollars)");
    }

    /**
     * this method create terms from a word that already been calculated
     *
     */

    private void wordIsCalculated(String word) {
        //if the word is (Captial letter at the beginning)
        if (word.charAt(0)=='('){
            word = word.substring(1, word.length() - 1);
                //todo insert to BigWordList

         //percent word
        }else if(word.length()>1&&word.charAt(word.length()-1)=='*'&&word.charAt(word.length()-2)=='*'){
            word = word.substring(0, word.length() - 2);
            insertToWordsList(word,indexInText);
            //add to dictionary (word,docIndex,position)
        // a word or number that contains '-' inside
        }else{
            insertToWordsList(word,indexInText);
        }
    }

    /**
     * between function - parse to dictionary between 7 and seven = 6-7
     * @param textWords
     */
    public void wordIsBetween(ArrayList<String> textWords) {

        if (indexInText+2<textWords.size()&& textWords.get(indexInText+1).chars().allMatch(Character::isDigit)&& textWords.get(indexInText+2).chars().allMatch(Character::isDigit)){
            String temp = textWords.get(indexInText+1)+"-"+textWords.get(indexInText+2);
            insertToWordsList(temp,indexInText);
            indexInText +=3;
        }

    }







    /**
     * this method is a sub method of parse that remove all the stop words.
     */
    private String removeStopWords() throws IOException {
        stopWords.remove("between");

        ArrayList<String> allWords = Stream.of(doc.toLowerCase()
                .split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopWords);
        return allWords.stream().collect(Collectors.joining(" "));
    }
    /**
     * this method is a sub method of parse in case of the term is entity, it will add the term to entites data base and check for the next word recursively.
     */

    public String addToEntity(){
        ArrayList<String> allWords = new ArrayList<String>(Arrays.asList(doc.split(" +")));
        allWords.remove(new String(""));
        for (int i=0;i<allWords.size();i++){
            if (isUpperCase(i, allWords)){
                //if its bigger than one letter
                if (allWords.get(i).length()<2){
                    continue;
                }
                if (allWords.get(i).length()==2&&allWords.get(i).charAt(1)=='.'){
                    allWords.set(i,allWords.get(i).substring(0,1));
                }
                //if its not in the start of a sentence
                if (i>0 && isStartOfSentence(allWords.get(i-1))){
                    allWords.set(i-1,allWords.get(i-1).substring(0, allWords.get(i-1).length() - 1));
                    continue;
                }

                if(i>0&&i+1<allWords.size()&& isMonth(allWords.get(i),allWords.get(i-1),allWords.get(i+1))){
                    allWords = dateChange(allWords,i);
                    continue;
                }
                String entity = allWords.get(i);
                if(!isStopWord(allWords.get(i))){

                    allWords.set(i,"("+ allWords.get(i)+")");
                }
                i++;
                while(isUpperCase(i, allWords)){////////////////////////////////////////
                    if (allWords.get(i).length()<2){
                        i++;
                        break;
                    }
                    if (i>0 && isStartOfSentence(allWords.get(i-1))){
                        entity = entity.substring(0,entity.length()-1);
                        allWords.set(i-1,allWords.get(i-1).substring(0, allWords.get(i-1).length() - 2)+")");
                        i++;
                        break;
                    }
                    if(i+1<allWords.size()&&isMonth(allWords.get(i),allWords.get(i-1),allWords.get(i)+1)){
                        allWords = dateChange(allWords,i);
                        break;
                    }
                    if (allWords.get(i).length()==2&&allWords.get(i).charAt(1)=='.'){
                        allWords.set(i,allWords.get(i).substring(0,1));
                    }
                    entity = entity + " " + allWords.get(i);
                    if(!isStopWord(allWords.get(i))){
                        allWords.set(i,"("+allWords.get(i)+ ")");
                    }
                    i++;
                }
                //delete last dot if exist
                if (entity.charAt(entity.length()-1)=='.'){
                    entity = entity.substring(0,entity.length()-1);
                }

                addToEntityList( entity);

            }
        }

        //delete last dot
        allWords.set(allWords.size()-1,deleteLastDoc(allWords.get(allWords.size()-1)));

        return allWords.stream().collect(Collectors.joining(" "));
    }

    private void addToEntityList(String entity) {
        if (entities.containsKey(entity)){
            if(!entities.get(entity).contains(indexDoc)){
                entities.get(entity).add(indexDoc);
            }
        }else {
            ArrayList<Integer> temp = new ArrayList<>();
            entities.put(entity,temp);
            entities.get(entity).add(indexDoc);
        }
    }


    private boolean isUpperCase(int i, ArrayList<String> allWords ){
        if (i<allWords.size()&& Character.isUpperCase(allWords.get(i).charAt(0))){
            return true;
        }
        return false;
    }

    private String deleteLastDoc(String s) {
        if (s.charAt(s.length()-1)==')')
            return s.substring(0, s.length() - 2)+")";
        return s.substring(0, s.length() - 1);

    }


    /**
     * a sub method to at to entity to check if a given word is not at a start of a sentence
     * @param s
     * @return
     */
    private boolean isStartOfSentence(String s) {
        if (s.charAt(s.length()-1)=='.'){
            return true;
        }else if(s.length()>1 && s.charAt(s.length()-2)=='.' && s.charAt(s.length()-1)==')'){
            return true;
        }
        return false;
    }

    /**
     * this method change the dates
     * @param allWords
     * @param i
     * @return
     */

    public ArrayList<String> dateChange(ArrayList<String> allWords, int i) {
        if (i>0 && allWords.get(i-1).chars().allMatch(Character::isDigit)){
            //send to date function (allWords.get(i), allWords.get(i-1))
            allWords.set(i,date.changeToDate(allWords.get(i), allWords.get(i-1)));
            allWords.set(i-1,"the");
        } else if (i<allWords.size()-1 && allWords.get(i+1).chars().allMatch(Character::isDigit)){
            //send to date function (allWords.get(i), allWords.get(i+1))
            allWords.set(i,date.changeToDate(allWords.get(i), allWords.get(i+1)));
            allWords.set(i+1,"the");
        }
        return allWords;
    }

    /**
     * this method check if a given word is a stop word
     * @param word
     * @return
     */
    private boolean isStopWord(String word) {
        word = word.toLowerCase();
        if (stopWords.contains(word)){
            return true;
        }
        return false;
    }


    /**
     * this method check if a given word is a month
     * @param word
     * @return
     */

    public boolean isMonth(String word,String lastWord,String nextWord){
        String month = "([Jj][Aa][Nn](?:[Uu][Aa][Rr][Yy])?|[Ff][Ee][Bb](?:[Rr][Uu][Aa][Rr][Yy])?|[Mm][Aa][Rr](?:[Cc][Hh])?|[Aa][Pp][Rr](?:[Ii][Ll])?|\\bMAY\\b|\\bMay\\b|\\bmay\\b|[Jj][Uu][Nn](?:[Ee])?|[Jj][Uu][Ll](?:[Yy])?|[Aa][Uu][Gg](?:[Uu][Ss][Tt])?|[Ss][Ee][Pp](?:[Tt][Ee][Mm][Bb][Ee][Rr])?|[Oo][Cc][Tt](?:[Oo][Bb][Ee][Rr])?|[Nn][Oo][Vv](?:[Ee][Mm][Bb][Ee][Rr])?|[Dd][Ee][Cc](?:[Ee][Mm][Bb][Ee][Rr])?)";
        Pattern p = Pattern.compile("^"+month+"$");
        Matcher m = p.matcher(word);
        if (m.find()){
            if(lastWord.chars().allMatch(Character::isDigit)||nextWord.chars().allMatch(Character::isDigit))
            return true;
        }
        return false;
    }

    /**
     * this method will insert a word to the list, if the word is already there, then the counter will increase by 1.
     * @param word
     */
    public void insertToWordsList(String word,int position){

        //wordsList.put(word,position);
        step1.addToTable(word, indexDoc, position);
    }

    public void insertToStemmingList(String word,int position){
        char[] charAray=word.toCharArray();
        stemmer.add(charAray,word.length());
        stemmer.stem();
        //stemmingList.put(stemmer.toString(),position);
        step1.addToTable(stemmer.toString(), indexDoc, position);
    }

}

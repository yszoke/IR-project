package invertedIndex;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {

    private static HashMap<String, Term> dictionary = new HashMap<>();
    private static String temDdocNum="";
    private static String tempword="";
    private static HashMap<Character,Integer> lineInPostingTable=new HashMap<>();

    public void addWordToDictionary(ArrayList<String> sortedTable){
        for(String word: sortedTable){
            String[] temp = word.split(" ");

            //if the word is already in the dictionary
            if (dictionary.containsKey(temp[0])) {
                Term termToChange = dictionary.get(temp[0]);
                if (tempword != temp[0] || temDdocNum != temp[1]) {
                    termToChange.increaseNDocs();
                    dictionary.put(temp[0], termToChange);
                }
                writeToPosting(temp[0], temp[1], temp[2], termToChange.getLineInPosting());

                //if word is not in the dictionary, create it
            }
            else {
                Term termToCreate = new Term(getLineInPosting(String temp[0]));
                dictionary.put(temp[0],termToCreate);
                writeToPosting(temp[0],temp[1],temp[2],termToCreate.getLineInPosting());
            }
            tempword=temp[0];
            temDdocNum=temp[1];
        }
    }

    private void writeToPosting(String word, String docIndex, String position, int lineInPosting) {
        //if we know the position in the posting file
        if (lineInPosting!=-1){
            char firstLetter=word.charAt(0);
            if(!Character.isLetter(firstLetter)){
                firstLetter='*';
            }
            if(lineInPostingTable.containsKey(firstLetter)){
                //todo write to firstLetter file to lineInPostingTable.value line
                lineInPostingTable.put(firstLetter, lineInPostingTable.get(lineInPostingTable) + 1);
            }
            else{
                lineInPostingTable.put(firstLetter,1);

            }

        }

    }

    /**
     * this method checks if a term is already in the dictionary
     * @param term
     * @return
     */
    public boolean isInDictionary(Term term){
        return true;
    }

    /**
     * this method checks for a given term and a doc index - if the term existed in the doc index
     * @param term
     * @param docIndex
     * @return
     */
    public boolean isInDocIndex(Term term, int docIndex){
        return true;
    }

}

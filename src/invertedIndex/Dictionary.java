package invertedIndex;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {

    private static HashMap<String, Term> dictionary = new HashMap<>();
    private static String tempDocNum = "";
    private static String tempword = "";
    private static HashMap<Character, Integer> lineInPostingTable = new HashMap<>();
    private char cuurntLetter = '*';

    private static HashMap<Character, String> currentPsting = new HashMap<>();

    public void addWordToDictionary(ArrayList<String> sortedTable) {
        for (String word : sortedTable) {
            String[] temp = word.split(" ");
            //get the first char of the word
            char firstLetter = getFirstLetter(temp[0]);
            int line;
            //check if we need to change the posting
            if (firstLetter != cuurntLetter) {
                //todo change file
                cuurntLetter = firstLetter;
            }
            //if the word is already in the dictionary
            if (dictionary.containsKey(temp[0])) {
                //check if the word and the doc is the same as before (different location)-just need to write to posting
                if (tempDocNum != temp[1] || tempword != temp[0]) {
                    dictionary.get(temp[0]).increaseNDocs();
                    tempDocNum = temp[1];
                    tempword = temp[0];
                }
            }
            //if word is not in the dictionary, create it
            else {
                //todo maybe create the file
                //getLine: gives you the line in your posting
                line = getLine(firstLetter);
                Term term = new Term(1, line);
                dictionary.put(temp[0], term);
            }
            //write to posting
            writeToPosting(temp[0], temp[1], temp[2], dictionary.get(temp[0]).getLineInPosting());
            tempword = temp[0];
            tempDocNum = temp[1];
        }
    }

    /**
     * this function get a letter and found the next line in the posting for this letter
     * @param firstLetter
     * @return a number of a line
     */
    private int getLine(char firstLetter) {
        if (lineInPostingTable.containsKey(firstLetter)) {
            int line = lineInPostingTable.get(lineInPostingTable);
            lineInPostingTable.put(firstLetter, line + 1);
            return line;
        } else {
            lineInPostingTable.put(firstLetter, 1);
            return 0;
        }
    }

    /**
     * this function get a word and return the character that represent the file name
     * @param word
     * @return the character that represent the file name
     */
    private char getFirstLetter(String word) {
        char temp = word.charAt(0);
        if (Character.isLetter(temp)) {
            return temp;
        }
        return '*';
    }

    private void writeToPosting(String word, String docIndex, String position, int lineInPosting) {
        //todo write: we have the current letter and the posting in Array
    }
}


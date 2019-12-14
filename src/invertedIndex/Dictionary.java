package invertedIndex;

import Parse.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * this class create a dictionary and split the file to posting files
 */
public class Dictionary {

    File file;
    private HashMap<String, String> dictionary;
    private HashMap<String, Integer> userDictionary;
    private HashMap<String, Integer> wordsInDoc;
    private HashMap<String, Integer> popularWordInDoc;

    /**
     * constructor
     *
     * @param file
     */
    public Dictionary(File file) {
        this.file = file;
        dictionary = new HashMap<>();
        userDictionary = new HashMap<>();
        wordsInDoc = new HashMap<>();
        popularWordInDoc = new HashMap<>();
        //init();
        Parser.getBigWordList();
    }

    /**
     * this function reads the file, split to x posting files and creates the dictionary
     *
     * @throws IOException
     */
    public void create() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String[] line;
        int index = 0;
        int counterFile = 0;
        int counterLine = 0;
        int pointerLine = 1;
        int Ndocs = 0;
        int shows = 0;
        int showsInCorpus = 0;
        String previousWord = "";
        String currentWord = "";
        String ln = reader.readLine();
        String doc = "";
        // while the line we read isn't null
        while (ln != null) {
            // if the line is a space, we skip
            while (ln.equals("") || ln.charAt(0) == ' ') {
                ln = reader.readLine();
            }
            //create the posting file
            counterFile++;
            FileWriter pw = new FileWriter("posting/" + counterFile + ".txt", false);
            counterLine = 0;

            // each x lines is one posting file
            while (ln != null && (counterLine < 10000000)) {
                line = ln.split(" ");
                if (line.length < 3) {
                    continue;
                }
                currentWord = calculateWord(line);
                doc = calculateDoc(line);
                if (wordsInDoc.containsKey(doc)) {
                    wordsInDoc.put(doc, wordsInDoc.get(doc) + 1);
                } else {
                    wordsInDoc.put(doc, 1);
                }
                shows = calculateShows(line);
                if (popularWordInDoc.containsKey(doc)) {
                    if (shows > popularWordInDoc.get(doc)) {
                        popularWordInDoc.put(doc, shows);
                    }
                } else {
                    popularWordInDoc.put(doc, shows);
                }

                if (currentWord.equals(previousWord)) {
                    Ndocs++;
                    showsInCorpus += shows;
                } else {
                    //add to dictionary with: number of docs, file name, number of line in the doc

                    if (Parser.getBigWordList().containsKey(previousWord) && Parser.getBigWordList().get(previousWord).size() == showsInCorpus) {

                        dictionary.put(previousWord.toUpperCase(), Ndocs + "-" + counterFile + "-" + pointerLine);
                        userDictionary.put(previousWord.toUpperCase(), showsInCorpus);
                    } else {
                        dictionary.put(previousWord, Ndocs + "-" + counterFile + "-" + pointerLine);
                        userDictionary.put(previousWord, showsInCorpus);
                    }
                    pointerLine = counterLine;
                    previousWord = currentWord;
                    Ndocs = 1;
                    showsInCorpus = shows;

                }
                //write to the posting file
                pw.write(ln + "\r\n");
                counterLine++;
                ln = reader.readLine();
            }
            pw.close();
            ln = reader.readLine();
            System.out.println(index);
            index++;
        }
        System.out.println(" ");
    }

    /**
     * @param line
     * @return
     */
    private int calculateShows(String[] line) {
        String list = line[line.length - 1];
        String[] locations = list.split(",");
        return locations.length;
    }

    /**
     * @param line
     * @return
     */
    private String calculateDoc(String[] line) {
        if (line.length > 3) {
            return line[line.length - 2];
        }
        return line[1];
    }

    /**
     * this function get a split line and find the word inside
     *
     * @param line
     * @return the word
     */
    private String calculateWord(String[] line) {
        if (line.length > 3) {
            String ans = line[0];
            for (int i = 1; i < line.length - 2; i++) {
                ans = ans + " " + line[i];
            }
            return ans;
        } else {
            return line[0];
        }
    }

    public void saveInformation() throws IOException {
        FileWriter pw = new FileWriter("posting/docMetaData.txt", false);
        FileWriter pw1 = new FileWriter("posting/termsMetaData.txt", false);
        Iterator it = dictionary.entrySet().iterator();
        Iterator it1 = userDictionary.entrySet().iterator();

        while (it.hasNext() && it1.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            pw.write(pair.getKey() + " " + pair.getValue() + "\r\n");
            Map.Entry pair1 = (Map.Entry) it1.next();
            pw1.write(pair1.getKey() + " " + pair1.getValue() + "\r\n");
        }
        pw.close();
        pw1.close();
    }

}


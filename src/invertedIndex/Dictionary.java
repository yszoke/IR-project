package invertedIndex;

import java.io.*;
import java.util.HashMap;

/**
 * this class create a dictionary and split the file to posting files
 */
public class Dictionary {

    File file;
    private HashMap<String, String> dictionary;
    private HashMap<String,Integer> wordsInDoc;
    private HashMap<String,Integer> popularWordInDoc;

    /**
     * constructor
     *
     * @param file
     */
    public Dictionary(File file) {
        this.file = file;
        dictionary = new HashMap<>();
        wordsInDoc = new HashMap<>();
        popularWordInDoc = new HashMap<>();
        //init();
    }

//    private void init() {
//        for(int i=1; i<=472525; i++){
//            wordsInDoc.put(String.valueOf(i),0);
//            popularWordInDoc.put(String.valueOf(i),0);
//        }
//    }

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
            while (ln != null && (counterLine < 100000)) {
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
                } else {
                    //add to dictionary with: number of docs, file name, number of line in the doc
                    dictionary.put(previousWord, Ndocs + "-" + counterFile + "-" + pointerLine);
                    pointerLine = counterLine;
                    previousWord = currentWord;
                    Ndocs = 1;
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
    }

    /**
     *
     * @param line
     * @return
     */
    private int calculateShows(String[] line) {
        String list=line[line.length-1];
        String [] locations= list.split(",");
        return locations.length;
    }
    /**
     *
     * @param line
     * @return
     */
    private String calculateDoc(String[] line) {
        if(line.length>3){
            return line[line.length-2];
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
}

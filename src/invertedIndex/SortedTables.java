package invertedIndex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * this class create sorted tables of words and write them to the disk
 * input: words after parse
 * output: tables
 */
public class SortedTables {

    private static int tableNum = 0;
    private static ArrayList<String> table = new ArrayList<>();

    /**
     * get words from parse, insert to tables and writes to the disk
     *
     * @param word
     * @param docNum
     * @param position
     * @throws IOException
     */

    public void addToTable(String word, int docNum, int position) throws IOException {
        if (word == null || word.equals("")) {
            return;
        }
        //each table contains words from x docs
        if (docNum / 10000 == tableNum) {
            table.add(word + " " + docNum + " " + position);
        } else {
            Collections.sort(table);
            tableNum++;
            FileWriter writer = new FileWriter(new File("posting//" + tableNum + ".txt"));
            for (String str : table) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            table.clear();
        }
    }
}
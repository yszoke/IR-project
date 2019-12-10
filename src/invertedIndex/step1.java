package invertedIndex;

import javafx.collections.transformation.SortedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class step1 {

    private static int tableNum=0;
    private static ArrayList<String> table=new ArrayList<>();




    public void addToTable(String word, int docNum, int position) throws IOException {
        if(docNum/10000==tableNum){
            table.add(word+" "+docNum+" "+position);
        }
        else{
            Collections.sort(table);
            //System.out.println(table);
            //dictionary.addWordToDictionary(table);
            String dir = "//posting";
            FileWriter writer = new FileWriter(new File( "posting//"+tableNum+ ".txt"));
            for(String str: table) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            table.clear();
            tableNum++;
        }
    }
}

package invertedIndex;

import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.Collections;

public class step1 {

    private static int tableNum=0;
    private static ArrayList<String> table=new ArrayList<>();
    Dictionary dictionary= new Dictionary();



    public void addToTable(String word, int docNum, int position){
        if(docNum/10000==tableNum){
            table.add(word+" "+docNum+" "+position);
        }
        else{
            Collections.sort(table);
            //System.out.println(table);
            dictionary.addWordToDictionary(table);
            table.clear();
            tableNum++;
        }
    }
}

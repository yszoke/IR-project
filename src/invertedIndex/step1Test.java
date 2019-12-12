package invertedIndex;

import java.io.IOException;

public class step1Test {

    public static void main(String[] args) throws IOException {

        SortedTables SortedTables =new SortedTables();
        String word="";

        for(int i=0; i<100; i++){
            word="a"+i;
            SortedTables.addToTable(word,i,2*i+2);
            SortedTables.addToTable(word,i,i+1);
        }

    }
}

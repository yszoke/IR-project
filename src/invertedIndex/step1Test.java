package invertedIndex;

import java.io.IOException;

public class step1Test {

    public static void main(String[] args) throws IOException {

        step1 step1=new step1();
        String word="";

        for(int i=0; i<100; i++){
            word="a"+i;
            step1.addToTable(word,i,2*i+2);
            step1.addToTable(word,i,i+1);
        }

    }
}

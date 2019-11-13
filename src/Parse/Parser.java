package Parse;

import java.io.IOException;
import java.util.HashSet;

public class Parser {

    public HashSet parse(String[] words) throws IOException {
        int index = 1;
        HashSet<String> newWords=new HashSet();
        StopWords stopWords=new StopWords();
        Number number= new Number();

        while(index<=words.length)
        {
            //if word is part of yeshut
            if (Character.isUpperCase(words[index].charAt(0))) {
                //send to check if yeshut (new words, words, index)

            }
            //if word is stop word
            else if(stopWords.check(words[index])){
                //dont insert to new words
            }
            //if the word is a number
            else if(number.check(words,index)){
                //send to numbers
            }
            //the word is a term
            else
            {
                newWords.add(words[index]);
            }

            index++;
        }
        return newWords;

    }

}

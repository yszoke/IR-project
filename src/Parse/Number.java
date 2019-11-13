package Parse;

import java.util.HashMap;
import java.util.HashSet;

public class Number {

    public boolean check(String[] words, int index) {
        char firstChar=words[index].charAt(0);
        if(Character.isDigit(firstChar)||firstChar=='$') {
            return true;
        }
        return false;
    }

    public HashMap<String,Integer> change(HashMap<String,Integer> newWords, String[] words, int index) {
        double temp;
        //remove ","
        if (words[index].contains(",")) {
            words[index]=words[index].replace(",", "");
        }
        // 3/4  --> 3/4
        //$4 ---> 4 dollars
        //check if number is b/m/k and change
        if (words[index].contains(".")) {
            temp = Double.parseDouble(words[index]);
        }
        else {
            temp = Integer.parseInt(words[index]);
        }

        if (temp >= 1000000000) {
            temp = temp / 1000000000;
            words[index] = temp + "B";
            System.out.println(words[index]+"!!!");
        }
        else if (temp >= 1000000) {
            temp = temp / 1000000;
            words[index] = temp + "M";
        }
        else if (temp >= 1000) {
            temp = temp / 1000;
            words[index] = temp + "K";
            System.out.println(words[index]+"!!!");
        }
        insertToWordsList(words[index]);
        return newWords;

    }
            //if the next word is: u.s/dolar


    public HashMap<String,Integer> change2(HashMap<String,Integer> newWords, String[] words, int index) {
        return newWords;
    }
}

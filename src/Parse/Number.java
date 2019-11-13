package Parse;

import java.util.HashSet;

public class Number {

    public boolean check(String[] words, int index) {
        char firstChar=words[index].charAt(0);
        if(Character.isDigit(firstChar)||firstChar=='$') {
            return true;
        }
        return false;
    }

    public void change(HashSet<String> newWords, String[] words, int index) {
        //remove ","
        // 3/4  --> 3/4
        //$4 ---> 4 dollars
        //check if number is b/m/k and change
        // if the next word is : tou.../milio.../bili.../per../per../
            //if the next word is: u.s/dolar
    }
}

package Parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Number {
    private ArrayList<String> newWords;

    public boolean check(String word) {
        char firstChar=word.charAt(0);
        if(Character.isDigit(firstChar)||firstChar=='$') {
            return true;
        }
        return false;
    }

    public String change(String[] words, int index) {
        double temp;
        //remove ","
        if (words[index].contains(",")) {
            words[index]=words[index].replace(",", "");
        }
        // check if contain %
        if (words[index].contains("%")) {

        }
//        if (words[index].contains(".")) {
//            temp = Double.parseDouble(words[index]);
//        }
//        else {
//            temp = Integer.parseInt(words[index]);
//        }
        temp = Double.parseDouble(words[index]);

        if (temp >= 1000000000) {
            temp = temp / 1000000000;
            double scale = Math.pow(10, 3);
            temp= Math.round(temp * scale) / scale;
            words[index] = temp + "B";
            //System.out.println(words[index]+"!!!");
        }
        else if (temp >= 1000000) {
            temp = temp / 1000000;
            double scale = Math.pow(10, 3);
            temp= Math.round(temp * scale) / scale;
            words[index] = temp + "M";
        }
        else if (temp >= 1000) {
            temp = temp / 1000;
            double scale = Math.pow(10, 3);
            temp= Math.round(temp * scale) / scale;
            words[index] = temp + "K";
            //System.out.println(words[index]+"!!!");
        }
        else{
            double scale = Math.pow(10, 3);
            temp= Math.round(temp * scale) / scale;
            words[index] = ""+temp;
        }
        //insertToWordsList(words[index]);
        return words[index];

    }

    public String changeWords(String[] words, int index) {
        newWords= new ArrayList();
        String firstWord=words[index];
        String secondWord=words[index+1];
        if(secondWord.equals("percent") || secondWord.equals("percentage")){
            firstWord=firstWord+"%";
            return firstWord;
        }
        //if second word is 3/4
//        if(secondWord.contains("/")&& check(secondWord){
//
//
//        }
        // if second word is million

        return firstWord;



    }
}

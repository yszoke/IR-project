package Parse;

import java.util.ArrayList;

public class Number {
    private ArrayList<String> newWords;

    public boolean check(String word) {
        char firstChar = word.charAt(0);
        if (Character.isDigit(firstChar) && word.matches("[0-9]+|[0-9]++\\b\\.\\b+[0-9]+")) {
            return true;

        }
        return false;
    }

    public String change(ArrayList<String> textWords, int index) {
        double doubleNum;
        String word=textWords.get(index);

        doubleNum = Double.valueOf(word);

        if (doubleNum >= 1000000000) {
            doubleNum = doubleNum / 1000000000;
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                word = "" + (int) doubleNum + "B";
            } else {
                word = "" + doubleNum + "B";
            }
            System.out.println(word);
        } else if (doubleNum >= 1000000) {
            doubleNum = doubleNum / 1000000;
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                word = "" + (int) doubleNum + "M";
            } else {
                word = "" + doubleNum + "M";
            }
            //System.out.println(word);

        } else if (doubleNum >= 1000) {
            doubleNum = doubleNum / 1000;
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                word = "" + (int) doubleNum + "K";
            } else {
                word = "" + doubleNum + "K";
            }
            //System.out.println(word);

        } else {
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                word = "" + (int) doubleNum;
            } else {
                word = "" + doubleNum;
            }
            //System.out.println(word);
        }

        //insertToWordsList(words[index]);
        return word;

    }
}


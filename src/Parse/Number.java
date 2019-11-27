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

    public String change(String[] words, int index) {
        double doubleNum;

        doubleNum = Double.valueOf(words[index]);

        if (doubleNum >= 1000000000) {
            doubleNum = doubleNum / 1000000000;
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                words[index] = "" + (int) doubleNum + "B";
            } else {
                words[index] = "" + doubleNum + "B";
            }
            System.out.println(words[index]);
        } else if (doubleNum >= 1000000) {
            doubleNum = doubleNum / 1000000;
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                words[index] = "" + (int) doubleNum + "M";
            } else {
                words[index] = "" + doubleNum + "M";
            }
            System.out.println(words[index]);

        } else if (doubleNum >= 1000) {
            doubleNum = doubleNum / 1000;
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                words[index] = "" + (int) doubleNum + "K";
            } else {
                words[index] = "" + doubleNum + "K";
            }
            System.out.println(words[index]);

        } else {
            double scale = Math.pow(10, 3);
            doubleNum = Math.round(doubleNum * scale) / scale;
            if ((doubleNum % 1) == 0) {
                words[index] = "" + (int) doubleNum;
            } else {
                words[index] = "" + doubleNum;
            }
            System.out.println(words[index]);
        }

        //insertToWordsList(words[index]);
        return words[index];

    }
}


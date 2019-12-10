package Parse;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class price {

    public static String change(ArrayList<String> textWords, int counter) {
        boolean addDollars = false;
        String word = textWords.get(counter);
        word = word.replaceAll(",", "");
        char last = word.charAt(word.length() - 1);
        double doubleNum = 0;
        int intNum;

        word=word.replaceAll("o","0");

        if (word.charAt(0) == '$') {
            addDollars = true;
            word = word.substring(1);
        }
        //1.5 dollars or $108
        if (word.matches("[0-9]+|[0-9]++\\b\\.\\b+[0-9]+") && Double.parseDouble(word) < 1000) {

        }
        //10000 dollars or $100000
        else if (word.matches("[0-9]+|[0-9]++\\b\\.\\b+[0-9]+") && Double.parseDouble(word) < 1000000) {
            doubleNum = Double.valueOf(word);

            if ((doubleNum % 1) == 0) {
                int amount = (int) doubleNum;
                DecimalFormat formatter = new DecimalFormat("###,###");
                word = formatter.format(amount);
            } else {
                DecimalFormat formatter = new DecimalFormat("###,###.###");
                word = formatter.format(doubleNum);
            }

        } else if (word.matches("[0-9]+|[0-9]++\\b\\.\\b+[0-9]+") && Double.parseDouble(word) >= 1000000) {
            double Num = Double.valueOf(word);
            Num = Num / 1000000;
            //double scale = Math.pow(10, 3);
            //Num = Math.round(Num * scale) / scale;
            intNum = (int) (Num * 1000);
            Num = (double) intNum / 1000;
            if ((Num % 1) == 0) {
                word = "" + (int) Num + " M";
            } else {
                word = "" + Num + " M";
            }
        } else if (last == 'B' || last == 'b'||last == 'n') {
            //word = word.substring(0, word.length() - 1);
            word=word.replaceAll("[^\\d.]", "");
            if(word.matches("[0-9]+|[0-9]++\\b\\.\\b+[0-9]+")) {
                //System.out.println(word);
                doubleNum = Double.parseDouble(word);
                if ((doubleNum % 1) == 0) {
                    intNum = (int) doubleNum;
                    word = intNum * 1000 + " M";
                } else {
                    word = Double.parseDouble(word) * 1000 + " M";
                }
            }
        }  else if (last == 'm' || last == 'M') {
            word = word.replaceAll("[^\\d.]", "");
            if (word.matches("[0-9]+|[0-9]++\\b\\.\\b+[0-9]+")) {

                word = word + " M";
            }
        }
        if (addDollars) {
            word += " Dollars";
            addDollars = false;
        }
        return word;

    }
}
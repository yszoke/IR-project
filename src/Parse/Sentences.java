package Parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * this class get docs and break them into sentences
 */
public class Sentences {

    private String line;

    public static String breakIntoText(String Doc) throws IOException {
        String line = "";
        BufferedReader bufReader = new BufferedReader(new StringReader(Doc));
        while ((line = bufReader.readLine()) != null) {
            while (line != null && !line.equals("<TEXT>")) {
                line = bufReader.readLine();
            }
            if (line != null && line.equals("<TEXT>")) {
                line = bufReader.readLine();
            }
            String text = "";
            while (line != null && !line.equals("</TEXT>")) {
                while (line.equals("")) {
                    line = bufReader.readLine();
                }
                if (line != null && !line.equals("</TEXT>")) {
                    text += line;
                    //splitToWords(line);
                    line = bufReader.readLine();
                }
            }
            return text;
            }
            return null;
        }


    public static String[] breakToSentences(String text) {
        String sentences[] = text.split("\\.|, |\\?|\\!");
        return sentences;
    }

    public static void splitToWords(String line) {
        String lineOfWords[] = line.split(" +");
        for (String parseWord : lineOfWords) {
            if(parseWord.equals("")){
                continue;
            }
            char lastChar = parseWord.charAt(parseWord.length()-1);
            if(lastChar==','||lastChar=='.'||lastChar==':'){
                parseWord=parseWord.substring(0,parseWord.length()-1);
            }

            //parseWord = Parse(parseWord);
            //add parseWord to index
        }
    }

}

package Parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class StopWords {
    String line="";
    HashSet<String> stopWords;


    public StopWords(String path) throws IOException {
        stopWords=new HashSet<>();
        String doc = new String(Files.readAllBytes(Paths.get(path+"\\05 stop_words.txt")));
        BufferedReader bufReader = new BufferedReader(new StringReader(doc));
        while ((line = bufReader.readLine()) != null) {
            stopWords.add(line);
        }
    }


    public boolean check(String word) {
        if (stopWords.contains(word.toLowerCase())) {
            return true;
        }
        return false;
    }
}

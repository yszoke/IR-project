package invertedIndex;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class testPosings {

    public static void main(String[] args) throws IOException {
        readFromFile();

    }
    public static ArrayList<String> readFromFile() throws IOException {
        ArrayList<String> allWords = new ArrayList<>();
        FileReader readfile = new FileReader("C:\\Users\\ohoff\\Documents\\information retrieval\\test\\text.txt");
        BufferedReader readbuffer = new BufferedReader(readfile);
        String line= readbuffer.readLine();
        while (line!=null){
            allWords.add(line);
            line= readbuffer.readLine();
        }
        return allWords;
    }

    public static void addDetailsToFile(int linenumber, ArrayList<String> allWords){
        String line = allWords.get(linenumber);
        //allWords.set(linenumber);
    }

}



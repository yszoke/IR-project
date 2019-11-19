package Parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class testsParser {

    public static void main(String[] args) throws IOException {
        int docIndex = 1;
        String pathForStopWords = "C:\\Users\\ohoff\\Documents\\information retrieval\\corpus";
        Parser parser = new Parser(docIndex,pathForStopWords);
        File input = new File(pathForStopWords+"\\tests\\testForParse");
        //generalTest(input,parser);
        //numberTest(parser);
        entityTest(parser);



    }

    private static void entityTest(Parser parser) throws IOException {
        String sentence1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
        String sentence2 = "The Mongolian Government and people gave wholehearted support to the " +
                            "Vietnamese people in their struggle for national liberation in " +
                                "the past as well as in national Vietnamese construction and defence at present";
        String sentence3 = " We also provide-static-- electricity protection for filling and draining devices";
        String[] test  = parser.splitTextToSentence(sentence3);
        String[] words = parser.splitToWords(sentence1);
        //parser.parse(words);
        words = parser.splitToWords(sentence2);
        parser.parse(words);
        words = parser.splitToWords(sentence3);
        parser.parse(words);

    }

    private static void numberTest(Parser parser) {
    }

    private static void generalTest(File input, Parser parser) throws IOException {
        Document html = Jsoup.parse(input,"UTF-8","");
        Elements elements=  html.getElementsByTag("TEXT");
        for (Element element : elements) {
            System.out.println(element.text());
            parser.preparationToPaser(element.text());
        }
    }
}

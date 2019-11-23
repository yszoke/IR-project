package Parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class testsParser {

    public static void main(String[] args) throws IOException {
        int docIndex = 1;
        String pathForStopWords = "C:\\Users\\ohoff\\Documents\\information retrieval\\corpus";
        String sentence1 = "Lorem Ipsum is simply dummy text of the Printing and typesetting industry";
        String sentence2 = "The Mongolian Government and people gave wholehearted support to the " +
                "Vietnamese people in their struggle for national liberation in " +
                "the past as well as in national Vietnamese construction and defence at present";
        String sentence3 = " We also provide-static-- electricity protection for filling and draining devices";
        Parser parser = new Parser(docIndex,sentence1,pathForStopWords);
        File input = new File(pathForStopWords+"\\tests\\testForParse");
        //generalTest(input,parser);
        //numberTest(parser);
        entityTest(parser);
        //betweenTest(parser);




    }



    private static void entityTest(Parser parser) throws IOException {
        String sentence1 = "Lorem Ipsum is simply dummy text of the Printing and typesetting industry";
        String sentence2 = "The Mongolian Government and people gave wholehearted support to the " +
                            "Vietnamese people in their struggle for national liberation in " +
                                "the past as well as in national Vietnamese construction and defence at present";
        String sentence3 = " We also provide-static-- electricity protection for filling and draining devices";


        System.out.println(parser.addToEntity());

    }

    private static void numberTest(Parser parser) throws IOException {

    }

    private static void betweenTest(Parser parser) throws IOException {
        String sentence1 = "Lorem Ipsum is between 1 and 10 not Between 1 and 0 or between omer and israel simply dummy text of the printing and typesetting industry";
        String[] words = parser.splitToWords(sentence1);
        parser.parse(words);

    }

    private static void generalTest(File input, Parser parser) throws IOException {
        Document html = Jsoup.parse(input,"UTF-8","");
        Elements elements=  html.getElementsByTag("TEXT");
        for (Element element : elements) {
            System.out.println(element.text());
            //parser.parse(element.text());
        }
    }
}

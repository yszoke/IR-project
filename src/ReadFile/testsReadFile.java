package ReadFile;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * this class is for test the read files classes.
 */


public class testsReadFile {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        String path = "C:\\Users\\ohoff\\Documents\\information retrieval\\corpus";
        JsoupThreadStrategy(path);
        //JsoupStrategy(path);
        //standartStrategy(path);

        }

        private static void standartStrategy(String path)throws IOException{
            ReadFile readFile = new ReadFile(path);
            readFile.splitToDocs();
        }

        private static void JsoupStrategy(String path) throws IOException, InterruptedException {
            ReadFileJsoup readFileJsoup = new ReadFileJsoup(path);
            readFileJsoup.splitToDocs();
        }

        private static void JsoupThreadStrategy(String path) throws IOException, InterruptedException {
            ReadFileJsoupThreads readFileJsoupThreads = new ReadFileJsoupThreads(path,true, "posting");
            readFileJsoupThreads.splitToDocs();
        }
    }


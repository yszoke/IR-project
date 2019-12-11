package ReadFile;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * this class is for test the read files classes.
 */


public class testsReadFile {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        String path = "C:\\Users\\ohoff\\Documents\\information retrieval\\corpus";
        JsoupStrategy(path);
        //standartStrategy(path);

        }

        private static void standartStrategy(String path)throws IOException{
            ReadFile readFile = new ReadFile(path);
            readFile.splitToDocs();
        }

        private static void JsoupStrategy(String path) throws IOException {
            ReadFileJsoup readFileJsoup = new ReadFileJsoup(path);
            readFileJsoup.splitToDocs();
        }
    }


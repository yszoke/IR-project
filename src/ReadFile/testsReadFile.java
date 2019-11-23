package ReadFile;

import ReadFile.ReadFile;
import ReadFile.ReadFileJsoup;
import ReadFile.ReadFileXML;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class testsReadFile {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        //String path = "C:\\Users\\ohoff\\Documents\\information retrieval\\corpus";
        String path ="C:\\Users\\yszok\\Desktop\\IR project";
        //standartStrategy(path);
        JsoupStrategy(path);

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


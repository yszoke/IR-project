package ReadFile;

import Parse.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileJsoup implements ReadFileMethods {

    /**
     * variables
     */
    private File[] folders;
    private int indexDoc = 1;
    private String path;

    /**
     * constructor
     *
     * @param path
     */
    public ReadFileJsoup(String path) {
        this.folders = new File(path + "\\corpus").listFiles();
        this.path = path;
    }


    /**
     * this function takes the files from the folder and split them docs, then send only the text part to parse class
     *
     * @throws IOException
     */
    public void splitToDocs() throws IOException {
        for (File file : folders) {
            if (file.isDirectory()) {
                String doc = new String(Files.readAllBytes(Paths.get(file.getPath() + "\\" + file.getName())));
                Document html = Jsoup.parse(doc);
                Elements elements=  html.getElementsByTag("DOC");
                for (Element element : elements) {
                    Parser parser = new Parser(indexDoc,element.getElementsByTag("TEXT").text(),path);
                    parser.parse();
                    System.out.println(indexDoc);
                    indexDoc++;
                }
            }
        }
    }
}

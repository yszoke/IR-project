package ReadFile;

import Parse.Parser;
import invertedIndex.Dictionary;
import invertedIndex.MergeSorter;
import invertedIndex.MergeSorter;
import invertedIndex.SortedTables;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileJsoup extends Thread implements ReadFileMethods  {

    /**
     * variables
     */
    private File[] folders;
    private int indexDoc = 1;
    private String path;
    private Dictionary dictionary;
    private MergeSorter merge;

    /**
     * constructor
     *
     * @param path
     */
    public ReadFileJsoup(String path) {
        this.folders = new File(path + "\\Tests").listFiles();
        this.path = path;
        File f = new File("posting");
        f.mkdir();
        File f1 = new File("prePosting");
        f1.mkdir();
        this.merge = new MergeSorter(1);

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
                    if(element.getElementsByTag("TEXT").text().equals("")){
                        indexDoc++;
                    }else{
                        Parser parser = new Parser(indexDoc,element.getElementsByTag("TEXT").text(),path);
                        parser.parse();
                        System.out.println(indexDoc);
                        indexDoc++;
                    }
                }
            }
        }

        Parser.entityToSortedTable();
        SortedTables.addLastTable();
        File folder = new File("prePosting");
        File[] listOfFiles = folder.listFiles();
        merge.startMergingfiles(listOfFiles.length);
        System.out.println("");
    }
}
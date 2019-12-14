package ReadFile;

import Parse.Parser;
import invertedIndex.*;
import invertedIndex.MergeSorter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

public class ReadFileJsoupThreads extends Thread implements ReadFileMethods  {

    /**
     * variables
     */
    private File[] folders;
    private int indexDoc = 1;
    private String path;
    private Dictionary dictionary;
    private MergeSorter merge;
    private ArrayList<Integer> docIndexer;
    ArrayList<magicThreads> threadList = new ArrayList<>();

    /**
     * constructor
     *
     * @param path
     */
    public ReadFileJsoupThreads(String path) {
        this.folders = new File(path + "\\corpus").listFiles();
        this.path = path;
        File f = new File("posting");
        f.mkdir();
        File f1 = new File("prePosting");
        f1.mkdir();
        this.merge = new MergeSorter(1);
        this.docIndexer = new ArrayList<>();
        this.threadList = new ArrayList<>();


    }


    /**
     * this function takes the files from the folder and split them docs, then send only the text part to parse class
     *
     * @throws IOException
     */
    public void splitToDocs() throws IOException, InterruptedException {
        docIndexer.add(1);
        for (File file : folders) {

            String doc = null;
            doc = new String(Files.readAllBytes(Paths.get(file.getPath() + "\\" + file.getName())));
            Document html = Jsoup.parse(doc);
            Elements elements = html.getElementsByTag("DOC");
            int docNumber = docIndexer.get(docIndexer.size()-1)+elements.size();
            docIndexer.add(docNumber);

        }


        ExecutorService threadPool = newFixedThreadPool(16);
        List<Callable<Object>> todo = new ArrayList<Callable<Object>>();

        int currentIndexDoc = 0;
        for (File file : folders) {
            todo.add(Executors.callable(new magicThreads(file, docIndexer.get(currentIndexDoc),path)));
            currentIndexDoc++;

        }

        List<Future<Object>> answers = threadPool.invokeAll(todo);

        SortedTablesThreads sortedTablesThreads = new SortedTablesThreads();
        System.out.println("finish parser!----------------");
        sortedTablesThreads.entityToSortedTable();
        System.out.println("finish entity!----------------");
        SortedTablesThreads.addLastTable();
        System.out.println("finish last table!----------------");
        File folder = new File("prePosting");
        File[] listOfFiles = folder.listFiles();
        merge.startMergingfiles(listOfFiles.length);
        System.out.println("finish merge!----------------");
        listOfFiles = folder.listFiles();
        Dictionary dictionary = new Dictionary(listOfFiles[0]);
        dictionary.create();
        dictionary.saveInformation();
    }
}

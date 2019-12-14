package ReadFile;

import invertedIndex.*;
import invertedIndex.MergeSorter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    private String pathForData;
    private String pathForPosting;
    private String pathForPrePosting;
    private Dictionary dictionary;
    private MergeSorter merge;
    private ArrayList<Integer> docIndexer;
    ArrayList<magicThreads> threadList = new ArrayList<>();
    boolean stemming;

    /**
     * constructor
     *
     * @param pathForData
     */
    public ReadFileJsoupThreads(String pathForData, boolean stemming, String pathForPosting) {
        this.folders = new File(pathForData + "\\tests").listFiles();
        this.pathForData = pathForData;
        this.pathForPosting = pathForPosting;
        this.stemming = stemming;
        createFolders();
        this.merge = new MergeSorter(1,pathForPrePosting);
        this.docIndexer = new ArrayList<>();
        this.threadList = new ArrayList<>();



    }

    private void createFolders() {
        File f = new File(pathForData);
        f.mkdir();
        if (stemming){
            File f2 = new File("stemming");
            f2.mkdir();
            pathForPrePosting = "stemming";
        } else {
            File f2 = new File("withoutStemming");
            f2.mkdir();
            pathForPrePosting = "withoutStemming";
        }


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
            todo.add(Executors.callable(new magicThreads(file, docIndexer.get(currentIndexDoc), pathForData,stemming)));
            currentIndexDoc++;

        }

        List<Future<Object>> answers = threadPool.invokeAll(todo);

        SortedTablesThreads sortedTablesThreads = new SortedTablesThreads(stemming);
        System.out.println("finish parser!----------------");
        sortedTablesThreads.entityToSortedTable();
        System.out.println("finish entity!----------------");
        sortedTablesThreads.addLastTable();
        System.out.println("finish last table!----------------");
        File folder = new File(pathForPrePosting);
        File[] listOfFiles = folder.listFiles();
        merge.startMergingfiles(listOfFiles.length);
        System.out.println("finish merge!----------------");
        listOfFiles = folder.listFiles();
        Dictionary dictionary = new Dictionary(listOfFiles[0]);
        dictionary.create();
        dictionary.saveInformation();
    }
}

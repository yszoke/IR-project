package invertedIndex;

import java.io.File;
import java.io.IOException;

public class TestForMerge {

    public static void main(String[] args) throws IOException {
        MergeSorter merge = new MergeSorter(1);

        File folder = new File("posting");
        File[] listOfFiles = folder.listFiles();
        //merge.startMergingfiles(listOfFiles.length);

        Dictionary dictionary = new Dictionary(listOfFiles[0]);
        dictionary.create();
    }
}

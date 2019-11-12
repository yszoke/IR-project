package ReadFile;

import Doc.Doc;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadFile {
    protected File[] folders;
    private int index = 0;
    private ArrayList<Doc> docs;
    String allWords = "";
    protected ArrayList<String> allDocs;

    public ReadFile(String path) {

        this.folders = new File(path).listFiles();
        this.docs = new ArrayList<Doc>();
        this.allDocs = new ArrayList<>();

    }

    public void openFile() throws IOException {
        for (File file : folders) {
            if (file.isDirectory()) {

                try {
                    allDocs.add(new String(Files.readAllBytes(Paths.get(file.getPath() + "\\" + file.getName()))));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}



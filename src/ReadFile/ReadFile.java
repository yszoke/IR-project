package ReadFile;

import Doc.Doc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private ArrayList<Doc> docs;
    private File[] folders;

    public ReadFile(String path) {
        this.docs = new ArrayList<Doc>();
        this.folders = new File(path).listFiles();
    }

    public void openFile() throws IOException {
        for (File file : folders) {
            if (file.isDirectory()) {
                //System.out.println(file.getPath()+"\\"+ file.getName());
                //File file1 = new File(file.getPath()+"\\"+ file.getName());
                //String data = "";
                //data = new String(Files.readAllBytes(Paths.get(file.getPath()+"\\"+ file.getName())));
                //saveDoc(data);
                File file1=new File(file.getPath()+"\\"+ file.getName());
                Scanner input=new Scanner(file1);
                input.useDelimiter("\n"); //delimitor is one or more spaces
                input.useDelimiter(" +"); //delimitor is one or more spaces

                ArrayList<String> lisrOfWords=new ArrayList<>();
                while(input.hasNext()){
                    //System.out.println(input.next());
                    lisrOfWords.add(input.next());

                }
                saveDoc(lisrOfWords);

            }
        }
    }



    private void saveDoc(ArrayList<String> lisrOfWords) {
        //System.out.println(file.getPath());
        ArrayList<String> lisrOfText = new ArrayList<>();
        int index = 0;
        String word = lisrOfWords.get(index);

        while (word != null) {
            if (word.equals("<TEXT>")) {
                while (!word.equals("</TEXT>") && word != null) {
                    lisrOfText.add(lisrOfWords.get(index));
                    index++;
                    word = lisrOfWords.get(index);
                }
            }
            index++;
            word = lisrOfWords.get(index);
        }

    }
}


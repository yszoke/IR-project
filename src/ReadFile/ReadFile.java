package ReadFile;

import Doc.Doc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private File[] folders;
    private int index=0;
    private ArrayList<Doc> docs;
    String allWords="";
    protected ArrayList<String> allDocs;

    public ReadFile(String path) {
        this.folders = new File(path).listFiles();
        this.docs=new ArrayList<Doc>();
        this.allDocs=new ArrayList<>();

    }

    public void openFile() throws IOException {
        for (File file : folders) {
            if (file.isDirectory()) {
                /*
                File file1=new File(file.getPath()+"\\"+ file.getName());
                Scanner input=new Scanner(file1);

                ArrayList<String> lisrOfWords=new ArrayList<>();
                while(input.hasNext()){
                    lisrOfWords.add(input.next());

                }
                if(!lisrOfWords.isEmpty()){
                    saveDoc(lisrOfWords);
                }
                */
                //File file1=new File(file.getPath()+"\\"+ file.getName());
                try
                {

                    allDocs.add(new String ( Files.readAllBytes( Paths.get(file.getPath()+"\\"+ file.getName()) ) ));
                    //index++;
                    //System.out.println(index);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }




            }
        }
    }



    private void saveDoc(ArrayList<String> lisrOfWords) throws FileNotFoundException, UnsupportedEncodingException {
        //System.out.println(file.getPath());
        index = 0;

        String word = lisrOfWords.get(index);

        while (word != null) {
            if (word.equals("<TEXT>")) {
                index++;
                //ArrayList<String> lisrOfText = new ArrayList<>();
                String lisrOfText="";
                while (!word.equals("</TEXT>") && word != null) {
                    //lisrOfText.add(lisrOfWords.get(index));
                    lisrOfText= lisrOfText + " " +lisrOfWords.get(index);
                    index++;
                    if(index==lisrOfWords.size()) {
                        break;
                    }
                    word = lisrOfWords.get(index);
                }
                //allDocs+=lisrOfText;
                //Doc doc= new Doc(lisrOfText);
                //docs.add(doc);


            }
            index++;
            if(index>=lisrOfWords.size()) {
                break;
            }
            word = lisrOfWords.get(index);


        }


    }
}


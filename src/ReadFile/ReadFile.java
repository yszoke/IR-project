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
    private int docNum=1;

    public ReadFile(String path) {
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
                //input.useDelimiter("\n"); //delimitor is one or more spaces
                //input.useDelimiter(" +,\n"); //delimitor is one or more spaces

                ArrayList<String> lisrOfWords=new ArrayList<>();
                while(input.hasNext()){
                    //System.out.println(input.next());
                    lisrOfWords.add(input.next());

                }
                if(!lisrOfWords.isEmpty()){
                    saveDoc(lisrOfWords);
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

                Doc doc= new Doc(lisrOfText);
                File file = new File("logs1/file.txt");
                File f = new File("directory/fileName.txt");


                PrintWriter writer = new PrintWriter("documents//"+docNum+".txt", "UTF-8");
                writer.println(doc.getText());
                writer.close();

                docNum++;

            }
            index++;
            if(index>=lisrOfWords.size()) {
                break;
            }
            word = lisrOfWords.get(index);


        }


    }
}


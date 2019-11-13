package ReadFile;

import Parse.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * this class read files from a directory, split the words, give the words a document number and send them to parse
 */
public class ReadFile {
    /**
     * variables
     */
    private File[] folders;
    private int indexDoc = 1;
    private String line;
    private String path;

    /**
     * constructor
     * @param path
     */
    public ReadFile(String path) {
        this.folders = new File(path+"\\tests").listFiles();
        this.path=path;
    }


    /**
     * this function takes the files from the folder and split them to lines and send each line to the split lines function
     * @throws IOException
     */
    public void splitToText() throws IOException {
        for (File file : folders) {
            if (file.isDirectory()) {
                String doc = new String(Files.readAllBytes(Paths.get(file.getPath() + "\\" + file.getName())));
                BufferedReader bufReader = new BufferedReader(new StringReader(doc));
                while ((line = bufReader.readLine()) != null) {
                    while (line != null && !line.equals("<TEXT>")) {
                        line = bufReader.readLine();
                    }
                    if (line != null && line.equals("<TEXT>")) {
                        line = bufReader.readLine();
                    }
                    String text = "";
                    while (line != null && !line.equals("</TEXT>")) {
                        while (line.equals("")) {
                            line = bufReader.readLine();
                        }

                        if (line != null && !line.equals("</TEXT>")) {
                            text+=line;

                            //splitToWords(line);
                            line = bufReader.readLine();
                        }
                    }
                    System.out.println(line);
                    if (line != null) {
                        Parser parser = new Parser(indexDoc,path);
                        parser.splitTextToSentence(text);
                        indexDoc++;
                        System.out.println(indexDoc);
                    }
                    //file ends or arrive at </text>
                }
            }
        }
    }

}
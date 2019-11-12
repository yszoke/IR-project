package ReadFile;

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

    /**
     * constructor
     * @param path
     */
    public ReadFile(String path) {
        this.folders = new File(path).listFiles();
    }

    /**
     * this function takes the files from the folder and split them to lines and send each line to the split lines function
     * @throws IOException
     */
    public void splitToLines() throws IOException {
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
                    while (line != null && !line.equals("</TEXT>")) {
                        while (line.equals("")) {
                            line = bufReader.readLine();
                        }
                        if (line != null && !line.equals("</TEXT>")) {
                            splitToWords(line);
                            line = bufReader.readLine();
                        }
                    }
                    System.out.println(line);
                    if (line != null) {
                        indexDoc++;
                        System.out.println(indexDoc);
                    }
                    //file ends or arrive at </text>
                }
            }
        }
    }
    /**
     * this function take a line and split to list of words, and send each word to a parse function
     * @param line
     */
    private void splitToWords(String line) {
        String lineOfWords[] = line.split(" +");
        for (String parseWord : lineOfWords) {
            //parseWord = Parse(parseWord);
            //add parseWord to index
        }
    }
}

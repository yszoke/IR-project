package ReadFile;

import Parse.Sentences;

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
    private Sentences sentence;

    /**
     * constructor
     * @param path
     */
    public ReadFile(String path) {
        this.folders = new File(path+"\\corpus").listFiles();
        this.sentence = new Sentences();
    }


    /**
     * this function takes the files from the folder and split them to lines and send each line to the split lines function
     * @throws IOException
     */
    public void splitToDocs() throws IOException {
        for (File file : folders) {
            if (file.isDirectory()) {
                String doc = new String(Files.readAllBytes(Paths.get(file.getPath() + "\\" + file.getName())));

                BufferedReader bufReader = new BufferedReader(new StringReader(doc));
                while ((line = bufReader.readLine()) != null) {
                    while (line != null && !line.equals("<DOC>")) {
                        line = bufReader.readLine();
                    }
                    if (line != null && line.equals("<DOC>")) {
                        line = bufReader.readLine();
                    }
                    String doc = "";
                    while (line != null && !line.equals("</DOC>")) {
                        while (line.equals("")) {
                            line = bufReader.readLine();
                        }
                        if (line != null && !line.equals("</DOC>")) {
                            doc+=line;
                            //splitToWords(line);
                            line = bufReader.readLine();
                        }
                    }

                    if (line != null) {
                        sentence.breakIntoText(doc);
                        indexDoc++;
                        System.out.println(indexDoc);
                    }
                    //file ends or arrive at </doc>
                }
            }
        }
    }

    private void breakToSentences(String text) {
        String sentences[] = text.split("\\.|, |\\?|\\!");
        for(int i=0;i<sentences.length;i++){
            //System.out.println(sentences[i]);
        }

    }

    /**
     * this function take a line and split to list of words, and send each word to a parse function
     * @param line
     */
    private void splitToWords(String line) {
        String lineOfWords[] = line.split(" +");
        for (String parseWord : lineOfWords) {
            if(parseWord.equals("")){
                continue;
            }
            char lastChar = parseWord.charAt(parseWord.length()-1);
            if(lastChar==','||lastChar =='.'||lastChar==':'){
                parseWord=parseWord.substring(0,parseWord.length()-1);
            }

            //parseWord = Parse(parseWord);
            //add parseWord to index
        }
    }
}

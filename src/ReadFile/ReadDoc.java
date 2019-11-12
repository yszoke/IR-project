package ReadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadDoc extends ReadFile {

    private int indexDoc=1;
    private String line;

    /**
     * constructor
     * @param path
     */
    public ReadDoc(String path) {
        super(path);
    }

    /**
     *
     * @throws IOException
     */
    public void devideToLines() throws IOException {
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
                            sendToParse(line);
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

    private void sendToParse(String line) {
        String lineOfWords[] = line.split(" +");
        for (String parseWord: lineOfWords){
            //parseWord = Parse(parseWord);
            //add parseWord to index


        }

    }
}

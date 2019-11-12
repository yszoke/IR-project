import ReadFile.ReadFile;
import ReadFile.ReadDoc;

import java.io.File;
import java.io.IOException;

public class tests {

    public static void main(String[] args) throws IOException {
        //File file = new File("omer");
        //file.mkdir();
        //ReadFile readFile=new ReadFile("C:\\Users\\ohoff\\Documents\\information retrieval\\corpus\\corpus");
        ReadDoc readDoc = new ReadDoc("C:\\Users\\ohoff\\Documents\\information retrieval\\corpus\\corpus");
        readDoc.openFile();
        readDoc.devideTolines();
        }
    }


import ReadFile.ReadFile;
import ReadFile.ReadDoc;

import java.io.File;
import java.io.IOException;

public class tests {

    public static void main(String[] args) throws IOException {
        ReadDoc readDoc = new ReadDoc("C:\\Users\\yszok\\Desktop\\IR project\\corpus");
        readDoc.devideToLines();
        }
    }


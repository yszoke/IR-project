import ReadFile.ReadFile;

import java.io.File;
import java.io.IOException;

public class tests {

    public static void main(String[] args) throws IOException {
        ReadFile readFile = new ReadFile("C:\\Users\\yszok\\Desktop\\IR project");
        readFile.splitToLines();
        }
    }


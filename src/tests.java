import ReadFile.ReadFile;


import java.io.IOException;

public class tests {

    public static void main(String[] args) throws IOException {
        ReadFile readFile = new ReadFile("C:\\Users\\ohoff\\Documents\\information retrieval\\corpus");
        readFile.splitToText();
        }
    }


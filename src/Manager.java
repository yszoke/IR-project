import ReadFile.ReadFile;

import java.io.IOException;

public class Manager {

    public void execute(String path) throws IOException {
        ReadFile readFile = new ReadFile("C:\\Users\\ohoff\\Documents\\information retrieval\\corpus");
        readFile.splitToDocs();

    }
}

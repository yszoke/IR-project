package invertedIndex;

import java.io.*;

public class testPosings {

    public static void main(String[] args){
        String line = "";
        int lineNo;
        try {
            File f=new File("C:\\Users\\ohoff\\Documents\\information retrieval\\test\\text.txt");
            FileWriter fw = new FileWriter(f,true);
            BufferedWriter bw = new BufferedWriter(fw);
            LineNumberReader lnr = new LineNumberReader(new FileReader(f));
            lnr.setLineNumber(2);
            String is = lnr.readLine();

            bw.write("Hello World");
            bw.close();
            lnr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


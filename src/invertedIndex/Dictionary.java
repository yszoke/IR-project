package invertedIndex;

import java.io.*;
import java.util.HashMap;

public class Dictionary {

    File file;
    private HashMap<String, String> dictionary = new HashMap<>();


    public Dictionary(File file) {
        this.file = file;
    }

    public void create() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] line;
        int counterFile = 0;
        int counterLine = 0;
        int pointerLine = 1;
        int Ndocs=0;
        String previousWord="";
        String currentWord="";
        String ln=reader.readLine();
        while (ln != null ) {
            while (ln.equals("")||ln.charAt(0)==' '){
                ln=reader.readLine();
            }
            counterFile++;
            System.out.println(counterFile);

            FileWriter pw = new FileWriter("posting/" + counterFile + ".txt", false);

            counterLine = 0;
            while (ln != null &&(counterLine < 100000)) {
                line = ln.split(" ");
                currentWord=calculateWord(line);
                if(currentWord.equals(previousWord)){
                    Ndocs++;
                }
                else{
                    dictionary.put(previousWord, Ndocs+"-"+counterFile+"-"+pointerLine);
                    pointerLine=counterLine;
                    previousWord=currentWord;
                    Ndocs=1;
                }
                pw.write(ln+"\r\n");
                counterLine++;
                ln=reader.readLine();
            }
            pw.close();
            ln=reader.readLine();
        }
        System.out.println(" ");
    }
    private String calculateWord(String[] line) {
        if(line.length>3){
            String ans =line[0];
            for(int i=1;i<line.length-2;i++){
                ans = ans +" "+ line[i];
            }
            return ans;
        }else
            {
            return line[0];
        }
    }
}

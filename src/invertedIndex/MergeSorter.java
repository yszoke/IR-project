package invertedIndex;

import java.io.*;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class MergeSorter extends Thread {
    int numberOfFiles;
    int fileNumber = 1;
    static int counter;

    public MergeSorter(int fileNum) {
        fileNumber = fileNum;
    }

    public void startMergingfiles(int numberOfFiles) {
        int objectcounter = 0;
        counter = numberOfFiles+1;
        while (numberOfFiles > 1) {
            try {
                ArrayList<MergeSorter> objectList = new ArrayList<MergeSorter>();
                File folder = new File("posting");
                SortedSet<Integer> files = new TreeSet<>();
                File[] listOfFiles = folder.listFiles();
                for(int i=0;i<listOfFiles.length;i++){
                    String s = listOfFiles[i].getName();
                    s = s.replace(".txt","");
                    files.add(Integer.parseInt(s));
                }

                numberOfFiles = files.size();

                for (int j = files.first() ;j < files.last(); j = j + 2) {
                    objectList.add(new MergeSorter(j));
                    objectList.get(objectcounter).start();
                    objectList.get(objectcounter).join();
                    objectcounter++;
                }
                objectcounter = 0;

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public void run() {

        try {
            // PrintWriter object for file3.txt
            FileWriter pw = new FileWriter("posting/" + Integer.toString(counter) + ".txt", false);
            counter++;
            String lastWord = "";
            String lastIndexDoc ="";
            // BufferedReader object for file1.txt
            BufferedReader br1 = new BufferedReader(new FileReader("posting/" + Integer.toString(fileNumber)+".txt"));
            BufferedReader br2 = new BufferedReader(new FileReader("posting/" + Integer.toString(fileNumber + 1)+".txt"));

            String previousLine = "";

            String line1 = br1.readLine();
            if(line1.equals("")){
                line1 = br1.readLine();
            }
            String line2 = br2.readLine();
            if(line2.equals("")){
                line2 = br2.readLine();
            }
            //String[] lineA=new String[3];
            // loop to copy lines of
            // file1.txt and file2.txt
            // to  file3.txt alternatively
            while (line1 != null || line2 !=null)
            {
                if(line1 == null) {
                    String[] lineB = line2.split(" ");
                    String word2 = calculateWord(lineB);
                    String docIndex2 = lineB[lineB.length-2];
                    if (word2.equals(lastWord)&& docIndex2.equals(lastIndexDoc)){
                        previousLine = previousLine+","+ lineB[lineB.length-1];
                    }else{
                        pw.write(previousLine+"\r\n");
                        previousLine = line2;
                        lastWord = word2;
                        lastIndexDoc = docIndex2;
                    }
                    line2 = br2.readLine();

                }
                else if(line2 == null) {
                    String[] lineA = line1.split(" ");
                    String word1 = calculateWord(lineA);
                    String docIndex1 = lineA[lineA.length-2];
                    if (word1.equals(lastWord)&& docIndex1.equals(lastIndexDoc)){
                        previousLine = previousLine+","+ lineA[lineA.length-1];
                    }else{
                        pw.write(previousLine+"\r\n");
                        previousLine = line1;
                        lastWord = word1;
                        lastIndexDoc = docIndex1;
                    }
                    line1 = br1.readLine();
                }
                else{
                    String[] lineA = line1.split(" ");
                    String[] lineB = line2.split(" ");
                    String word1 = calculateWord(lineA);
                    String word2 = calculateWord(lineB);
                    String docIndex1 = lineA[lineA.length-2];
                    String docIndex2 = lineB[lineB.length-2];

                    //choose LineA
                    if((word1.compareTo(word2)<0)||(word1.equals(word2)&& Integer.parseInt(docIndex1)<Integer.parseInt(docIndex2))){
                        //todo check if equals to the last string and doc->print to last line
                        if (lastIndexDoc.equals(docIndex1) && lastWord.equals(word1)){
                            previousLine = previousLine+","+ lineA[lineA.length-1];
                        }else{
                            pw.write(previousLine+"\r\n");
                            previousLine = line1;
                            lastWord = word1;
                            lastIndexDoc = docIndex1;
                        }
                        line1 = br1.readLine();

                    }
                    else if((word1.compareTo(word2)>0)||(word1.equals(word2)&&Integer.parseInt(docIndex1)>Integer.parseInt(docIndex2))){
                        //todo check if equals to the last string and doc->print to last line
                        if (lastIndexDoc.equals(docIndex2) && lastWord.equals(word2)){
                            previousLine = previousLine+","+ lineB[lineB.length-1];
                        }else{
                            pw.write(previousLine+"\r\n");
                            previousLine = line2;
                            lastWord = word2;
                            lastIndexDoc = docIndex2;
                        }
                        line2 = br2.readLine();
                    }
                }
            }
            pw.write(previousLine+"\r\n");
            pw.flush();
            // closing resources
            br1.close();
            br2.close();
            pw.close();
            File file1 = new File("posting/" + Integer.toString(fileNumber)+".txt");
            file1.delete();
            File file2 = new File("posting/" + Integer.toString(fileNumber+1)+".txt");
            file2.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculateWord(String[] line) {
        if(line.length>3){
            String ans =line[0];
            for(int i=1;i<line.length-2;i++){
                ans = ans +" "+ line[i];
            }
            return ans;
        }else{
            return line[0];
        }
    }
}

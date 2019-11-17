package ReadFile;

import Parse.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReadFileXML {

    /**
     * variables
     */
    private File[] folders;
    private int indexDoc = 1;
    private String line;
    private String path;

    /**
     * constructor
     * @param path
     */
    public ReadFileXML(String path) {
        this.folders = new File(path+"\\corpus").listFiles();
        this.path=path;
    }


    /**
     * this function takes the files from the folder and split them to lines and send each line to the split lines function
     * @throws IOException
     */
    public void splitToText() throws IOException, ParserConfigurationException, SAXException {
        for (File file : folders) {
            if (file.isDirectory()) {
                List<InputStream> streams = Arrays.asList(
                        new ByteArrayInputStream("<root>".getBytes()),
                        new FileInputStream(file.getPath() + "\\" + file.getName()),
                        new ByteArrayInputStream("</root>".getBytes())
                );
                InputStream is = new SequenceInputStream(Collections.enumeration(streams));

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                //documentBuilderFactory.setIgnoringElementContentWhitespace(true);
                //documentBuilderFactory.setIgnoringComments(true);
                //documentBuilderFactory.setAttribute("F P=100","F");
                Document doc = documentBuilderFactory.newDocumentBuilder().parse(is);
                //NodeList children = doc.getDocumentElement().getChildNodes();
                NodeList children = doc.getElementsByTagName("DOC");
                //System.out.println(children.getLength());
                for(int i=0; i<children.getLength(); i++){
                    Node child = children.item(i);
                    //Parser parser = new Parser(indexDoc,path);
                    //parser.splitTextToSentence(child.getTextContent());
                    indexDoc++;
                    System.out.println(indexDoc);
                }
            }
        }

    }
}

package Doc;

import java.util.ArrayList;

public class Doc {

    private String DocNumber;
    private String HT;
    private String Header;
    private ArrayList<String> Text;

    public ArrayList<String> getText() {
        return Text;
    }

    public void setText(ArrayList<String> text) {
        Text = text;
    }

    public Doc(ArrayList<String> text) {
        Text = text;
    }

    public String getDocNumber() {
        return DocNumber;
    }

    public void setDocNumber(String docNumber) {
        DocNumber = docNumber;
    }

    public String getHT() {
        return HT;
    }

    public void setHT(String HT) {
        this.HT = HT;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }


}

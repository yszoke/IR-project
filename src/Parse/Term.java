package Parse;

import java.util.*;

public class Term {

    private String contant;
    private boolean isSmall;
    private int position;

    private SortedMap<Integer, List<Integer>>docList;

    public Term(String contant, int docNum) {
        this.contant = contant;
        this.docList = new TreeMap<>();
        this.add(docNum);
    }
    public Term(String contant, int docNum,int positionInDoc) {
        this.contant = contant;
        this.docList = new TreeMap<>();
        this.position = positionInDoc;
        this.add(docNum);
    }

    public Term add(int docNum) {
        List<Integer> list = new ArrayList<>();
        this.docList.put(docNum,list);
        return this;
    }

    public boolean isSmall() {
        return isSmall;
    }

    public void setSmall(boolean small) {
        isSmall = small;
    }

    public SortedMap<Integer, List<Integer>> getDocList() {
        return docList;
    }

    public void setDocList(SortedMap<Integer, List<Integer>> docList) {
        this.docList = docList;
    }
}

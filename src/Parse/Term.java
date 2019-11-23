package Parse;

import java.util.*;

public class Term {

    private String contant;
    private boolean isSmall;
    private SortedMap<Integer, List<Integer>>docList;

    public Term(String contant, int docNum) {
        this.contant = contant;
        this.docList = new TreeMap<>();
        this.add(docNum);
    }

    public Term add(int docNum) {
        List<Integer> list = new ArrayList<>();
        this.docList.put(docNum,list);
        return this;
    }
}

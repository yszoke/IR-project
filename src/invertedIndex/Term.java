package invertedIndex;

import java.util.*;

public class Term {

    private int NDocs;
    private int lineInPosting;


    public Term(int postingPosition ) {
        this.lineInPosting = postingPosition;
    }

    public int getNDocs() {
        return NDocs;
    }

    public void increaseNDocs() {
        NDocs++;
    }

    public int getLineInPosting() {
        return lineInPosting;
    }
}

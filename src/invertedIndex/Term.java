package invertedIndex;

import java.util.*;

public class Term {

    private int NDocs;
    private int lineInPosting;

    public Term(int numOfDocs, int postingPosition ) {
        this.NDocs= numOfDocs;
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

    public void setNDocs(int NDocs) {
        this.NDocs = NDocs;
    }

    public void setLineInPosting(int lineInPosting) {
        this.lineInPosting = lineInPosting;
    }
}

package invertedIndex;

import Parse.Term;

import java.util.HashMap;

public class Dictionary {

    private HashMap<String, Term> dictionary;

    public Dictionary() {
        this.dictionary = new HashMap<>();
    }

    public void addTermToDictionary(Term term){

    }

    /**
     * this method checks if a term is already in the dictionary
     * @param term
     * @return
     */
    public boolean isInDictionary(Term term){
        return true;
    }

    /**
     * this method checks for a given term and a doc index - if the term existed in the doc index
     * @param term
     * @param docIndex
     * @return
     */
    public boolean isInDocIndex(Term term, int docIndex){
        return true;
    }

}

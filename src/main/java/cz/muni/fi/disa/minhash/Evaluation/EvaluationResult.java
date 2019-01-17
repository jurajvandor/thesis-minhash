package cz.muni.fi.disa.minhash.Evaluation;

import cz.muni.fi.disa.minhash.QueryExecutors.QueryResult;

public class EvaluationResult {
    protected int sameItems;
    protected QueryResult first;
    protected QueryResult second;

    public EvaluationResult(int sameItems, QueryResult first, QueryResult second) {
        this.sameItems = sameItems;
        this.first = first;
        this.second = second;
    }

    public int getSameItems() {
        return sameItems;
    }

    public void setSameItems(int sameItems) {
        this.sameItems = sameItems;
    }

    public QueryResult getFirst() {
        return first;
    }

    public void setFirst(QueryResult first) {
        this.first = first;
    }

    public QueryResult getSecond() {
        return second;
    }

    public void setSecond(QueryResult second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return sameItems + "," + first.getExecutionTime() + "," + second.getExecutionTime() + ",,";
    }

    public static String getCsvHeader(){
        return "SameItems,FirstTime,SecondTime,FirstInCat,SecondInCat";
    }
}

package cz.muni.fi.disa.minhash.Evaluation;

import cz.muni.fi.disa.minhash.QueryExecutors.*;

public class Evaluator {

    private QueryExecutor first;
    private QueryExecutor second;

    public Evaluator(QueryExecutor first, QueryExecutor second) {
        this.first = first;
        this.second = second;
    }

    public QueryExecutor getFirst() {
        return first;
    }

    public void setFirst(QueryExecutor first) {
        this.first = first;
    }

    public QueryExecutor getSecond() {
        return second;
    }

    public void setSecond(QueryExecutor second) {
        this.second = second;
    }

    public EvaluationResult executeAndEvaluate(int numberOfRequestedItems, String queryItemId){
        QueryResult firstResult = first.findSimilarItems(numberOfRequestedItems, queryItemId);
        QueryResult secondResult = second.findSimilarItems(numberOfRequestedItems, queryItemId);
        int count = 0;
        for (QueryResultItem i : firstResult.getItems()){
            for (QueryResultItem j : secondResult.getItems())
                if (i.getId().equals(j.getId())){
                    count++;
                }
        }
        return new EvaluationResult(count, firstResult, secondResult);
    }

    public EvaluationMotionResult executeAndEvaluateMotion(int numberOfRequestedItems, String queryItemId, boolean ignorePngFirstQuery){
        String cat = getCat(queryItemId);
        QueryResult firstResult;
        if (ignorePngFirstQuery)
            firstResult = first.findSimilarItems(numberOfRequestedItems, queryItemId.replace(".png", ""));
        else
            firstResult = first.findSimilarItems(numberOfRequestedItems, queryItemId);
        QueryResult secondResult = second.findSimilarItems(numberOfRequestedItems, queryItemId);
        int count = 0;
        int catFirst = 0;
        int catSecond = 0;
        for (QueryResultItem i : firstResult.getItems()){
            if (cat.equals(getCat(i.getId())))
                catFirst++;
            for (QueryResultItem j : secondResult.getItems())
                if (ignorePngFirstQuery) {
                    if (i.getId().equals(j.getId().replace(".png", "")))
                        count++;
                }else if (i.getId().equals(j.getId()))
                    count++;
        }
        for (QueryResultItem j : secondResult.getItems())
            if (cat.equals(getCat(j.getId())))
                catSecond++;
        return new EvaluationMotionResult(count, firstResult, secondResult, catFirst, catSecond);
    }

    private String getCat(String id){
        return id.split("_")[1];
    }

}
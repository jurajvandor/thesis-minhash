package cz.muni.fi.disa.minhash.Evaluation;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.QueryExecutors.*;

public class Evaluator {
    public static void main(String[] args){
        try {
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/objects-annotations-specific-coords_normPOS_minhash_4_4096.data", " ", 4096);
            MinhashQueryExecutor minhash = new MinhashQueryExecutor(loader);
            ReferenceQueryExecutor ref = new ReferenceQueryExecutor(new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096));
            (new Evaluator(minhash, ref)).executeAndEvaluate(5,"3136_100_1245_236.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(10,"3136_100_1245_236.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(20,"3136_100_1245_236.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(50,"3136_100_1245_236.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(5,"3136_101_1708_81.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(10,"3136_101_1708_81.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(20,"3136_101_1708_81.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(50,"3136_101_1708_81.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(5,"3150_106_2375_266.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(10,"3150_106_2375_266.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(20,"3150_106_2375_266.png");
            (new Evaluator(minhash, ref)).executeAndEvaluate(50,"3150_106_2375_266.png");
        } catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }


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

    public EvaluationResult executeAndEvaluateMotion(int numberOfRequestedItems, String queryItemId){
        String cat = getCat(queryItemId);
        QueryResult firstResult = first.findSimilarItems(numberOfRequestedItems, queryItemId.replace(".png", ""));
        QueryResult secondResult = second.findSimilarItems(numberOfRequestedItems, queryItemId);
        int count = 0;
        int catFirst = 0;
        int catSecond = 0;
        for (QueryResultItem i : firstResult.getItems()){
            if (cat.equals(getCat(i.getId())))
                catFirst++;
            for (QueryResultItem j : secondResult.getItems())
                if (i.getId().equals(j.getId().replace(".png", ""))){
                    count++;
                }
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
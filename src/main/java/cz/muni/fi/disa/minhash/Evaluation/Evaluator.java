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
        QueryResult minhashResult = first.findSimilarItems(numberOfRequestedItems, queryItemId.replace(".png", ""));
        QueryResult referenceResult = second.findSimilarItems(numberOfRequestedItems, queryItemId);
        int count = 0;
        for (QueryResultItem i : minhashResult.getItems()){
            for (QueryResultItem j : referenceResult.getItems())
                if (i.getId().equals(j.getId().replace(".png", ""))){
                    count++;
                }
        }
        System.out.println("same:" + count + ", times: " + minhashResult.getExecutionTime() + " " + referenceResult.getExecutionTime());
        return new EvaluationResult(minhashResult.getExecutionTime(), referenceResult.getExecutionTime(), count);
    }
}
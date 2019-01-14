package cz.muni.fi.disa.minhash.Evaluation;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.QueryResult;
import cz.muni.fi.disa.minhash.QueryExecutors.QueryResultItem;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

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


    private MinhashQueryExecutor minhash;
    private ReferenceQueryExecutor euclidean;

    public Evaluator(MinhashQueryExecutor minhash, ReferenceQueryExecutor euclidean) {
        this.minhash = minhash;
        this.euclidean = euclidean;
    }

    public MinhashQueryExecutor getMinhash() {
        return minhash;
    }

    public void setMinhash(MinhashQueryExecutor minhash) {
        this.minhash = minhash;
    }

    public ReferenceQueryExecutor getEuclidean() {
        return euclidean;
    }

    public void setEuclidean(ReferenceQueryExecutor euclidean) {
        this.euclidean = euclidean;
    }

    public void executeAndEvaluate(int numberOfRequestedItems, String queryItemId){
        QueryResult minhashResult = minhash.findSimilarItems(numberOfRequestedItems, queryItemId.replace(".png", ""));
        QueryResult referenceResult = euclidean.findSimilarItems(numberOfRequestedItems, queryItemId);
        int count = 0;
        for (QueryResultItem i : minhashResult.getItems()){
            for (QueryResultItem j : referenceResult.getItems())
                if (i.getId().equals(j.getId().replace(".png", ""))){
                    count++;
                }
        }
        System.out.println("same:" + count + ", times: " + minhashResult.getExecutionTime() + " " + referenceResult.getExecutionTime());
    }
}
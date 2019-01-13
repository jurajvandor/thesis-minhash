package cz.muni.fi.disa.minhash.MinhashEvaluators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.QueryResultItem;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;
import javafx.util.Pair;

import java.util.SortedSet;

public class EvaluatorMinhashVsEuclidean {
    public static void main(String[] args){
        try {
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/objects-annotations-specific-coords_normPOS_minhash_4_2048.data", " ", 2048);
            MinhashQueryExecutor minhash = new MinhashQueryExecutor(loader);
            ReferenceQueryExecutor ref = new ReferenceQueryExecutor(new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096));
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(5,"3136_100_1245_236.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(10,"3136_100_1245_236.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(20,"3136_100_1245_236.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(50,"3136_100_1245_236.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(5,"3136_101_1708_81.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(10,"3136_101_1708_81.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(20,"3136_101_1708_81.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(50,"3136_101_1708_81.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(5,"3150_106_2375_266.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(10,"3150_106_2375_266.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(20,"3150_106_2375_266.png");
            (new EvaluatorMinhashVsEuclidean(minhash, ref)).executeAndEvaluate(50,"3150_106_2375_266.png");
        } catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }


    private MinhashQueryExecutor minhash;
    private ReferenceQueryExecutor euclidean;

    public EvaluatorMinhashVsEuclidean(MinhashQueryExecutor minhash, ReferenceQueryExecutor euclidean) {
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
        Pair<SortedSet<QueryResultItem>, Long> minhashResult = minhash.findSimilarItems(numberOfRequestedItems, queryItemId.replace(".png", ""));
        Pair<SortedSet<QueryResultItem>, Long> referenceResult = euclidean.findSimilarItems(numberOfRequestedItems, queryItemId);
        int count = 0;
        for (QueryResultItem i : minhashResult.getKey()){
            for (QueryResultItem j : referenceResult.getKey())
                if (i.getId().equals(j.getId().replace(".png", ""))){
                    count++;
                }
        }
        System.out.println("same:" + count + ", times: " + minhashResult.getValue() + " " + referenceResult.getValue());
    }
}
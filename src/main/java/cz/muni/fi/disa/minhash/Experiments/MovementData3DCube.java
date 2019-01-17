package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.Evaluation.EvaluationResult;
import cz.muni.fi.disa.minhash.Evaluation.Evaluator;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import java.util.List;
import java.util.Random;

public class MovementData3DCube {
    public static void main(String[] args){
        try{
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/objects-annotations-specific-coords_normPOS_minhash_4_2048.data", " ", 2048);
            MinhashQueryExecutor minhash = new MinhashQueryExecutor(loader);
            FloatVectorLoader loader1 = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor ref = new ReferenceQueryExecutor(loader1);
            Evaluator ev = new Evaluator(minhash, ref);
            Random rand = new Random();
            List<FloatVectorData> l = loader1.loadAllVectorsToList();
            long timeFirst = 0;
            long timeSecond = 0;
            int sameItems = 0;
            int len = 100;
            for (int i = 0; i < len; i++){
                EvaluationResult result = ev.executeAndEvaluate(20, l.get(rand.nextInt(l.size())).getId());
                timeFirst += result.getFirst().getExecutionTime();
                timeSecond += result.getSecond().getExecutionTime();
                sameItems += result.getSameItems();
            }
            System.out.println("same: " + sameItems/(float)len + " times: " + timeFirst/len + " " + timeSecond/len);
        } catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }
}

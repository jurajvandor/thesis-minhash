package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.Evaluation.Evaluator;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import java.util.List;
import java.util.Random;

public class Quantization {
    public static void main(String[] args){
        try{
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_2_2048.data", " ", 2048);
            MinhashQueryExecutor minhash = new MinhashQueryExecutor(loader);
            FloatVectorLoader loader2 = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            ReferenceQueryExecutor ref = new ReferenceQueryExecutor(loader2);
            Evaluator ev = new Evaluator(minhash, ref);
            Random rand = new Random();
            List<FloatVectorData> l = loader2.loadAllVectorsToList();
            for (int i = 0; i < 20; i++){
                ev.executeAndEvaluate(20, l.get(rand.nextInt(l.size())).getId());
            }
        } catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }
}

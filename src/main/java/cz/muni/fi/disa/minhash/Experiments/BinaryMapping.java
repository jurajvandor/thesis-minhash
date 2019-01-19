package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.MinhashCreators.AbstractMinhashCreator;
import cz.muni.fi.disa.minhash.MinhashCreators.BinaryMappingMinhashCreator;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import java.io.File;

public class BinaryMapping {
    public static void main(String[] args){
        try {
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            AbstractMinhashCreator creator = new BinaryMappingMinhashCreator(loader, 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096));
            System.out.println("starting evaluation");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/binary/", ExperimentsUtils.randomImageQueries100, false);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }
}

package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.*;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;


public class BinaryMappingMinhashCreator extends AbstractMinhashCreator{

    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        BinaryMappingMinhashCreator creator = new BinaryMappingMinhashCreator(new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096), 2048);
        creator.createMinhashes();
    }

    public BinaryMappingMinhashCreator(BooleanVectorLoader loader, int minhashVectorSize){
        super(minhashVectorSize, new PermutationGenerator(loader.getVectorSize(), minhashVectorSize), loader);
    }

    @Override
    protected String getPath() {
        return loader.getPath().replace(".data", "") +
                "_minhash_1_" + generator.getNumberOfVectors() + ".data";
    }

    @Override
    protected void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations) {
        for (int i = 0; i < generator.getNumberOfVectors(); i++) {
            int j = 0;
            while (!((BooleanVectorData)data).getVector()[permutations[i][j]])
                j++;
            if (i != 0)
                builder.append(" ");
            j++;
            builder.append(j);
        }
    }
}

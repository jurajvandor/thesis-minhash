package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.*;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;


public class BinaryMappingMinhashCreator extends AbstractMinhashCreator{

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

    @Override
    protected void createBinarySignature(StringBuilder builder, AbstractVectorData data) {
        boolean[] vector = ((BooleanVectorData)data).getVector();
        for (int i = 0; i < vector.length; i++){
            if (i != 0)
                builder.append(" ");
            builder.append(vector[i] ? "1" : "0");
        }
    }
}

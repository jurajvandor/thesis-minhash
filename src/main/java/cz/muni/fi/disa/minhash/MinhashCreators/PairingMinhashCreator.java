package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationException;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PairingMinhashCreator extends AbstractMinhashCreator{

    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        MinhashCreator creator = new PairingMinhashCreator(new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096), 2048);
        creator.createMinhashes();
    }

    public PairingMinhashCreator(BooleanVectorLoader loader, int minhashVectorSize){
        super(minhashVectorSize, new PermutationGenerator(loader.getVectorSize()*loader.getVectorSize(), minhashVectorSize), loader);
    }

    @Override
    protected String getPath() {
        return loader.getPath().replace(".data", "") +
                "_minhash_3_" + generator.getNumberOfVectors() + ".data";
    }

    @Override
    protected void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations) {
        BooleanVectorData d = (BooleanVectorData)data;
        for (int i = 0; i < generator.getNumberOfVectors(); i++) {
            int j = 0;
            int index = permutations[i][j];
            while (!(d.getVector()[index/loader.getVectorSize()] && d.getVector()[index%loader.getVectorSize()])) {
                index = permutations[i][j];
                j++;
            }
            if (i != 0)
                builder.append(" ");
            j++;
            builder.append(j);
        }
    }
}

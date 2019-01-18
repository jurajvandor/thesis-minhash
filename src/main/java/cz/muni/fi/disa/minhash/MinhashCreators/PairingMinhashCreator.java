package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;


public class PairingMinhashCreator extends AbstractMinhashCreator{

    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        MinhashCreator creator = new PairingMinhashCreator(new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096), 2048, true);
        creator.createMinhashes();
    }

    private boolean and;

    public PairingMinhashCreator(BooleanVectorLoader loader, int minhashVectorSize, boolean and){
        super(minhashVectorSize, new PermutationGenerator(loader.getVectorSize()*loader.getVectorSize(), minhashVectorSize), loader);
    }

    public void setOperator(boolean and){
        this.and = and;
    }

    @Override
    protected String getPath() {
        String op = and ? "and" : "or";
        return loader.getPath().replace(".data", "") +
                "_minhash_3_" + generator.getNumberOfVectors() + "_" + op + ".data";
    }

    @Override
    protected void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations) {
        BooleanVectorData d = (BooleanVectorData)data;
        for (int i = 0; i < generator.getNumberOfVectors(); i++) {
            int j = 0;
            int index = permutations[i][j];
            while (!op(d.getVector()[index/loader.getVectorSize()], d.getVector()[index%loader.getVectorSize()])) {
                index = permutations[i][j];
                j++;
            }
            if (i != 0)
                builder.append(" ");
            j++;
            builder.append(j);
        }
    }

    private boolean op(boolean i1, boolean i2){
        return and ? (i1 && i2) : (i1 || i2);
    }
}

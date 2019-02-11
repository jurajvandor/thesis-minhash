package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;

public class QuantizationMinhashCreator extends AbstractMinhashCreator{

    private float[] buckets;

    //max value of float descriptors is 36.6
    public QuantizationMinhashCreator(FloatVectorLoader loader, int minhashVectorSize, int numberOfBuckets){
        super(minhashVectorSize, new PermutationGenerator(numberOfBuckets * loader.getVectorSize(), minhashVectorSize), loader);
        setNumberOfBuckets(numberOfBuckets);
    }

    public void setNumberOfBuckets(int numberOfBuckets){
        generator.setSizeOfVector(numberOfBuckets * loader.getVectorSize());
        buckets = new float[numberOfBuckets];
        buckets[0] = (float)0.0;
        for(int i = 1; i < numberOfBuckets; i++){
            buckets[i] = (float)Math.pow(36.6,(i/(double)numberOfBuckets)); //((float)36.6 / (float)numberOfBuckets)*i;
        }
    }


    @Override
    protected String getPath() {
        return loader.getPath().replace(".data", "") +
                "_minhash_2_" + generator.getNumberOfVectors() + "_" + buckets.length + ".data";
    }

    @Override
    protected void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations) {
        boolean[] descriptor = createDescriptor((FloatVectorData)data);
        for (int i = 0; i < generator.getNumberOfVectors(); i++) {
            int j = 0;
            while (!descriptor[permutations[i][j]])
                j++;
            if (i != 0)
                builder.append(" ");
            j++;
            builder.append(j);
        }
    }

    @Override
    protected void createBinarySignature(StringBuilder builder, AbstractVectorData data) {
        boolean[] vector = createDescriptor((FloatVectorData)data);
        for (int i = 0; i < vector.length; i++){
            if (i != 0)
                builder.append(" ");
            builder.append(vector[i] ? "1" : "0");
        }
    }

    private boolean[] createDescriptor(FloatVectorData data){
        int j = 0;
        boolean[] result = new boolean[data.getVector().length*(buckets.length)];
        for (float n : data.getVector()){
            for (float b : buckets){
                result[j] = n > b;
                j++;
            }
        }
        return result;
    }
}

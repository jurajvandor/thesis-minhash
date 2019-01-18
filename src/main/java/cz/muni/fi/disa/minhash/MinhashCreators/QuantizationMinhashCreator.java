package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationException;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuantizationMinhashCreator extends AbstractMinhashCreator{
    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        MinhashCreator creator = new QuantizationMinhashCreator(new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096), 4, 2048);
        creator.createMinhashes();
    }

    private float[] buckets;

    //max value of float descriptors is 36.6
    public QuantizationMinhashCreator(FloatVectorLoader loader, int numberOfBucketsForValue, int minhashVectorSize){
        super(minhashVectorSize, new PermutationGenerator(numberOfBucketsForValue*loader.getVectorSize(), minhashVectorSize), loader);
        buckets = new float[numberOfBucketsForValue];
        buckets[0] = (float)0.0;
        for(int i = 1; i < numberOfBucketsForValue; i++){
            buckets[i] = (float)Math.pow(36.6,(i/(double)numberOfBucketsForValue)); //((float)36.6 / (float)numberOfBucketsForValue)*i;
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

    private boolean[] createDescriptor(FloatVectorData data){
        int j = 0;
        boolean[] result = new boolean[data.getVector().length*(buckets.length)];
        for (float n : data.getVector()){
            for (float b : buckets){
                result[j] = n > b;
                j++;
            }
        }
//        StringBuilder s = new StringBuilder();
//        for (boolean v : result){
//            if (v)
//                s.append("1");
//            else
//                s.append("0");
//        }
//        System.out.println(s);
        return result;
    }
}

package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.*;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BinaryMappingMinhashCreator implements MinhashCreator{

    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        BinaryMappingMinhashCreator creator = new BinaryMappingMinhashCreator(new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096), new PermutationGenerator(4096, 2048));
        creator.createMinhashes();
    }

    private BooleanVectorLoader loader;
    private PermutationGenerator generator;

    public BinaryMappingMinhashCreator(BooleanVectorLoader loader, PermutationGenerator generator){
        this.loader = loader;
        this.generator = generator;
    }

    public BooleanVectorLoader getLoader() {
        return loader;
    }

    public void setLoader(BooleanVectorLoader loader) {
        this.loader = loader;
    }

    public PermutationGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(PermutationGenerator generator) {
        this.generator = generator;
    }
    /**
     * This will overwrite existing minhash of same vector size for this data file
     */
    @Override
    public void createMinhashes() throws MinhashException{
        String path = loader.getPath().replace(".data", "") +
                "_minhash_1_" + generator.getNumberOfVectors() + ".data";
        try {
            OutputStream out = Files.newOutputStream(Paths.get(path));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            int[][] permutations = generator.loadPermutations();
            for (BooleanVectorData data : loader) {
                writer.write("#objectKey messif.objects.keys.AbstractObjectKey " + data.getId());
                writer.newLine();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < generator.getNumberOfVectors(); i++) {
                    int j = 0;
                    while (!data.getVector()[permutations[i][j]])
                        j++;
                    if (i != 0)
                        builder.append(" ");
                    j++;
                    builder.append(j);
                }
                writer.write(builder.toString());
                writer.newLine();
            }
            writer.newLine();
            writer.close();
        }catch (IOException e){
            throw new MinhashException("Data of created minhash could not be written to file", e);
        }catch (PermutationException e) {
            throw new MinhashException("Error loading permutation", e);
        }
    }
}

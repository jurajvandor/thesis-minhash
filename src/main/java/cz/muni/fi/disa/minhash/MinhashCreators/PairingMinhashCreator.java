package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
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

public class PairingMinhashCreator implements MinhashCreator{

    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        MinhashCreator creator = new PairingMinhashCreator(new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096), 2048);
        creator.createMinhashes();
    }

    private BooleanVectorLoader loader;
    private PermutationGenerator generator;

    public PairingMinhashCreator(BooleanVectorLoader loader, int minhashVectorSize){
        this.loader = loader;
        this.generator = new PermutationGenerator(loader.getVectorSize()*loader.getVectorSize(), minhashVectorSize);
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
    @Override
    public void createMinhashes() throws MinhashException {
        String path = loader.getPath().replace(".data", "") +
                "_minhash_3_" + generator.getNumberOfVectors() + ".data";
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
                    int index = permutations[i][j];
                    while (!(data.getVector()[index/loader.getVectorSize()] && data.getVector()[index%loader.getVectorSize()]))
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
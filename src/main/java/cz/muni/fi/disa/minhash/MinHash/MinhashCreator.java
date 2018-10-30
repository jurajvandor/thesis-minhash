package cz.muni.fi.disa.minhash.MinHash;

import com.sun.org.apache.xml.internal.utils.IntVector;
import cz.muni.fi.disa.minhash.DataHelpers.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHelpers.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHelpers.PermutationGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MinhashCreator {
    private BooleanVectorLoader loader;
    private PermutationGenerator generator;

    public MinhashCreator(BooleanVectorLoader loader, PermutationGenerator generator){
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
    public void createMinhashes() throws MinhashException{
        String path = loader.getPath().replace(".data", "") +
                "_minhash_" + generator.getNumberOfVectors() + "_.data";
        try {
            OutputStream out = Files.newOutputStream(Paths.get(path));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            for (BooleanVectorData data : loader) {
                writer.write("#objectKey messif.objects.keys.AbstractObjectKey " + data.getId());
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < generator.getSizeOfVector(); i++) {
                    if (i != 0)
                        builder.append(" ");
                    builder.append(data.getVector()[i]);//TODO not really.. should be minhash
                }
            }
            writer.newLine();
            writer.close();
        }catch (IOException e){
            throw new MinhashException("Data of created minhash could not be written to file", e);
        }
    }
}

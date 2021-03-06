package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.AbstractVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationException;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractMinhashCreator implements MinhashCreator{
    protected int minhashVectorSize;
    protected PermutationGenerator generator;
    protected AbstractVectorLoader loader;

    public AbstractMinhashCreator(int minhashVectorSize, PermutationGenerator generator, AbstractVectorLoader loader){
        this.minhashVectorSize = minhashVectorSize;
        this.generator = generator;
        this.loader = loader;
    }

    public int getMinhashVectorSize() {
        return minhashVectorSize;
    }

    public void setMinhashVectorSize(int minhashVectorSize){
        generator.setNumberOfVectors(minhashVectorSize);
        this.minhashVectorSize = minhashVectorSize;
    }

    protected abstract String getPath();

    public String getSignaturePath(){
        String path = getPath();
        return path.replaceAll("(minhash_[1-4]_[0-9]{2,4})", "bin_sig");
    }

    public abstract int getSignatureVectorSize();

    protected abstract void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations);

    private void createBinarySignatureString(StringBuilder builder, AbstractVectorData data){
        boolean[] vector = createBinarySignature(data);
        for (int i = 0; i < vector.length; i++){
            if (i != 0)
                builder.append(" ");
            builder.append(vector[i] ? "1" : "0");
        }
    }

    protected abstract boolean[] createBinarySignature(AbstractVectorData data);

    /**
     * This will overwrite existing minhash of same vector size for this data file
     */
    public String createMinhashes() throws MinhashException{
        String path = getPath();
        try {
            OutputStream out = Files.newOutputStream(Paths.get(path));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            int[][] permutations = generator.loadPermutations();
            for (AbstractVectorData data : loader) {
                writer.write("#objectKey messif.objects.keys.AbstractObjectKey " + data.getId());
                writer.newLine();
                StringBuilder builder = new StringBuilder();
                createMinhash(builder, data, permutations);
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
        return path;
    }

    public String createBinarySignatures() throws MinhashException{
        String path = getSignaturePath();
        try {
            OutputStream out = Files.newOutputStream(Paths.get(path));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            for (AbstractVectorData data : loader) {
                writer.write("#objectKey messif.objects.keys.AbstractObjectKey " + data.getId());
                writer.newLine();
                StringBuilder builder = new StringBuilder();
                createBinarySignatureString(builder, data);
                writer.write(builder.toString());
                writer.newLine();
            }
            writer.newLine();
            writer.close();
        }catch (IOException e){
            throw new MinhashException("Data of created minhash could not be written to file", e);
        }
        return path;
    }
}

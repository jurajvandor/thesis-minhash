package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.MovementDataVectorsLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.Joint;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.MovementData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationException;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MovementDataMinhashCreator {
    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        MovementDataMinhashCreator creator = new MovementDataMinhashCreator(new MovementDataVectorsLoader("data_files/objects-annotations-specific-coords_normPOS.data", ";"), new PermutationGenerator(4096, 2048));
        creator.createMinhashes();
    }

    private MovementDataVectorsLoader loader;
    private PermutationGenerator generator;

    public MovementDataMinhashCreator(MovementDataVectorsLoader loader, PermutationGenerator generator){
        this.loader = loader;
        this.generator = generator;
    }

    public MovementDataVectorsLoader getLoader() {
        return loader;
    }

    public void setLoader(MovementDataVectorsLoader loader) {
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
                "_minhash_4_" + generator.getNumberOfVectors() + ".data";
        int nOfCubes = generator.getSizeOfVector();
        int cubeSize = (int) Math.cbrt(nOfCubes);
        if (cubeSize*cubeSize*cubeSize != nOfCubes)
            throw new MinhashException("cube root of number of the smaller cubes need to be whole number");
        float stepSize = 44/(float)cubeSize;
        try {
            OutputStream out = Files.newOutputStream(Paths.get(path));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            int[][] permutations = generator.loadPermutations();
            for (MovementData data : loader) {
                writer.write("#objectKey messif.objects.keys.AbstractObjectKey " + data.getId());
                writer.newLine();
                StringBuilder builder = new StringBuilder();
                boolean[] minhashInput = constructBinaryVector(cubeSize, data, stepSize);
                for (int i = 0; i < generator.getNumberOfVectors(); i++) {
                    int j = 0;
                    while (!minhashInput[permutations[i][j]])
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


    //for now using +22 -22 borders
    private boolean[] constructBinaryVector(int cubeSize, MovementData data, float stepSize) {
        boolean[] result = new boolean[cubeSize*cubeSize*cubeSize];
        for (int i = 0; i < data.getFrames().length; i++){
            for (int j = 0; j < MovementData.N_OF_JOINTS; j++){
                Joint joint = data.getFrames()[i][j];
                int index = (int)((joint.getX()+22)/stepSize) + cubeSize*((int)((joint.getY()+22)/stepSize) + cubeSize*(int)((joint.getZ()+22)/stepSize));
//                System.out.println(joint.getX() + " " + joint.getY() + " " + joint.getZ() + " " + index);
                result[index] = true;
            }
        }
        return result;
    }
}

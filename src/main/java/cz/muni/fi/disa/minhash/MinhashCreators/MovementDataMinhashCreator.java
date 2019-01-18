package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.MovementDataVectorsLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.Joint;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.MovementData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

public class MovementDataMinhashCreator extends AbstractMinhashCreator{
    public static void main(String[] args)throws VectorLoaderException, MinhashException {
        MovementDataMinhashCreator creator = new MovementDataMinhashCreator(new MovementDataVectorsLoader("data_files/objects-annotations-specific-coords_normPOS.data", ";"), 2048, 15);
        creator.createMinhashes();
    }

    private int cubeSize;
    private float stepSize;

    public MovementDataMinhashCreator(MovementDataVectorsLoader loader, int minhashVectorSize, int numberOfDivisionsInOneDimension){
        super(minhashVectorSize, new PermutationGenerator(numberOfDivisionsInOneDimension*numberOfDivisionsInOneDimension*numberOfDivisionsInOneDimension, minhashVectorSize), loader);
        cubeSize = numberOfDivisionsInOneDimension;
        stepSize = 44/(float)cubeSize;
    }

    @Override
    protected String getPath() {
        return loader.getPath().replace(".data", "") +
                "_minhash_4_" + generator.getNumberOfVectors() + "_" + cubeSize + ".data";
    }

    @Override
    protected void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations) {
        boolean[] minhashInput = constructBinaryVector(cubeSize, (MovementData)data, stepSize);
        for (int i = 0; i < generator.getNumberOfVectors(); i++) {
            int j = 0;
            while (!minhashInput[permutations[i][j]])
                j++;
            if (i != 0)
                builder.append(" ");
            j++;
            builder.append(j);
        }
    }

    //for now using +22 -22 borders
    private boolean[] constructBinaryVector(int cubeSize, MovementData data, float stepSize) {
        boolean[] result = new boolean[cubeSize*cubeSize*cubeSize];
        for (int i = 0; i < data.getFrames().length; i++){
            for (int j = 0; j < MovementData.N_OF_JOINTS; j++){
                Joint joint = data.getFrames()[i][j];
                int index = (int)((joint.getX()+22)/stepSize) + cubeSize*((int)((joint.getY()+22)/stepSize) + cubeSize*(int)((joint.getZ()+22)/stepSize));
                result[index] = true;
            }
        }
        return result;
    }
}

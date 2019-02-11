package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.MovementDataVectorsLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.Joint;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.MovementData;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;

public class MovementDataMinhashCreator extends AbstractMinhashCreator{

    private int cubeSize;
    private float stepSize;
    private int timeCubes;
    private JointSelection jointSelection;
    private static final int[] LEFT = {1,2,3,4,5,17,18,19,20,21,22,23};
    private static final int[] RIGHT = {6,7,8,9,10,24,25,26,27,28,29,30};
    private static final int[] MID = {0,11,12,13,14,15,16};
    private static final int[] LEFT_HAND = {17,18,19,20,21,22,23};
    private static final int[] RIGHT_HAND = {24,25,26,27,28,29,30};
    private static final int[] LEFT_LEG = {1,2,3,4,5};
    private static final int[] RIGHT_LEG = {6,7,8,9,10};

    public MovementDataMinhashCreator(MovementDataVectorsLoader loader, int minhashVectorSize, int cubeSize, int timeCubes,
                                      JointSelection jointSelection){
        super(minhashVectorSize, new PermutationGenerator(0, minhashVectorSize), loader);
        this.timeCubes = timeCubes;
        this.jointSelection = jointSelection;
        setCubeSize(cubeSize);
        setPermutations();
    }

    private int jointGroups(){
        if (jointSelection == JointSelection.ALL_IN_ONE)
            return 1;
        if (jointSelection == JointSelection.LEFT_RIGHT_MID)
            return 3;
        return 5;
    }

    private void setPermutations(){
        generator.setSizeOfVector(cubeSize*cubeSize*cubeSize*jointGroups()*timeCubes);
    }

    public void setCubeSize(int cubeSize){
        this.cubeSize = cubeSize;
        stepSize = 44/(float) this.cubeSize;
        setPermutations();
    }

    public int getCubeSize() {
        return cubeSize;
    }

    public float getStepSize() {
        return stepSize;
    }

    public int getTimeCubes() {
        return timeCubes;
    }

    public JointSelection getJointSelection() {
        return jointSelection;
    }

    public void setTimeCubes(int timeCubes) {
        this.timeCubes = timeCubes;
        setPermutations();
    }

    public void setJointSelection(JointSelection jointSelection) {
        this.jointSelection = jointSelection;
        setPermutations();
    }

    @Override
    protected String getPath() {
        return loader.getPath().replace(".data", "") + "_minhash_4_" + generator.getNumberOfVectors() +
                "_" + cubeSize + "_" + timeCubes + "_" + jointSelection + ".data";
    }

    @Override
    protected void createMinhash(StringBuilder builder, AbstractVectorData data, int[][] permutations) {
        boolean[] minhashInput = constructBinaryVector((MovementData)data);
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

    @Override
    protected void createBinarySignature(StringBuilder builder, AbstractVectorData data) {
        boolean[] vector = constructBinaryVector((MovementData)data);
        for (int i = 0; i < vector.length; i++){
            if (i != 0)
                builder.append(" ");
            builder.append(vector[i] ? "1" : "0");
        }
    }

    private boolean[] constructBinaryVector(MovementData data) {
        boolean[] result = new boolean[cubeSize * cubeSize * cubeSize * jointGroups() * timeCubes];
        int timeStep = (data.getFrames().length + timeCubes - 1) / timeCubes;
        int offsetStep = cubeSize * cubeSize * cubeSize * jointGroups();
        int offset = 0;
        if (jointSelection == JointSelection.ALL_IN_ONE) {
            for (int i = 0; i < data.getFrames().length; i++) {
                for (int j = 0; j < MovementData.N_OF_JOINTS; j++) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset] = true;
                }
                if (i % timeStep == timeStep - 1)
                    offset += offsetStep;
            }
        }
        if (jointSelection == JointSelection.LEFT_RIGHT_MID){
            int jointOffsetStep = cubeSize * cubeSize * cubeSize;
            for (int i = 0; i < data.getFrames().length; i++) {
                for (int j : LEFT) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset] = true;
                }
                for (int j : RIGHT) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset + jointOffsetStep] = true;
                }
                for (int j : MID) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset + jointOffsetStep + jointOffsetStep] = true;
                }
                if (i % timeStep == timeStep - 1)
                    offset += offsetStep;
            }
        }
        if (jointSelection == JointSelection.TORSO_AND_LIMBS){
            int jointOffsetStep = cubeSize * cubeSize * cubeSize;
            for (int i = 0; i < data.getFrames().length; i++) {
                for (int j : LEFT_HAND) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset] = true;
                }
                for (int j : RIGHT_HAND) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset + jointOffsetStep] = true;
                }
                for (int j : MID) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset + jointOffsetStep*2] = true;
                }
                for (int j : LEFT_LEG) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset + jointOffsetStep*3] = true;
                }
                for (int j : RIGHT_LEG) {
                    Joint joint = data.getFrames()[i][j];
                    int index = (int) ((joint.getX() + 22) / stepSize) + cubeSize * ((int) ((joint.getY() + 22) / stepSize) + cubeSize * (int) ((joint.getZ() + 22) / stepSize));
                    result[index + offset + jointOffsetStep*4] = true;
                }
                if (i % timeStep == timeStep - 1)
                    offset += offsetStep;
            }
        }
        return result;
    }
}

package cz.muni.fi.disa.minhash.DataHolders;

public class MovementData {
    public static final int N_OF_JOINTS = 31;
    private Joint[][] frames;
    private String id;

    public MovementData(int numberOfFrames, String id){
        frames = new Joint[numberOfFrames][N_OF_JOINTS];
        this.id = id;
    }

    public Joint[][] getFrames() {
        return frames;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package cz.muni.fi.disa.minhash.DataHolders.ObjectData;

import java.util.Arrays;
import java.util.Objects;

public class MovementData extends AbstractVectorData{
    public static final int N_OF_JOINTS = 31;
    private Joint[][] frames;

    public MovementData(int numberOfFrames, String id){
        super(id);
        frames = new Joint[numberOfFrames][N_OF_JOINTS];
    }

    public Joint[][] getFrames() {
        return frames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovementData that = (MovementData) o;
        return Arrays.equals(frames, that.frames) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(frames);
        return result;
    }

    @Override
    public String toString() {
        return "MovementData{" +
                "frames=" + Arrays.toString(frames) +
                ", id='" + id + '\'' +
                '}';
    }
}

package cz.muni.fi.disa.minhash.DataHolders.ObjectData;

import java.util.Arrays;
import java.util.Objects;

public class IntegerVectorData extends AbstractVectorData{
    private int[] vector;

    public IntegerVectorData(int[] vector, String id){
        super(id);
        this.vector = vector;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerVectorData that = (IntegerVectorData) o;
        return Arrays.equals(vector, that.vector) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, id);
    }

    @Override
    public String toString() {
        return "IntegerVectorData{" +
                ", id='" + id + '\'' +
                "vector=" + vector +
                '}';
    }
}

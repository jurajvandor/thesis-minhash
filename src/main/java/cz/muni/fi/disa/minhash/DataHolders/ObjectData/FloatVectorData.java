package cz.muni.fi.disa.minhash.DataHolders.ObjectData;

import java.util.Arrays;
import java.util.Objects;

public class FloatVectorData extends AbstractVectorData{
    private float[] vector;
    private String id;

    public FloatVectorData(float[] vector, String id){
        super(id);
        this.vector = vector;
    }

    public float[] getVector() {
        return vector;
    }

    public void setVector(float[] vector) {
        this.vector = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatVectorData that = (FloatVectorData) o;
        return Arrays.equals(vector, that.vector) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, id);
    }

    @Override
    public String toString() {
        return "FloatVectorData{" +
                ", id='" + id + '\'' +
                "vector=" + vector +
                '}';
    }
}

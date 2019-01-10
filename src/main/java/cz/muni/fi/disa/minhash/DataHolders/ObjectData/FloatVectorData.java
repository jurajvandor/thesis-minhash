package cz.muni.fi.disa.minhash.DataHolders.ObjectData;


import java.util.Arrays;
import java.util.Objects;

public class FloatVectorData {
    private float[] vector;
    private String id;

    public FloatVectorData(float[] vector, String id){
        this.vector = vector;
        this.id = id;
    }

    public float[] getVector() {
        return vector;
    }

    public void setVector(float[] vector) {
        this.vector = vector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

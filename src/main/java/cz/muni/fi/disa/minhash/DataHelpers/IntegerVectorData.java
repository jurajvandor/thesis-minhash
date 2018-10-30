package cz.muni.fi.disa.minhash.DataHelpers;


import java.util.Arrays;
import java.util.Objects;

public class IntegerVectorData {
    private int[] vector;
    private String id;

    public IntegerVectorData(int[] vector, String id){
        this.vector = vector;
        this.id = id;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
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

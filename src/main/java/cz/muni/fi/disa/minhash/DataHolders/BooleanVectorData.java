package cz.muni.fi.disa.minhash.DataHolders;

import java.util.Arrays;
import java.util.Objects;

public class BooleanVectorData {
    private boolean[] vector;
    private String id;

    public BooleanVectorData(boolean[] vector, String id){
        this.vector = vector;
        this.id = id;
    }

    public boolean[] getVector() {
        return vector;
    }

    public void setVector(boolean[] vector) {
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
        BooleanVectorData that = (BooleanVectorData) o;
        return Arrays.equals(vector, that.vector) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, id);
    }

    @Override
    public String toString() {
        return "BooleanVectorData{" +
                ", id='" + id + '\'' +
                "vector=" + vector +
                '}';
    }
}

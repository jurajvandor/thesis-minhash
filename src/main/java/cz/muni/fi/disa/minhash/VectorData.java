package cz.muni.fi.disa.minhash;

import java.util.List;
import java.util.Objects;

public class VectorData {
    private List<Boolean> vector;
    private String id;

    public VectorData(List<Boolean> vector, String id){
        this.vector = vector;
        this.id = id;
    }

    public List<Boolean> getVector() {
        return vector;
    }

    public void setVector(List<Boolean> vector) {
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
        VectorData that = (VectorData) o;
        return Objects.equals(vector, that.vector) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, id);
    }

    @Override
    public String toString() {
        return "VectorData{" +
                ", id='" + id + '\'' +
                "vector=" + vector +
                '}';
    }
}

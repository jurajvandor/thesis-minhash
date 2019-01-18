package cz.muni.fi.disa.minhash.DataHolders.ObjectData;

import java.util.Arrays;
import java.util.Objects;

public class BooleanVectorData extends AbstractVectorData{
    private boolean[] vector;

    public BooleanVectorData(boolean[] vector, String id){
        super(id);
        this.vector = vector;
    }

    public boolean[] getVector() {
        return vector;
    }

    public void setVector(boolean[] vector) {
        this.vector = vector;
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

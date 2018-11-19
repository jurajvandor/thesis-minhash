package cz.muni.fi.disa.minhash.MinHash;

import java.util.Objects;

public class QueryResult implements Comparable<QueryResult>{
    private String id;
    private float similarity;

    public QueryResult(String id, float similarity){
        this.id = id;
        this.similarity = similarity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryResult)) return false;
        QueryResult that = (QueryResult) o;
        return Objects.equals(getId(), that.getId()) &&
                getSimilarity() == that.getSimilarity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSimilarity());
    }


    @Override
    public int compareTo(QueryResult o) {
        if (similarity > o.similarity)
            return 1;
        return -1;
    }
}

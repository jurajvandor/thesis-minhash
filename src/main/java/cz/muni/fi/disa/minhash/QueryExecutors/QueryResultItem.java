package cz.muni.fi.disa.minhash.QueryExecutors;

import java.util.Objects;

public class QueryResultItem implements Comparable<QueryResultItem>{
    private String id;
    private float similarity;

    public QueryResultItem(String id, float similarity){
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
        if (!(o instanceof QueryResultItem)) return false;
        QueryResultItem that = (QueryResultItem) o;
        return Objects.equals(getId(), that.getId()) &&
                getSimilarity() == that.getSimilarity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSimilarity());
    }


    @Override
    public int compareTo(QueryResultItem o) {
        if (similarity > o.similarity)
            return 1;
        return -1;
    }
}

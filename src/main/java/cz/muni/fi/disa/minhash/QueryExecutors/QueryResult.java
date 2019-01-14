package cz.muni.fi.disa.minhash.QueryExecutors;

import java.util.SortedSet;

public class QueryResult {
    SortedSet<QueryResultItem> items;
    long executionTime;

    public QueryResult(SortedSet<QueryResultItem> items, long executionTime) {
        this.items = items;
        this.executionTime = executionTime;
    }

    public SortedSet<QueryResultItem> getItems() {
        return items;
    }

    public void setItems(SortedSet<QueryResultItem> items) {
        this.items = items;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}

package cz.muni.fi.disa.minhash.QueryExecutors;

public interface QueryExecutor {
    QueryResult findSimilarItems(int numberOfRequestedItems, String idOfQueryItem);
}

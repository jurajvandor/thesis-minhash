package cz.muni.fi.disa.minhash.QueryExecutors;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public class BinarySignatureQueryExecutor implements QueryExecutor{

    private BooleanVectorLoader loader;
    private List<BooleanVectorData> data;

    public BinarySignatureQueryExecutor(BooleanVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToList();
    }

    public QueryResult findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<BooleanVectorData> query = data.stream().filter(x -> x.getId().equals(idOfQueryItem)).findAny();
        if (!query.isPresent())
            return null;
        QueryResult result = findSimilarItems(numberOfRequestedItems+1, query.get().getVector());
        ((TreeSet)result.getItems()).pollLast();
        return result;
    }

    public QueryResult findSimilarItems(int numberOfRequestedItems, boolean[] queryVector){
        TreeSet<QueryResultItem> result = new TreeSet<>();
        long begin = System.currentTimeMillis();
        for (BooleanVectorData item : data){
            result.add(new QueryResultItem(item.getId(), compare(queryVector, item.getVector())));
            if (result.size() > numberOfRequestedItems)
                result.pollFirst();
        }
        long end = System.currentTimeMillis();
        return new QueryResult(result, end - begin);
    }

    private float compare(boolean[] o1, boolean[] o2) {
        int union = 0;
        int intersection = 0;
        for (int i = 0; i < o1.length; i++) {
            if (o1[i] || o2[i])
                union++;
            if (o1[i] && o2[i])
                intersection++;
        }
        return intersection/(float)union;
    }
}

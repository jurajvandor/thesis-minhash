package cz.muni.fi.disa.minhash.QueryExecutors;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public class BinarySignatureQueryExecutor implements QueryExecutor{

    public static void main(String[] args){
        try {
            BinarySignatureQueryExecutor executor = new BinarySignatureQueryExecutor(
                    new BooleanVectorLoader("data_files/features-images-profiset100K_bin_sig.data", " ", 4096));
            QueryResult r = executor.findSimilarItems(1, "0000000002");
            System.out.println(r.getExecutionTime());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
        int intersection = 0;
        int union = 0;
        for (int i = 0; i < o1.length; i++){
            intersection += o1[i] & o2[i] ? 1 : 0;
            union += o1[i] | o2[i] ? 1 : 0;
        }
        return intersection/(float)union;
    }
}

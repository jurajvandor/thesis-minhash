package cz.muni.fi.disa.minhash.QueryExecutors;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import javafx.util.Pair;

import java.util.*;

public class ReferenceQueryExecutor implements QueryExecutor{

    private FloatVectorLoader loader;
    private List<FloatVectorData> data;

    public static void main(String[] args) throws Exception{
        try {
            FloatVectorLoader loader = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            ReferenceQueryExecutor executor = new ReferenceQueryExecutor(loader);
            QueryResult result = executor.findSimilarItems(50, "0000000002");
            for (QueryResultItem item : result.getItems())
                System.out.println(item.getSimilarity() + " " + item.getId());
            System.out.println("Time: " + result.getExecutionTime());
        }catch (Exception e){
            throw new Exception(e);
        }
    }


    public ReferenceQueryExecutor(FloatVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToList();
    }

    public QueryResult findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<FloatVectorData> query = data.stream().filter(x -> x.getId().equals(idOfQueryItem)).findAny();
        if (!query.isPresent())
            return null;
        QueryResult result = findSimilarItems(numberOfRequestedItems+1, query.get().getVector());
        ((TreeSet)result.getItems()).pollLast();
        return result;
    }

    public QueryResult findSimilarItems(int numberOfRequestedItems, float[] queryVector){
        TreeSet<QueryResultItem> result = new TreeSet<>();
        long begin = System.currentTimeMillis();
        for (FloatVectorData item : data){
            result.add(new QueryResultItem(item.getId(), compare(queryVector, item.getVector())));
            if (result.size() > numberOfRequestedItems)
                result.pollLast();
        }
        long end = System.currentTimeMillis();
        return new QueryResult(result, end - begin);
    }

    private float compare(float[] o1, float[] o2) {
        double sum = 0.0;
        for(int i=0; i < o1.length; i++) {
            float diff = o1[i] - o2[i];
            sum = sum + diff*diff;
        }
        return (float) Math.sqrt(sum);
    }

}

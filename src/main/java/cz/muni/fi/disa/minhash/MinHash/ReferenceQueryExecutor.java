package cz.muni.fi.disa.minhash.MinHash;

import cz.muni.fi.disa.minhash.DataHolders.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.FloatVectorLoader;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReferenceQueryExecutor {

    private FloatVectorLoader loader;
    private ArrayList<FloatVectorData> data;

    public static void main(String[] args) throws Exception{
        try {
            FloatVectorLoader loader = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            ReferenceQueryExecutor executor = new ReferenceQueryExecutor(loader);
            Pair<SortedSet<QueryResultItem>, Long> result = executor.findSimilarItems(50, "0000000002");
            for (QueryResultItem item : result.getKey())
                System.out.println(item.getSimilarity() + " " + item.getId());
            System.out.println("Time: " + result.getValue());
        }catch (Exception e){
            throw new Exception(e);
        }
    }


    public ReferenceQueryExecutor(FloatVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToArrayList();
    }

    public Pair<SortedSet<QueryResultItem>, Long> findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<FloatVectorData> query = data.stream().findFirst().filter(x -> x.getId().equals(idOfQueryItem));
        return query.isPresent() ? findSimilarItems(numberOfRequestedItems, query.get().getVector()) : null;
    }

    public Pair<SortedSet<QueryResultItem>, Long> findSimilarItems(int numberOfRequestedItems, float[] queryVector){
        TreeSet<QueryResultItem> result = new TreeSet<>();
        long begin = System.currentTimeMillis();
        for (FloatVectorData item : data){
            result.add(new QueryResultItem(item.getId(), compare(queryVector, item.getVector())));
            if (result.size() > numberOfRequestedItems)
                result.pollLast();
        }
        long end = System.currentTimeMillis();
        return new Pair<>(result, end - begin);
    }

    public float compare(float[] o1, float[] o2) {
        double sum = 0.0;
        for(int i=0; i < o1.length; i++) {
            float diff = o1[i] - o2[i];
            sum = sum + diff*diff;
        }
        return (float) Math.sqrt(sum);
    }

}

package cz.muni.fi.disa.minhash.MinHash;

import cz.muni.fi.disa.minhash.DataHolders.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.IntegerVectorLoader;
import javafx.util.Pair;

import java.util.*;

public class MinhashQueryExecutor {

    private IntegerVectorLoader loader;
    private ArrayList<IntegerVectorData> data;

    public static void main(String[] args) throws Exception{
            try {
                IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_2048.data", " ", 2048);
                MinhashQueryExecutor executor = new MinhashQueryExecutor(loader);
                Pair<SortedSet<QueryResultItem>, Long> result = executor.findSimilarItems(50, "0000000002");
                for (QueryResultItem item : result.getKey())
                    System.out.println(item.getSimilarity() + " " + item.getId());
                System.out.println("Time: " + result.getValue());
            }catch (Exception e){
                throw new Exception(e);
            }
    }


    public MinhashQueryExecutor(IntegerVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToArrayList();
    }

    public Pair<SortedSet<QueryResultItem>, Long> findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<IntegerVectorData> query = data.stream().findFirst().filter(x -> x.getId().equals(idOfQueryItem));
        return query.isPresent() ? findSimilarItems(numberOfRequestedItems, query.get().getVector()) : null;
    }

    public Pair<SortedSet<QueryResultItem>, Long> findSimilarItems(int numberOfRequestedItems, int[] queryVector){
        TreeSet<QueryResultItem> result = new TreeSet<>();
        long begin = System.currentTimeMillis();
        for (IntegerVectorData item : data){
            result.add(new QueryResultItem(item.getId(), compare(queryVector, item.getVector())));
            if (result.size() > numberOfRequestedItems)
                result.pollFirst();
        }
        long end = System.currentTimeMillis();
        return new Pair<>(result, end - begin);
    }

    public float compare(int[] o1, int[] o2) {
        int count = 0;
        for (int i = 0; i < o1.length; i++) {
            if (o1[i] == o2[i])
                count++;
        }
        return count/(float)o1.length;
    }


}
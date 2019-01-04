package cz.muni.fi.disa.minhash.MinHash;

import cz.muni.fi.disa.minhash.DataHolders.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.IntegerVectorLoader;

import java.util.*;

public class MinhashQueryExecutor {

    private IntegerVectorLoader loader;
    private ArrayList<IntegerVectorData> data;

    public static void main(String[] args) throws Exception{
            try {
                IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_4096.data", " ", 4096);
                MinhashQueryExecutor executor = new MinhashQueryExecutor(loader);
                SortedSet<QueryResultItem> result = executor.findSimilarItems(50, "0000000002");
                for (QueryResultItem item : result)
                    System.out.println(item.getSimilarity() + " " + item.getId());
            }catch (Exception e){
                throw new Exception(e);
            }
    }


    public MinhashQueryExecutor(IntegerVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToArrayList();
    }

    public SortedSet<QueryResultItem> findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<IntegerVectorData> query = data.stream().findFirst().filter(x -> x.getId().equals(idOfQueryItem));
        return query.isPresent() ? findSimilarItems(numberOfRequestedItems, query.get().getVector()) : null;
    }

    public SortedSet<QueryResultItem> findSimilarItems(int numberOfRequestedItems, int[] queryVector){
        TreeSet<QueryResultItem> result = new TreeSet<>();
        for (IntegerVectorData item : data){
            result.add(new QueryResultItem(item.getId(), compare(queryVector, item.getVector())));
            if (result.size() > numberOfRequestedItems)
                result.pollFirst();
        }
        return result;
    }

    public float compare(int[] o1, int[] o2) {
        if (o1.length != o2.length)
            return 0;
        int count = 0;
        for (int i = 0; i < o1.length; i++) {
            if (o1[i] == o2[i])
                count++;
        }
        return count/(float)o1.length;
    }


}
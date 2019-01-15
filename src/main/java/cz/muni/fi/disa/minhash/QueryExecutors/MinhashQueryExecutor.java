package cz.muni.fi.disa.minhash.QueryExecutors;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import javafx.util.Pair;

import java.util.*;

public class MinhashQueryExecutor implements QueryExecutor{

    private IntegerVectorLoader loader;
    private List<IntegerVectorData> data;

    public static void main(String[] args) throws Exception{
            try {
                IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_1_2048.data", " ", 2048);
                MinhashQueryExecutor executor = new MinhashQueryExecutor(loader);
                QueryResult result = executor.findSimilarItems(50, "0000000002");
                for (QueryResultItem item : result.getItems())
                    System.out.println(item.getSimilarity() + " " + item.getId());
                System.out.println("Time: " + result.getExecutionTime());
            }catch (Exception e){
                throw new Exception(e);
            }
    }


    public MinhashQueryExecutor(IntegerVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToList();
    }

    public QueryResult findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<IntegerVectorData> query = data.stream().filter(x -> x.getId().equals(idOfQueryItem)).findAny();
        if (!query.isPresent())
            return null;
        QueryResult result = findSimilarItems(numberOfRequestedItems+1, query.get().getVector());
        ((TreeSet)result.getItems()).pollLast();
        return result;
    }

    public QueryResult findSimilarItems(int numberOfRequestedItems, int[] queryVector){
        TreeSet<QueryResultItem> result = new TreeSet<>();
        long begin = System.currentTimeMillis();
        for (IntegerVectorData item : data){
            result.add(new QueryResultItem(item.getId(), compare(queryVector, item.getVector())));
            if (result.size() > numberOfRequestedItems)
                result.pollFirst();
        }
        long end = System.currentTimeMillis();
        return new QueryResult(result, end - begin);
    }

    private float compare(int[] o1, int[] o2) {
        int count = 0;
        for (int i = 0; i < o1.length; i++) {
            if (o1[i] == o2[i])
                count++;
        }
        return count/(float)o1.length;
    }


}
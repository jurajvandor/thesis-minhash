package cz.muni.fi.disa.minhash.QueryExecutors;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.Evaluation.EvaluationResult;
import cz.muni.fi.disa.minhash.Experiments.ExperimentsUtils;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.*;

public class MinhashQueryExecutor implements QueryExecutor{
    public static void main(String[] args){
        try {
            MinhashQueryExecutor ex = new MinhashQueryExecutor(new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_2_2048_2.data", " ", 2048));
            for (String s : ExperimentsUtils.randomImageQueries100){
                QueryResult res = ex.findSimilarItems(20 , s);
                System.out.println(res.getExecutionTime());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private IntegerVectorLoader loader;
    private List<IntegerVectorData> data;

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
package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.QueryExecutors.QueryResult;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import java.util.HashMap;
import java.util.Map;

public class ReferenceImgResultsCache {
    private static Map<String, QueryResult> one;
    private static Map<String, QueryResult> five;
    private static Map<String, QueryResult> ten;
    private static Map<String, QueryResult> twenty;

    public static void inflate(){
        System.out.println("inflating reference cache");
        one = new HashMap<>();
        five = new HashMap<>();
        ten = new HashMap<>();
        twenty = new HashMap<>();
        try {
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096));
            for (String id : ExperimentsUtils.randomImageQueries100) {
                one.put(id, referenceQueryExecutor.findSimilarItems(1, id));
                five.put(id, referenceQueryExecutor.findSimilarItems(5, id));
                ten.put(id, referenceQueryExecutor.findSimilarItems(10, id));
                twenty.put(id, referenceQueryExecutor.findSimilarItems(20, id));
                System.out.print(".");
                System.out.flush();
            }
            System.out.println();
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static QueryResult get(int k, String query){
        if (k == 1)
            return one.get(query);
        if (k == 5)
            return five.get(query);
        if (k == 10)
            return ten.get(query);
        if (k == 20)
            return twenty.get(query);
        return null;
    }
}

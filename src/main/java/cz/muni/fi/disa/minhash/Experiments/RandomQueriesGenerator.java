package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomQueriesGenerator {
    public static void main(String[] args){
        try{
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            List<BooleanVectorData> l = loader.loadAllVectorsToList();
            List<String> ids = new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < 100; i++) {
                String randomId = l.get(rand.nextInt(l.size())).getId();
                if (ids.contains(randomId)){
                    i--;
                }else {
                    ids.add(randomId);
                    System.out.print("\"" + randomId + "\", ");
                }
            }
        } catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }
}

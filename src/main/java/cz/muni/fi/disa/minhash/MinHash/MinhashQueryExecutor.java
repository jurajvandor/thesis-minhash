package cz.muni.fi.disa.minhash.MinHash;

import cz.muni.fi.disa.minhash.DataHelpers.IntegerVectorData;

import java.util.List;

public class MinhashQueryExecutor {

    private String minhashFile;

    MinhashQueryExecutor(String minhashFile){
        this.minhashFile = minhashFile;
    }

    public List<IntegerVectorData> findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        return null;
    }

    public List<IntegerVectorData> findSimilarItems(int numberOfRequestedItems, int[] queryVector){
        return null;
    }
}
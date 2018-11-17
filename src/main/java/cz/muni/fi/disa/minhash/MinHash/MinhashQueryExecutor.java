package cz.muni.fi.disa.minhash.MinHash;

import cz.muni.fi.disa.minhash.DataHelpers.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHelpers.IntegerVectorLoader;
import sun.awt.util.IdentityArrayList;

import java.util.*;
import java.util.stream.Collectors;

public class MinhashQueryExecutor {

    private IntegerVectorLoader loader;
    private ArrayList<IntegerVectorData> data;

    MinhashQueryExecutor(IntegerVectorLoader loader){
        this.loader = loader;
        data = loader.loadAllVectorsToArrayList();
    }

    public List<IntegerVectorData> findSimilarItems(int numberOfRequestedItems, String idOfQueryItem){
        Optional<IntegerVectorData> query = data.stream().findFirst().filter(x -> x.getId().equals(idOfQueryItem));
        return query.isPresent() ? findSimilarItems(numberOfRequestedItems, query.get().getVector()) : null;
    }

    public List<IntegerVectorData> findSimilarItems(int numberOfRequestedItems, int[] queryVector){
        PriorityQueue<IntegerVectorData> result = new PriorityQueue<>(new MinhashComparator());
        for (IntegerVectorData item : data){
            result.add(item);
            if (result.size() > numberOfRequestedItems)
                result.poll();
        }
        return result.stream().sorted(new MinhashComparator()).collect(Collectors.toList());
    }

    public class MinhashComparator implements Comparator<IntegerVectorData> {

        @Override
        public int compare(IntegerVectorData o1, IntegerVectorData o2) {

            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof MinhashComparator;
        }
    }
}
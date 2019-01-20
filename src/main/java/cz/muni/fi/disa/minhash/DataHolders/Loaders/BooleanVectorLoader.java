package cz.muni.fi.disa.minhash.DataHolders.Loaders;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.util.List;

public class BooleanVectorLoader extends AbstractVectorLoader{

    public BooleanVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        super(path, delimiter, vectorSize);
        this.iterator = new BooleanVectorLoader.CustomIterator(this);
    }

    @Override
    public List<BooleanVectorData> loadAllVectorsToList() {
        return (List<BooleanVectorData>)super.loadAllVectorsToList();
    }

    private class CustomIterator extends AbstractVectorLoader.CustomIterator {

        public CustomIterator(BooleanVectorLoader loader){
            super(loader);
        }
        @Override
        public BooleanVectorData next() {
            boolean[] vector = new boolean[vectorSize];
            String id = nextLineId.split(" ")[2];
            String[] split = nextLineData.split(delimiter);
            for (int i = 0; i < vectorSize; i++) {
                vector[i] = !new Double(split[i]).equals(0.0);
            }
            setNextLine();
            return new BooleanVectorData(vector, id);
        }
    }
}

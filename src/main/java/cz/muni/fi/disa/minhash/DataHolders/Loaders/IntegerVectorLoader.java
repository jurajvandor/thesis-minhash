package cz.muni.fi.disa.minhash.DataHolders.Loaders;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.util.List;

public class IntegerVectorLoader extends AbstractVectorLoader {

    public IntegerVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        super(path, delimiter, vectorSize);
        this.iterator = new CustomIterator(this);
    }

    @Override
    public List<IntegerVectorData> loadAllVectorsToList() {
        return (List<IntegerVectorData>)super.loadAllVectorsToList();
    }

    private class CustomIterator extends AbstractVectorLoader.CustomIterator {

        public CustomIterator(IntegerVectorLoader loader){
            super(loader);
        }

        @Override
        public IntegerVectorData next() {
            int[] vector = new int[vectorSize];
            String id = nextLineId.split(" ")[2];
            String[] split = nextLineData.split(delimiter);
            for (int i = 0; i < vectorSize; i++) {
                vector[i] = Integer.parseInt(split[i]);
            }
            setNextLine();
            return new IntegerVectorData(vector, id);
        }
    }
}

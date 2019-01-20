package cz.muni.fi.disa.minhash.DataHolders.Loaders;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.util.List;

public class FloatVectorLoader extends AbstractVectorLoader {

    public FloatVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        super(path, delimiter, vectorSize);
        this.iterator = new CustomIterator(this);
    }

    @Override
    public List<FloatVectorData> loadAllVectorsToList() {
        return (List<FloatVectorData>)super.loadAllVectorsToList();
    }

    private class CustomIterator extends AbstractVectorLoader.CustomIterator {

        public CustomIterator(FloatVectorLoader loader){
            super(loader);
        }

        @Override
        public FloatVectorData next() {
            float[] vector = new float[vectorSize];
            String id = nextLineId.split(" ")[2];
            String[] split = nextLineData.split(delimiter);
            for (int i = 0; i < vectorSize; i++) {
                vector[i] = Float.parseFloat(split[i]);
            }
            setNextLine();
            return new FloatVectorData(vector, id);
        }
    }
}

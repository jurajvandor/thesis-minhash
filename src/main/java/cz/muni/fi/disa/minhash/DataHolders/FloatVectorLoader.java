package cz.muni.fi.disa.minhash.DataHolders;

import java.util.List;

public class FloatVectorLoader extends AbstractVectorLoader<FloatVectorData> {
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            FloatVectorLoader loader = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            List<FloatVectorData> d = loader.loadAllVectorsToLinkedList();
            for (FloatVectorData data: d) {
                System.out.append(data.toString());
                System.out.append("\n");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    public FloatVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        super(path, delimiter, vectorSize);
        this.iterator = new CustomIterator(this);
    }

    private class CustomIterator extends AbstractVectorLoader.CustomIterator {

        public CustomIterator(FloatVectorLoader loader){
            super(loader);
        }

        @Override
        public Object next() {
            this.nextCalled = true;
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

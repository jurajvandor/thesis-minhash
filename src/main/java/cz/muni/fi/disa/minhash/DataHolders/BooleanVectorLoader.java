package cz.muni.fi.disa.minhash.DataHolders;

import java.util.List;

public class BooleanVectorLoader extends AbstractVectorLoader<BooleanVectorData>{
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            List<BooleanVectorData> d = loader.loadAllVectorsToList();
            for (BooleanVectorData data: d) {
                System.out.append(data.toString());
                System.out.append("\n");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public BooleanVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        super(path, delimiter, vectorSize);
        this.iterator = new BooleanVectorLoader.CustomIterator(this);
    }

    private class CustomIterator extends AbstractVectorLoader.CustomIterator {

        public CustomIterator(BooleanVectorLoader loader){
            super(loader);
        }
        @Override
        public BooleanVectorData next() {
            nextCalled = true;
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

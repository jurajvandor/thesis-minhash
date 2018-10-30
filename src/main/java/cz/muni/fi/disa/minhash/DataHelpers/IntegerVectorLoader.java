package cz.muni.fi.disa.minhash.DataHelpers;

import java.lang.instrument.Instrumentation;
import java.util.List;

public class IntegerVectorLoader extends AbstractVectorLoader<IntegerVectorData> {
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_20.data", " ", 20);
            List<IntegerVectorData> d = loader.loadAllVectorsToLinkedList();
            for (IntegerVectorData data: d) {
                System.out.append(data.toString());
                System.out.append("\n");
            }
            System.out.print(d.size());
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    public IntegerVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        super(path, delimiter, vectorSize);
        this.iterator = new CustomIterator(this);
    }

    private class CustomIterator extends AbstractVectorLoader.CustomIterator {

        public CustomIterator(IntegerVectorLoader loader){
            super(loader);
        }

        @Override
        public Object next() {
            this.nextCalled = true;
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

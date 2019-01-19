package cz.muni.fi.disa.minhash.DataHolders.Loaders;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.util.List;

public class IntegerVectorLoader extends AbstractVectorLoader {
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_1000.data", " ", 1000);
            List<IntegerVectorData> d = loader.loadAllVectorsToList();
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

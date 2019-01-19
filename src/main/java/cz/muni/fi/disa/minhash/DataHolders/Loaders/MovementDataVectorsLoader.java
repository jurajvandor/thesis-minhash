package cz.muni.fi.disa.minhash.DataHolders.Loaders;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.BooleanVectorData;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.Joint;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.MovementData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.io.IOException;
import java.util.List;

public class MovementDataVectorsLoader extends AbstractVectorLoader{
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            MovementDataVectorsLoader loader = new MovementDataVectorsLoader("data_files/objects-annotations-specific-coords_normPOS.data", ";");
            List<MovementData> d = loader.loadAllVectorsToList();
            for (MovementData data: d) {
                System.out.append(data.getId());
                System.out.append("\n");
            }
            System.out.print(d.size());
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    public MovementDataVectorsLoader(String path, String delimiter) throws VectorLoaderException {
        super(path, delimiter, 0);
        this.iterator = new CustomIterator(this);
    }

    @Override
    public List<MovementData> loadAllVectorsToList() {
        return (List<MovementData>)super.loadAllVectorsToList();
    }

    class CustomIterator extends AbstractVectorLoader.CustomIterator{
        CustomIterator(MovementDataVectorsLoader loader) {
            super(loader);
        }

        private String[] nextData;
        private int nextDataNumberOfFrames;

        @Override
        protected void setNextLine(){
            try {
                nextLineId = loader.reader.readLine();
                if (nextLineId == null || nextLineId.equals("")) {
                    hasNext = false;
                    return;
                }
                String dataLengthLine = loader.reader.readLine();
                if (dataLengthLine == null || dataLengthLine.equals("")) {
                    hasNext = false;
                    return;
                }
                nextDataNumberOfFrames = Integer.parseInt(dataLengthLine.split(";")[0]);
                nextData = new String[nextDataNumberOfFrames];
                for (int i = 0; i < nextDataNumberOfFrames; i++) {
                    nextData[i] = loader.reader.readLine();
                    if (nextData[i] == null || nextData[i].equals("")) {
                        hasNext = false;
                        return;
                    }
                }
            } catch (IOException e) {
                hasNext = false;
            }
        }

        @Override
        public MovementData next() {
            String id = nextLineId.split(" ")[2];
            MovementData result = new MovementData(nextDataNumberOfFrames, id);
            for (int i = 0; i < nextDataNumberOfFrames; i++) {
                String[] split = nextData[i].split(delimiter);
                for (int j = 0; j < MovementData.N_OF_JOINTS; j++) {
                    String[] coords = split[j].split(", ");
                    Joint joint = new Joint(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]), Float.parseFloat(coords[2]));
                    result.getFrames()[i][j] = joint;
                }
            }
            setNextLine();
            return result;
        }
    }
}

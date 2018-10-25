package cz.muni.fi.disa.minhash.DataHelpers;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BooleanVectorLoader implements Iterable<BooleanVectorData>, Closeable{
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            BooleanVectorLoader loader = new BooleanVectorLoader("features-images-profiset100K.data", " ", 4096);
            List<BooleanVectorData> d = loader.loadAllVectorsToLinkedList();
            for (BooleanVectorData data: d) {
                System.out.append(data.toString());
                System.out.append("\n");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private CustomIterator iterator;
    private String path;
    private BufferedReader reader = null;
    private String delimiter;
    private int vectorSize;

    public BooleanVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException{
        this.delimiter = delimiter;
        this.path = path;
        this.vectorSize = vectorSize;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        if (!file.exists()) {
            return;
        }
        try {
            InputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
        }catch (IOException e){
            throw new VectorLoaderException("file " + path + " could not be loaded", e);
        }
        iterator = new CustomIterator(this);
    }

    class CustomIterator implements Iterator<BooleanVectorData>{
        private boolean hasNext = true;
        private String nextLineId;
        private String nextLineData;
        private BooleanVectorLoader loader;
        private boolean nextCalled = false;

        CustomIterator(BooleanVectorLoader loader) {
            this.loader = loader;
            try {
                nextLineId = loader.reader.readLine();
                if (nextLineId == null || nextLineId.equals(""))
                    hasNext = false;
                nextLineData = loader.reader.readLine();
                if (nextLineData == null || nextLineData.equals(""))
                    hasNext = false;
            }catch (IOException e){
                hasNext = false;
                nextLineId = null;
                nextLineData = null;
            }
        }

        @Override
        public boolean hasNext(){
            return hasNext;
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
            try {
                nextLineId = loader.reader.readLine();
                if (nextLineId == null || nextLineId.equals(""))
                    hasNext = false;
                else {
                    nextLineData = loader.reader.readLine();
                    if (nextLineData == null || nextLineData.equals(""))
                        hasNext = false;
                }
            } catch (IOException e) {
                hasNext = false;
                return new BooleanVectorData(vector, id);
            }
            return new BooleanVectorData(vector, id);
        }
    }

    public LinkedList<BooleanVectorData> loadAllVectorsToLinkedList(){
        if (iterator.nextCalled)
            return null;
        LinkedList<BooleanVectorData> list = new LinkedList<>();
        for (BooleanVectorData data : this) {
            list.add(data);
        }
        return list;
    }

    public ArrayList<BooleanVectorData> loadAllVectorsToArrayList(){
        if (iterator.nextCalled)
            return null;
        ArrayList<BooleanVectorData> arrayList = new ArrayList<>();
        for (BooleanVectorData data : this){
            arrayList.add(data);
        }
        return arrayList;
    }

    @Override
    public void close() throws IOException {
        if (reader != null)
            reader.close();
        reader = null;
    }

    public String getPath() {
        return path;
    }

    @Override
    public Iterator<BooleanVectorData> iterator() {
        return iterator;
    }
}

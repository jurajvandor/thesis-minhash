package cz.muni.fi.disa.minhash.DataHelpers;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VectorLoader implements Iterable<VectorData>, Closeable{
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            VectorLoader loader = new VectorLoader("features-images-profiset100K.data", " ", 4096);
            List<VectorData> d = loader.loadAllVectorsToLinkedList();
            for (VectorData data: d) {
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

    public VectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException{
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

    class CustomIterator implements Iterator<VectorData>{
        private boolean hasNext = true;
        private String nextLineId;
        private String nextLineData;
        private VectorLoader loader;
        private boolean nextCalled = false;

        CustomIterator(VectorLoader loader) {
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
        public VectorData next() {
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
                return new VectorData(vector, id);
            }
            return new VectorData(vector, id);
        }
    }

    public LinkedList<VectorData> loadAllVectorsToLinkedList(){
        if (iterator.nextCalled)
            return null;
        LinkedList<VectorData> list = new LinkedList<>();
        for (VectorData data : this) {
            list.add(data);
        }
        return list;
    }

    public ArrayList<VectorData> loadAllVectorsToArrayList(){
        if (iterator.nextCalled)
            return null;
        ArrayList<VectorData> arrayList = new ArrayList<>();
        for (VectorData data : this){
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
    public Iterator<VectorData> iterator() {
        return iterator;
    }
}

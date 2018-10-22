package cz.muni.fi.disa.minhash;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VectorLoader implements Iterable<VectorData>, Closeable{
    //TODO delete test
    public static void main(String[] args) throws Exception{
        try {
            VectorLoader loader = new VectorLoader("features-images-profiset100K.data", " ");
            List<VectorData> d = loader.loadAllVectors();
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

    public VectorLoader(String path, String delimiter) throws PermutationException{
        this.delimiter = delimiter;
        this.path = path;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        if (!file.exists()) {
            return;
        }
        try {
            InputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
        }catch (IOException e){
            throw new PermutationException("file " + path + " could not be loaded", e);
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
            List<Boolean> vector = new ArrayList<>();
            String id = nextLineId.split(" ")[2];
            String[] split = nextLineData.split(delimiter);
            for (String num : split) {
                vector.add(!new Double(num).equals(0.0));
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

    public List<VectorData> loadAllVectors(){
        if (iterator.nextCalled)
            return null;
        List<VectorData> list = new ArrayList<>();
        for (VectorData data : this) {
            list.add(data);
        }
        return list;
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

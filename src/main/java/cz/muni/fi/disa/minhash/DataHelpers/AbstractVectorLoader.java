package cz.muni.fi.disa.minhash.DataHelpers;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class AbstractVectorLoader<T> implements Iterable<T>, Closeable{

    protected CustomIterator iterator;
    private String path;
    protected BufferedReader reader = null;
    protected String delimiter;
    protected int vectorSize;

    public AbstractVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException{
        this.delimiter = delimiter;
        this.path = path;
        this.vectorSize = vectorSize;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(getPath());
        if (!file.exists()) {
            return;
        }
        try {
            InputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
        }catch (IOException e){
            throw new VectorLoaderException("file " + path + " could not be loaded", e);
        }
    }

    abstract class CustomIterator implements Iterator<T>{
        protected boolean hasNext = true;
        protected String nextLineId;
        protected String nextLineData;
        protected AbstractVectorLoader<T> loader;
        protected boolean nextCalled = false;

        CustomIterator(AbstractVectorLoader<T> loader) {
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

        protected void setNextLine(){
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
            }
        }
    }

    public LinkedList<T> loadAllVectorsToLinkedList(){
        if (iterator.nextCalled)
            return null;
        LinkedList<T> list = new LinkedList<>();
        for (T data : this) {
            list.add(data);
        }
        return list;
    }

    public ArrayList<T> loadAllVectorsToArrayList(){
        if (iterator.nextCalled)
            return null;
        ArrayList<T> arrayList = new ArrayList<>();
        for (T data : this){
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
    public Iterator<T> iterator() {
        return iterator;
    }
}

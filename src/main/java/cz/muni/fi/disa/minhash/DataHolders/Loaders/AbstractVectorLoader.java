package cz.muni.fi.disa.minhash.DataHolders.Loaders;

import cz.muni.fi.disa.minhash.DataHolders.ObjectData.AbstractVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractVectorLoader implements Iterable<AbstractVectorData>, Closeable{

    protected CustomIterator iterator;
    private String path;
    protected BufferedReader reader = null;
    protected String delimiter;
    protected int vectorSize;
    protected List<AbstractVectorData> cache = null;

    public String getDelimiter() {
        return delimiter;
    }

    public int getVectorSize() {
        return vectorSize;
    }

    public AbstractVectorLoader(String path, String delimiter, int vectorSize) throws VectorLoaderException {
        this.delimiter = delimiter;
        this.path = path;
        this.vectorSize = vectorSize;
        resetLoader();
    }

    public void resetLoader() throws VectorLoaderException{
        File file = new File(getPath());
        if (!file.exists()) {
            throw new VectorLoaderException("file " + path + " does not exist");
        }
        try {
            InputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
        }catch (IOException e){
            throw new VectorLoaderException("file " + path + " could not be loaded", e);
        }
    }

    abstract class CustomIterator implements Iterator<AbstractVectorData>{
        protected boolean hasNext;
        protected String nextLineId;
        protected String nextLineData;
        protected AbstractVectorLoader loader;

        CustomIterator(AbstractVectorLoader loader) {
            this.loader = loader;
            this.hasNext = true;
            setNextLine();
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

    public List<?> loadAllVectorsToList(){
        if (cache != null)
            return cache;
        LinkedList<AbstractVectorData> list = new LinkedList<>();
        for (AbstractVectorData data : this) {
            list.add(data);
        }
        cache = list;
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
    public Iterator<AbstractVectorData> iterator() {
        if (cache != null)
            return cache.iterator();
        try {
            resetLoader();
        } catch (VectorLoaderException e){
            e.printStackTrace();
        }
        iterator.hasNext = true;
        iterator.setNextLine();
        return iterator;
    }
}

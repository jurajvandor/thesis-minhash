package cz.muni.fi.disa.minhash.DataHolders;

public class VectorLoaderException extends Exception {
    public VectorLoaderException(String string){
        super(string);
    }

    public VectorLoaderException(String string, Throwable throwable){
        super(string, throwable);
    }
}

package cz.muni.fi.disa.minhash.MinhashCreators;

public class MinhashException extends Exception {
    MinhashException(String message){
        super(message);
    }
    MinhashException(String message, Throwable throwable){
        super(message, throwable);
    }
}

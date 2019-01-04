package cz.muni.fi.disa.minhash.DataHolders;

public class PermutationException extends Exception {
    PermutationException(String message){
        super(message);
    }
    PermutationException(String message, Throwable throwable){
        super(message, throwable);
    }
}

package cz.muni.fi.disa.minhash.MinhashCreators;

public interface MinhashCreator {
    String createMinhashes() throws MinhashException;
    String createBinarySignatures() throws  MinhashException;
}

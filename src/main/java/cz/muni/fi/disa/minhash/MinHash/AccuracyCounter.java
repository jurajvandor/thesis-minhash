package cz.muni.fi.disa.minhash.MinHash;

public class AccuracyCounter {
    private String originalDataFile;
    private String minahshFile;

    AccuracyCounter(String originalDataFile, String minhashFile){
        this.minahshFile = minhashFile;
        this.originalDataFile = originalDataFile;
    }
}

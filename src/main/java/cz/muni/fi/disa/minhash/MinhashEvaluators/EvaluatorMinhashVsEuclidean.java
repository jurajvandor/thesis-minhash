package cz.muni.fi.disa.minhash.MinhashEvaluators;

public class EvaluatorMinhashVsEuclidean {
    private String originalDataFile;
    private String minahshFile;

    EvaluatorMinhashVsEuclidean(String originalDataFile, String minhashFile){
        this.minahshFile = minhashFile;
        this.originalDataFile = originalDataFile;
    }
}

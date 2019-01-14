package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;

public class QuantizationMinhashCreator implements MinhashCreator{
    private FloatVectorLoader loader;
    private PermutationGenerator generator;

    public QuantizationMinhashCreator(FloatVectorLoader loader, int numberOfBucketsForValue, int minhashVectorSize){
        this.loader = loader;
        this.generator = new PermutationGenerator(numberOfBucketsForValue*loader.getVectorSize(), minhashVectorSize);
    }

    public FloatVectorLoader getLoader() {
        return loader;
    }

    public void setLoader(FloatVectorLoader loader) {
        this.loader = loader;
    }

    public PermutationGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(PermutationGenerator generator) {
        this.generator = generator;
    }
    @Override
    public void createMinhashes() throws MinhashException {

    }
}

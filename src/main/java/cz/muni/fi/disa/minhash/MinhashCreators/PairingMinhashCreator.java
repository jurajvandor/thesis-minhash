package cz.muni.fi.disa.minhash.MinhashCreators;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.PermutationGenerator;

public class PairingMinhashCreator implements MinhashCreator{

    private BooleanVectorLoader loader;
    private PermutationGenerator generator;

    public PairingMinhashCreator(BooleanVectorLoader loader, int minhashVectorSize){
        this.loader = loader;
        this.generator = new PermutationGenerator(loader.getVectorSize()*loader.getVectorSize(), minhashVectorSize);
    }

    public BooleanVectorLoader getLoader() {
        return loader;
    }

    public void setLoader(BooleanVectorLoader loader) {
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

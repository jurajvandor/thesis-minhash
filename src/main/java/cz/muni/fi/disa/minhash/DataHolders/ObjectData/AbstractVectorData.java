package cz.muni.fi.disa.minhash.DataHolders.ObjectData;

public abstract class AbstractVectorData {
    protected String id;

    public AbstractVectorData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

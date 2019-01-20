package cz.muni.fi.disa.minhash.Experiments;

public class ExtraInfoForCsv {
    private String path;
    private String header;
    private String data;

    public ExtraInfoForCsv(String path, String header, String data) {
        this.path = path;
        this.header = header;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

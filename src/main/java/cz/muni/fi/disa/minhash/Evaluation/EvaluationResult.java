package cz.muni.fi.disa.minhash.Evaluation;

public class EvaluationResult {
    long timeFirst;
    long timeSecond;
    int sameItems;

    public EvaluationResult(long timeFirst, long timeSecond, int sameItems) {
        this.timeFirst = timeFirst;
        this.timeSecond = timeSecond;
        this.sameItems = sameItems;
    }

    public long getTimeFirst() {
        return timeFirst;
    }

    public void setTimeFirst(long timeFirst) {
        this.timeFirst = timeFirst;
    }

    public long getTimeSecond() {
        return timeSecond;
    }

    public void setTimeSecond(long timeSecond) {
        this.timeSecond = timeSecond;
    }

    public int getSameItems() {
        return sameItems;
    }

    public void setSameItems(int sameItems) {
        this.sameItems = sameItems;
    }
}

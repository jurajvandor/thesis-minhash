package cz.muni.fi.disa.minhash.Evaluation;

import cz.muni.fi.disa.minhash.QueryExecutors.QueryResult;

public class EvaluationMotionResult extends EvaluationResult{
    private int sameCatFirst;
    private int sameCatSecond;

    public EvaluationMotionResult(int sameItems, QueryResult first, QueryResult second, int sameCatFirst, int sameCatSecond){
        super(sameItems, first, second);
        this.sameCatFirst = sameCatFirst;
        this.sameCatSecond = sameCatSecond;
    }

    public int getSameCatFirst() {
        return sameCatFirst;
    }

    public void setSameCatFirst(int sameCatFirst) {
        this.sameCatFirst = sameCatFirst;
    }

    public int getSameCatSecond() {
        return sameCatSecond;
    }

    public void setSameCatSecond(int sameCatSecond) {
        this.sameCatSecond = sameCatSecond;
    }

    @Override
    public String toString() {
        return sameItems + "," + first.getExecutionTime() + "," + second.getExecutionTime() + "," + sameCatFirst + "," + sameCatSecond;
    }
}

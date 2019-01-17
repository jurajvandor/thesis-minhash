package cz.muni.fi.disa.minhash.Evaluation;

public class AverageResult {
    private int count = 0;
    private int same = 0;
    private int timeFirst = 0;
    private int timeSecond = 0;
    private int sameCatFirst = 0;
    private int sameCatSecond = 0;
    private boolean useCats;

    public AverageResult(boolean useCategories){
        useCats = useCategories;
    }

    public void add(EvaluationResult result){
        count++;
        same += result.getSameItems();
        timeFirst += result.first.getExecutionTime();
        timeSecond += result.second.getExecutionTime();
    }

    public void add(EvaluationMotionResult result){
        count++;
        same += result.getSameItems();
        timeFirst += result.first.getExecutionTime();
        timeSecond += result.second.getExecutionTime();
        sameCatFirst += result.getSameCatFirst();
        sameCatSecond += result.getSameCatSecond();
    }

    @Override
    public String toString() {
        float count = (float)this.count;
        String ret;
        if (useCats)
            ret = same/count + "," + timeFirst/count + "," + timeSecond/count + "," + sameCatFirst/count + "," + sameCatSecond/count;
        else
            ret = same/count + "," + timeFirst/count + "," + timeSecond/count + ",,";
        return ret;
    }

    public static String getCsvHeader(){
        return "SameItems,FirstTime,SecondTime,FirstInCat,SecondInCat";
    }
}

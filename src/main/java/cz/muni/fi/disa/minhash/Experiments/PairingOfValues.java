package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.IntegerVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.Evaluation.EvaluationResult;
import cz.muni.fi.disa.minhash.Evaluation.Evaluator;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PairingOfValues {
    public static void main(String[] args){
        try{
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_3_2048.data", " ", 2048);
            MinhashQueryExecutor minhash = new MinhashQueryExecutor(loader);
            ReferenceQueryExecutor ref = new ReferenceQueryExecutor(new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096));
            Evaluator ev = new Evaluator(minhash, ref);
            List<String> ids = Arrays.asList("0000237248", "0000049079", "0000078545", "0000168516", "0000521035", "0000120332",
                    "0000132128", "0000149626", "0000142613", "0000081166", "0000216686", "0000114132", "0000184856", "0000008807",
                    "0000132174", "0000700398", "0000115063", "0000108589", "0000047037", "0000090174", "0000115650", "0000000228",
                    "0000084223", "0000375954", "0000127787", "0000115585", "0000174948", "0000085456", "0000051478", "0000012140",
                    "0000016781", "0000172147", "0000164445", "0000145665", "0000132057", "0000012391", "0000108758", "0000430482",
                    "0000052617", "0000430868", "0000114247", "0000059419", "0000138128", "0000120094", "0000698923", "0000089599",
                    "0000212162", "0000159264", "0000156873", "0000149396", "0000162775", "0000012412", "0000165676", "0000172427",
                    "0000023415", "0000002185", "0000431119", "0000093736", "0000146035", "0000423576", "0000155549", "0000107743",
                    "0000135891", "0000160095", "0000116818", "0000116053", "0000147399", "0000001304", "0000140279", "0000151229",
                    "0000027016", "0000111677", "0000022930", "0000102086", "0000346975", "0000196921", "0000003740", "0000700765",
                    "0000023217", "0000107553", "0000196617", "0000069948", "0000018614", "0000103924", "0000697036", "0000026340",
                    "0000041740", "0000045852", "0000271795", "0000159967", "0000119925", "0000000282", "0000116144", "0000086744",
                    "0000022937", "0000014390", "0000383237", "0000521310", "0000118380", "0000348860");
            long timeFirst = 0;
            long timeSecond = 0;
            int sameItems = 0;
            for (String id : ids) {
                EvaluationResult result = ev.executeAndEvaluate(20, id);
                timeFirst += result.getFirst().getExecutionTime();
                timeSecond += result.getSecond().getExecutionTime();
                sameItems += result.getSameItems();
            }
            int len = ids.size();
            System.out.println("same: " + sameItems/(float)len + " times: " + timeFirst/len + " " + timeSecond/len);
        } catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }
}

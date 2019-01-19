package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.Evaluation.AverageResult;
import cz.muni.fi.disa.minhash.Evaluation.EvaluationMotionResult;
import cz.muni.fi.disa.minhash.Evaluation.EvaluationResult;
import cz.muni.fi.disa.minhash.Evaluation.Evaluator;
import cz.muni.fi.disa.minhash.MinhashCreators.AbstractMinhashCreator;
import cz.muni.fi.disa.minhash.MinhashCreators.MinhashException;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.QueryExecutor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ExperimentsUtils {
    public static final List<String> randomImageQueries100 = Arrays.asList(
            "0000237248", "0000049079", "0000078545", "0000168516", "0000521035", "0000120332",
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

    public static void checkMinhashLengthsAndQuerySizes(AbstractMinhashCreator creator, QueryExecutor reference,
                                                        String resultingCsvPath, List<String> queries, boolean motion){
        for (int i = 1; i < 17;i++){
            creator.setMinhashVectorSize(i*128);
            try {
                System.out.println("creating minhash " + i*128);
                String path = creator.createMinhashes();
                System.out.println("minhash " + i*128 + " created");
                Evaluator evaluator = new Evaluator(new MinhashQueryExecutor(new IntegerVectorLoader(path, " ", i*128)), reference);
                System.out.println("-k=1");
                checkQuerySizes(evaluator, resultingCsvPath + i*128, queries, motion,1);
                System.out.println("-k=5");
                checkQuerySizes(evaluator, resultingCsvPath + i*128, queries, motion,5);
                System.out.println("-k=10");
                checkQuerySizes(evaluator, resultingCsvPath + i*128, queries, motion,10);
                System.out.println("-k=20");
                checkQuerySizes(evaluator, resultingCsvPath + i*128, queries, motion,20);
            }catch (MinhashException| VectorLoaderException e){
                e.printStackTrace();
            }
        }
    }

    public static void checkQuerySizes(Evaluator evaluator, String resultingCsvPath, List<String> queries, boolean motion, int querySize){
        AverageResult avg = new AverageResult(motion);
        if (motion) {
            for (String id : queries) {
                EvaluationMotionResult res = evaluator.executeAndEvaluateMotion(querySize, id);
                avg.add(res);
                System.out.print(".");
            }
        } else {
            for (String id : queries) {
                EvaluationResult res = evaluator.executeAndEvaluate(querySize, id);
                avg.add(res);
                System.out.print(".");
            }
        }
        System.out.println();
        try {
            File f = new File(resultingCsvPath + "_" + querySize + ".csv");
            if (!f.getParentFile().exists() && !f.getParentFile().mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + f.getParent());
            }
            if (!f.exists() && !f.createNewFile()){
                throw new IllegalStateException("Couldn't create file: " + f.getPath());
            }
            PrintWriter out = new PrintWriter(f);
            out.println(avg);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

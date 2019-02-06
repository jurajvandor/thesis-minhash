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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ExperimentsUtils {
    public static final List<String> randomImageQueries100 = Arrays.asList(
            "0000237248", "0000049079", "0000078545", "0000168516", "0000521035", "0000120332", "0000132128", "0000149626",
            "0000142613", "0000081166", "0000216686", "0000114132", "0000184856", "0000008807", "0000132174", "0000700398",
            "0000115063", "0000108589", "0000047037", "0000090174", "0000115650", "0000000228", "0000084223", "0000375954",
            "0000127787", "0000115585", "0000174948", "0000085456", "0000051478", "0000012140", "0000016781", "0000172147",
            "0000164445", "0000145665", "0000132057", "0000012391", "0000108758", "0000430482", "0000052617", "0000430868",
            "0000114247", "0000059419", "0000138128", "0000120094", "0000698923", "0000089599", "0000212162", "0000159264",
            "0000156873", "0000149396", "0000162775", "0000012412", "0000165676", "0000172427", "0000023415", "0000002185",
            "0000431119", "0000093736", "0000146035", "0000423576", "0000155549", "0000107743", "0000135891", "0000160095",
            "0000116818", "0000116053", "0000147399", "0000001304", "0000140279", "0000151229", "0000027016", "0000111677",
            "0000022930", "0000102086", "0000346975", "0000196921", "0000003740", "0000700765", "0000023217", "0000107553",
            "0000196617", "0000069948", "0000018614", "0000103924", "0000697036", "0000026340", "0000041740", "0000045852",
            "0000271795", "0000159967", "0000119925", "0000000282", "0000116144", "0000086744", "0000022937", "0000014390",
            "0000383237", "0000521310", "0000118380", "0000348860");
    public static final List<String> randomMotionQueries100 = Arrays.asList("3251_117_2751_164.png", "3243_115_2750_157.png",
            "3361_24_1900_203.png", "3243_118_3398_625.png", "3243_117_3614_205.png", "3212_136_2773_335.png",
            "3208_82_3447_420.png", "3437_147_1015_83.png", "3360_76_708_210.png", "3177_42_1057_118.png",
            "3205_86_2688_346.png", "3405_83_186_134.png", "3322_36_794_331.png", "3279_48_946_127.png",
            "3422_92_620_454.png", "3309_83_569_206.png", "3293_46_1077_106.png", "3224_47_1739_83.png",
            "3365_91_938_240.png", "3300_31_956_152.png", "3455_147_1217_74.png", "3269_67_2992_226.png",
            "3221_130_4411_133.png", "3365_88_3738_135.png", "3399_124_644_280.png", "3169_116_1920_409.png",
            "3208_79_3087_360.png", "3242_27_3195_440.png", "3310_74_900_221.png", "3393_44_1094_107.png",
            "3430_144_1561_85.png", "3150_109_808_87.png", "3401_102_1843_283.png", "3183_83_424_283.png",
            "3238_55_399_317.png", "3448_147_769_58.png", "3172_36_767_318.png", "3422_80_1459_402.png",
            "3251_112_1658_129.png", "3288_76_741_293.png", "3201_49_1304_72.png", "3293_43_976_207.png",
            "3259_130_4149_141.png", "3186_109_796_77.png", "3238_44_1120_100.png", "3387_65_600_433.png",
            "3169_114_2047_151.png", "3346_33_6193_487.png", "3273_102_1982_249.png", "3253_99_1064_54.png",
            "3234_94_1999_408.png", "3421_133_3627_300.png", "3447_152_1040_353.png", "3215_118_2949_600.png",
            "3429_144_1564_80.png", "3451_145_1479_160.png", "3214_43_747_240.png", "3244_73_3069_231.png",
            "3386_65_540_395.png", "3146_83_230_249.png", "3259_131_5727_75.png", "3291_25_1780_376.png",
            "3375_44_1097_92.png", "3293_50_1444_157.png", "3236_37_805_166.png", "3221_132_5396_343.png",
            "3440_147_783_77.png", "3427_68_3432_360.png", "3399_128_7418_640.png", "3321_44_701_84.png",
            "3261_128_6182_500.png", "3239_75_1400_213.png", "3333_80_1847_614.png", "3386_63_2438_325.png",
            "3394_121_8289_620.png", "3236_28_3876_571.png", "3181_94_1358_260.png", "3430_144_1485_76.png",
            "3224_44_1272_121.png", "3355_95_2824_480.png", "3440_141_3251_84.png", "3241_99_1183_131.png",
            "3346_32_6193_287.png", "3181_96_1878_540.png", "3375_50_1475_120.png", "3356_132_4945_302.png",
            "3145_44_1095_114.png", "3217_26_2912_288.png", "3291_30_605_330.png", "3384_77_1397_166.png",
            "3274_30_718_341.png", "3243_119_953_177.png", "3361_38_7778_285.png", "3152_125_2969_161.png",
            "3353_65_460_462.png", "3141_114_1801_178.png", "3300_29_4433_378.png", "3253_101_1335_56.png",
            "3251_120_360_386.png", "3414_83_981_219.png");

    public static void checkMinhashLengthsAndQuerySizes(AbstractMinhashCreator creator, QueryExecutor reference,
                                                        String resultingCsvPath, List<String> queries, EvaluationType motion,
                                                        ExtraInfoForCsv extraAppend){
        System.out.println("starting evaluation");
        List<Integer> minhashSizes = Arrays.asList(64,128,256,512,1024);
        for (int i : minhashSizes){
            creator.setMinhashVectorSize(i);
            try {
                System.out.println(currentTime() + " creating minhash " + i);
                String path = creator.createMinhashes();
                System.out.println(currentTime() + " minhash " + path + " created");
                MinhashQueryExecutor minhash = new MinhashQueryExecutor(new IntegerVectorLoader(path, " ", i));
                if (motion == EvaluationType.NO_MOTION)
                    minhash.findSimilarItems(1, "0000000002");
                else
                    minhash.findSimilarItems(1, "3136_103_280_78.png");
                Evaluator evaluator = new Evaluator(minhash, reference);
                System.out.println(currentTime() + " -k=1");
                checkQuerySizes(evaluator, resultingCsvPath, queries, motion,1, extraAppend, i);
                System.out.println(currentTime() + " -k=5");
                checkQuerySizes(evaluator, resultingCsvPath, queries, motion,5, extraAppend, i);
                System.out.println(currentTime() + " -k=10");
                checkQuerySizes(evaluator, resultingCsvPath, queries, motion,10, extraAppend, i);
                System.out.println(currentTime() + " -k=20");
                checkQuerySizes(evaluator, resultingCsvPath, queries, motion,20, extraAppend, i);
            }catch (MinhashException | VectorLoaderException e){
                e.printStackTrace();
            }
        }
    }

    public static void checkQuerySizes(Evaluator evaluator, String resultingCsvPath, List<String> queries, EvaluationType motion,
                                       int querySize, ExtraInfoForCsv extraAppend, int minhashSize){
        try {
            File f = new File(resultingCsvPath + minhashSize + "_" + querySize + ".csv");
            if (!f.getParentFile().exists() && !f.getParentFile().mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + f.getParent());
            }
            if (!f.exists() && !f.createNewFile()){
                throw new IllegalStateException("Couldn't create file: " + f.getPath());
            }
            PrintWriter out = new PrintWriter(f);
            out.println(EvaluationResult.getCsvHeader());
            AverageResult avg = new AverageResult(motion != EvaluationType.NO_MOTION);
            if (motion == EvaluationType.MOTION) {
                for (String id : queries) {
                    EvaluationMotionResult res = evaluator.executeAndEvaluateMotion(querySize, id, false);
                    out.println(res);
                    avg.add(res);
                    System.out.print(".");
                    System.out.flush();
                }
            } else if (motion == EvaluationType.MOTION_IGNORE_PNG){
                for (String id : queries) {
                    EvaluationMotionResult res = evaluator.executeAndEvaluateMotion(querySize, id, true);
                    out.println(res);
                    avg.add(res);
                    System.out.print(".");
                    System.out.flush();
                }
            }else if (motion == EvaluationType.NO_MOTION){
                for (String id : queries) {
                    EvaluationResult res = evaluator.executeAndEvaluate(querySize, id);
                    out.println(res);
                    avg.add(res);
                    System.out.print(".");
                    System.out.flush();
                }
            }
            out.println(avg);
            System.out.println(" " + avg);
            ExperimentsUtils.extraAppend(avg, new ExtraInfoForCsv(resultingCsvPath + "avg",
                    "", ""), querySize, minhashSize);
            if (extraAppend != null){
                ExperimentsUtils.extraAppend(avg, extraAppend, querySize, minhashSize);
            }
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void extraAppend(AverageResult averageResult, ExtraInfoForCsv extraInfoForCsv, int querySize, int minhashSize){
        String p = extraInfoForCsv.getPath() + querySize;
        Path path = Paths.get(p + ".csv");
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.write(path, (extraInfoForCsv.getHeader() + "minhashSize," + EvaluationResult.getCsvHeader() +
                        System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }
            Files.write(path, (extraInfoForCsv.getData() + minhashSize + "," + averageResult.toString()+ System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);
        }catch (IOException e){
                e.printStackTrace();
        }
    }

    public static String currentTime(){
        LocalDateTime time = LocalDateTime.now();
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }
}

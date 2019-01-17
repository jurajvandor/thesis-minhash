package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.IntegerVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.ObjectData.FloatVectorData;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.Evaluation.EvaluationResult;
import cz.muni.fi.disa.minhash.Evaluation.Evaluator;
import cz.muni.fi.disa.minhash.QueryExecutors.MinhashQueryExecutor;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Quantization {
    public static void main(String[] args){
        try{
            IntegerVectorLoader loader = new IntegerVectorLoader("data_files/features-images-profiset100K_minhash_2_2048.data", " ", 2048);
            MinhashQueryExecutor minhash = new MinhashQueryExecutor(loader);
            FloatVectorLoader loader2 = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            ReferenceQueryExecutor ref = new ReferenceQueryExecutor(loader2);
            Evaluator ev = new Evaluator(minhash, ref);
            List<String> ids = Arrays.asList("0000120613", "0000091642", "0000176392", "0000034838", "0000106345", "0000083800", "0000137937", "0000247864", "0000163157", "0000288291", "0000521351", "0000168129", "0000079571", "0000142783", "0000043186", "0000128607", "0000162086", "0000120854", "0000430869", "0000001000", "0000050943", "0000163213", "0000082880", "0000084309", "0000411664", "0000017082", "0000034856", "0000098288", "0000134894", "0000155721", "0000085793", "0000066883", "0000008999", "0000153905", "0000126950", "0000116115", "0000047039", "0000152735", "0000029422", "0000142006", "0000114830", "0000133170", "0000100952", "0000073177", "0000292478", "0000113091", "0000103996", "0000699783", "0000146108", "0000136469", "0000138474", "0000145613", "0000132581", "0000153236", "0000143456", "0000087657", "0000282939", "0000080067", "0000055234", "0000109620", "0000130210", "0000000690", "0000086604", "0000268005", "0000132941", "0000099370", "0000144447", "0000073887", "0000164174", "0000151543", "0000085718", "0000225823", "0000041982", "0000059905", "0000067136", "0000138683", "0000151065", "0000058017", "0000128901", "0000131232", "0000266093", "0000042249", "0000109478", "0000042481", "0000520220", "0000011563", "0000000618", "0000083774", "0000171588", "0000107351", "0000031130", "0000113934", "0000520539", "0000696292", "0000130312", "0000030547", "0000168538", "0000271799", "0000411853", "0000108507");
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

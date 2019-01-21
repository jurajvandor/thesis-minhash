package cz.muni.fi.disa.minhash.Experiments;

import cz.muni.fi.disa.minhash.DataHolders.Loaders.BooleanVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.FloatVectorLoader;
import cz.muni.fi.disa.minhash.DataHolders.Loaders.MovementDataVectorsLoader;
import cz.muni.fi.disa.minhash.DataHolders.VectorLoaderException;
import cz.muni.fi.disa.minhash.MinhashCreators.*;
import cz.muni.fi.disa.minhash.QueryExecutors.ReferenceQueryExecutor;

import static cz.muni.fi.disa.minhash.Experiments.ExperimentsUtils.currentTime;


public class ExperimentScripts {
    public static void main(String[] args){
        binaryMappingImg();
        pairingOfValuesImg();
        quantizationImg();
        binaryMappingMotion();
        pairingOfValuesMotion();
        quantizationMotion();
        cubeFrom3dCoordsMotion();
    }

    public static void binaryMappingImg(){
        try {
            System.out.println(currentTime() + " binary mapping img - loading reference");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            AbstractMinhashCreator creator = new BinaryMappingMinhashCreator(loader, 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096));
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/binary/img/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION, null);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void pairingOfValuesImg(){
        try {
            System.out.println(currentTime() + " pairing img - loading reference");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            AbstractMinhashCreator creator = new PairingMinhashCreator(loader, 4096, true);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096));
            System.out.println("and");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/pairing/and/img/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION, null);
            creator = new PairingMinhashCreator(loader, 4096, false);
            System.out.println("or");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/pairing/or/img/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION, null);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void quantizationImg(){
        try {
            System.out.println(currentTime() + " quantization img - loading reference");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            for (int i = 2; i < 9; i++) {
                AbstractMinhashCreator creator = new QuantizationMinhashCreator(loader, 4096, i);
                ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                        "results/quantization/img/" + i + "/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION,
                        new ExtraInfoForCsv("results/quantization/img/", "buckets,", i + ","));
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }

    public static void binaryMappingMotion(){
        try {
            System.out.println(currentTime() + " binary mapping motion- loading reference");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            AbstractMinhashCreator creator = new BinaryMappingMinhashCreator(loader, 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096));
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/binary/motion/", ExperimentsUtils.randomMotionQueries100, EvaluationType.MOTION, null);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void pairingOfValuesMotion(){
        try {
            System.out.println(currentTime() + " pairing motion - loading reference");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            AbstractMinhashCreator creator = new PairingMinhashCreator(loader, 4096, true);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096));
            System.out.println("and");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/pairing/and/motion/", ExperimentsUtils.randomMotionQueries100, EvaluationType.MOTION, null);
            creator = new PairingMinhashCreator(loader, 4096, false);
            System.out.println("or");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/pairing/or/motion/", ExperimentsUtils.randomMotionQueries100, EvaluationType.MOTION, null);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void quantizationMotion(){
        try {
            System.out.println(currentTime() + " quantization motion - loading reference");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            QuantizationMinhashCreator creator = new QuantizationMinhashCreator(loader, 4096, 2);
            for (int i = 2; i < 9; i++) {
                creator.setNumberOfBuckets(i);
                ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                        "results/quantization/motion/" + i + "/", ExperimentsUtils.randomMotionQueries100, EvaluationType.MOTION,
                        new ExtraInfoForCsv("results/quantization/motion/", "buckets,", i + ","));
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }

    public static void cubeFrom3dCoordsMotion(){
        try {
            System.out.println(currentTime() + " 3d cube motion - loading reference");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            MovementDataVectorsLoader motionLoader = new MovementDataVectorsLoader(
                    "data_files/objects-annotations-specific-coords_normPOS.data", ";");
            MovementDataMinhashCreator creator = new MovementDataMinhashCreator(motionLoader, 4096, 10,
                    1, JointSelection.ALL_IN_ONE);
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 6; j++) {
                    creator.setCubeSize(i * 10);
                    ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                            "results/cube/" + i * 10 + "_" + j + "/", ExperimentsUtils.randomMotionQueries100, EvaluationType.MOTION_IGNORE_PNG,
                            new ExtraInfoForCsv("results/cube/", "oneDimensionalCuts,timeCubes,", i*10 + "," + j + ","));
                }
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }
}

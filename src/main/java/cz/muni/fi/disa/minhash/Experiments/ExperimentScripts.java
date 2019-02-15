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
//        ReferenceImgResultsCache.inflate();
//        binaryMappingImg();
//        pairingOfValuesImg();
//        quantizationImg();
//        binaryMappingMotion();
//        pairingOfValuesMotion();
//        quantizationMotion();
        cubeFrom3dCoordsMotion3(JointSelection.ALL_IN_ONE);
        cubeFrom3dCoordsMotion2(JointSelection.ALL_IN_ONE);
        cubeFrom3dCoordsMotion(JointSelection.ALL_IN_ONE);
        cubeFrom3dCoordsMotion(JointSelection.LEFT_RIGHT_MID);
        cubeFrom3dCoordsMotion(JointSelection.TORSO_AND_LIMBS);
    }

    public static void binaryMappingImg(){
        try {
            System.out.println(currentTime() + " binary mapping img");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            AbstractMinhashCreator creator = new BinaryMappingMinhashCreator(loader, 4096);
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, null,
                    "results/binary/img/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION, null, true, true);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void pairingOfValuesImg(){
        try {
            System.out.println(currentTime() + " pairing img");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            AbstractMinhashCreator creator = new PairingMinhashCreator(loader, 4096, true);
            System.out.println("and");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, null,
                    "results/pairing/and/img/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION, null, false, true);
            creator = new PairingMinhashCreator(loader, 4096, false);
            System.out.println("or");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, null,
                    "results/pairing/or/img/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION, null, false, true);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void quantizationImg(){
        try {
            System.out.println(currentTime() + " quantization img");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/features-images-profiset100K.data", " ", 4096);
            for (int i = 2; i < 9; i++) {
                AbstractMinhashCreator creator = new QuantizationMinhashCreator(loader, 4096, i);
                ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, null,
                        "results/quantization/img/" + i + "/", ExperimentsUtils.randomImageQueries100, EvaluationType.NO_MOTION,
                        new ExtraInfoForCsv("results/quantization/img/", "buckets,", i + ","), true, true);
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }

    public static void binaryMappingMotion(){
        try {
            System.out.println(currentTime() + " binary mapping motion");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            AbstractMinhashCreator creator = new BinaryMappingMinhashCreator(loader, 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096));
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/binary/motion/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION, null, true, true);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void pairingOfValuesMotion(){
        try {
            System.out.println(currentTime() + " pairing motion");
            BooleanVectorLoader loader = new BooleanVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            AbstractMinhashCreator creator = new PairingMinhashCreator(loader, 4096, true);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(
                    new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096));
            System.out.println("and");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/pairing/and/motion/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION, null, false, true);
            creator = new PairingMinhashCreator(loader, 4096, false);
            System.out.println("or");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/pairing/or/motion/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION, null, false, true);
        }catch (VectorLoaderException e){
            e.printStackTrace();
        }
    }

    public static void quantizationMotion(){
        try {
            System.out.println(currentTime() + " quantization motion");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            QuantizationMinhashCreator creator = new QuantizationMinhashCreator(loader, 4096, 2);
            for (int i = 2; i < 9; i++) {
                creator.setNumberOfBuckets(i);
                ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                        "results/quantization/motion/" + i + "/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION,
                        new ExtraInfoForCsv("results/quantization/motion/", "buckets,", i + ","), true, true);
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }

    public static void cubeFrom3dCoordsMotion(JointSelection jointSelection){
        try {
            System.out.println(currentTime() + " 3d cube motion");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            MovementDataVectorsLoader motionLoader = new MovementDataVectorsLoader(
                    "data_files/objects-annotations-specific-coords_normPOS.data", ";");
            MovementDataMinhashCreator creator = new MovementDataMinhashCreator(motionLoader, 4096, 10,
                    1, jointSelection);
            for (int i = 2; i < 5; i++) {
                for (int j = 4; j < 8; j++) {
                    int cubeSize = i*5;
                    creator.setCubeSize(cubeSize);
                    creator.setTimeCubes(j);
                    ExtraInfoForCsv csv = new ExtraInfoForCsv("results/cube/" + jointSelection + "/", "oneDimensionalCuts,timeCubes,", cubeSize + "," + j + ",");
                    ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                            "results/cube/" + jointSelection + "/" + cubeSize + "_" + j + "/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION_IGNORE_PNG,
                            csv, true, false);
                }
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }

    public static void cubeFrom3dCoordsMotion2(JointSelection jointSelection){
        try {
            System.out.println(currentTime() + " 3d cube motion");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            MovementDataVectorsLoader motionLoader = new MovementDataVectorsLoader(
                    "data_files/objects-annotations-specific-coords_normPOS.data", ";");
            MovementDataMinhashCreator creator = new MovementDataMinhashCreator(motionLoader, 4096, 10,
                    1, jointSelection);
            for (int i = 2; i < 5; i++) {
                for (int j = 6; j < 11; j++) {
                    int cubeSize = i*5;
                    creator.setCubeSize(cubeSize);
                    creator.setTimeCubes(j);
                    ExtraInfoForCsv csv = new ExtraInfoForCsv("results/cube/" + jointSelection + "/", "oneDimensionalCuts,timeCubes,", cubeSize + "," + j + ",");
                    ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                            "results/cube/" + jointSelection + "/" + cubeSize + "_" + j + "/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION_IGNORE_PNG,
                            csv, true, false);
                }
            }
        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }

    public static void cubeFrom3dCoordsMotion3(JointSelection jointSelection){
        try {
            System.out.println(currentTime() + " 3d cube motion");
            FloatVectorLoader loader = new FloatVectorLoader("data_files/original-2folds_1-merged.data", ",", 4096);
            ReferenceQueryExecutor referenceQueryExecutor = new ReferenceQueryExecutor(loader);
            MovementDataVectorsLoader motionLoader = new MovementDataVectorsLoader(
                    "data_files/objects-annotations-specific-coords_normPOS.data", ";");
            MovementDataMinhashCreator creator = new MovementDataMinhashCreator(motionLoader, 4096, 10,
                    1, jointSelection);
            int j = 5;
            int cubeSize = 35;
            creator.setCubeSize(cubeSize);
            creator.setTimeCubes(j);
            ExtraInfoForCsv csv = new ExtraInfoForCsv("results/cube/" + jointSelection + "/", "oneDimensionalCuts,timeCubes,", cubeSize + "," + j + ",");
            ExperimentsUtils.checkMinhashLengthsAndQuerySizes(creator, referenceQueryExecutor,
                    "results/cube/" + jointSelection + "/" + cubeSize + "_" + j + "/", ExperimentsUtils.getAllMotionIds(), EvaluationType.MOTION_IGNORE_PNG,
                    csv, true, false);

        }catch (VectorLoaderException e) {
            e.printStackTrace();
        }
    }
}

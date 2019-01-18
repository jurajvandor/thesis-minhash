package cz.muni.fi.disa.minhash.DataHolders;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PermutationGenerator {

    private int sizeOfVector;
    private int numberOfVectors;

    public PermutationGenerator(int sizeOfVector, int numberOfVectors){
        this.sizeOfVector = sizeOfVector;
        this.numberOfVectors = numberOfVectors;
    }

    //TODO delete test
    public static void main(String[] args) throws Exception{
        PermutationGenerator g = new PermutationGenerator(20, 20);
        try {
            g.loadPermutations();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getSizeOfVector() {
        return sizeOfVector;
    }

    public void setSizeOfVector(int sizeOfVector){
        this.sizeOfVector = sizeOfVector;
    }

    public int getNumberOfVectors() {
        return numberOfVectors;
    }

    public void setNumberOfVectors(int numberOfVectors){
        this.numberOfVectors = numberOfVectors;
    }

    /**
     * loads permutations from file, if file doesn't exist, new permutations are created and written to file
     * @return loaded permutations
     */
    public int[][] loadPermutations() throws PermutationException{
        File file = new File(getPathUri());
        if (!file.exists()) {
            return createPermutations();
        }
        try{
            InputStream in = Files.newInputStream(Paths.get(getPathUri()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            int[][] matrix = new int[numberOfVectors][];
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()){
                    matrix[i] = parseLine(line);
                    i++;
                }
            }
            if (i != numberOfVectors){
                throw new PermutationException("data for permutation with" + numberOfVectors + " vectors of size "
                        + sizeOfVector + " contains different number of permutations");
            }
            reader.close();
            return matrix;
        } catch (IOException e){
            throw new PermutationException("data for permutation with" + numberOfVectors + " vectors of size "
                    + sizeOfVector + " could not be loaded (is another program using this file?", e);
        }
    }

    /**
     * CAREFUL OVERWRITES EXISTING PERMUTATIONS
     * @return new permutations also written to file
     */
    public int[][] createPermutations() throws PermutationException{
        int cutSize = sizeOfVector > 10000 ? 10000 : sizeOfVector;
        int [][] matrix = new int[numberOfVectors][cutSize];
        for (int i = 0; i < numberOfVectors; i++){
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < sizeOfVector; j++){
                list.add(j);
            }
            Collections.shuffle(list);
            // since java cant convert to primitive array and we need that shuffle we have to use List
            // time complexity of this code doesn't matter anyway so we just convert to int array manually
            for (int j = 0; j < cutSize; j++)
                matrix[i][j] = list.get(j);
        }
        try {
            OutputStream out = Files.newOutputStream(Paths.get(getPathUri()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            for (int i = 0; i < numberOfVectors; i++){
                StringBuilder s = new StringBuilder();
                for (int j = 0; j < cutSize; j++){
                    if (j != 0)
                        s.append(" ");
                    s.append(matrix[i][j]);
                }
                writer.write(s.toString());
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            throw new PermutationException("file " + getPathUri() + " could not be created or overwritten", e);
        }
        return matrix;
    }

    private int[] parseLine(String line) throws PermutationException{
        int cutSize = sizeOfVector > 10000 ? 10000 : sizeOfVector;
        int[] vector = new int[cutSize];
        String[] split = line.split(" ");
        int i = 0;
        for (String value : split) {
            vector[i] = Integer.parseInt(value);
            i++;
        }
        if (i != cutSize){
            throw new PermutationException("data for permutation with" + numberOfVectors + " vectors of size "
                    + sizeOfVector + " contains permutation vector of different size");
        }
        return vector;
    }

    public String getPathUri(){
        return "data_files/permutations_" + sizeOfVector + "_" + numberOfVectors + ".perm";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermutationGenerator that = (PermutationGenerator) o;
        return sizeOfVector == that.sizeOfVector &&
                numberOfVectors == that.numberOfVectors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sizeOfVector, numberOfVectors);
    }
}

package cz.muni.fi.disa.minhash.DataHelpers;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * loads permutations from file, if file doesn't exist, new permutations are created and written to file
     * @return loaded permutations
     */
    public List<List<Integer>> loadPermutations() throws PermutationException{
        File file = new File(getPathUri());
        if (!file.exists()) {
            return createPermutations();
        }
        try{
            InputStream in = Files.newInputStream(Paths.get(getPathUri()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            List<List<Integer>> listOfVectors = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty())
                    listOfVectors.add(parseLine(line));
            }
            if (listOfVectors.size() != numberOfVectors){
                throw new PermutationException("data for permutation with" + numberOfVectors + " vectors of size "
                        + sizeOfVector + " contains different number of permutations");
            }
            reader.close();
            return listOfVectors;
        } catch (IOException e){
            throw new PermutationException("data for permutation with" + numberOfVectors + " vectors of size "
                    + sizeOfVector + " could not be loaded (is another program using this file?", e);
        }
    }

    /**
     * CAREFUL OVERWRITES EXISTING PERMUTATIONS
     * @return new permutations also written to file
     */
    public List<List<Integer>> createPermutations() throws PermutationException{
        List<List<Integer>> listOfVectors = new ArrayList<>();
        for (int i = 0; i < numberOfVectors; i++){
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < sizeOfVector; j++){
                list.add(j);
            }
            Collections.shuffle(list);
            listOfVectors.add(list);
        }
        try {
            OutputStream out = Files.newOutputStream(Paths.get(getPathUri()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            for (int i = 0; i < numberOfVectors; i++){
                StringBuilder s = new StringBuilder();
                for (int j = 0; j < sizeOfVector; j++){
                    if (j != 0)
                        s.append(" ");
                    s.append(listOfVectors.get(i).get(j));
                }
                writer.write(s.toString());
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            throw new PermutationException("file " + getPathUri() + " could not be created or overwritten", e);
        }
        return listOfVectors;
    }

    private List<Integer> parseLine(String line) throws PermutationException{
        List<Integer> list = new ArrayList<>();
        String[] split = line.split(" ");
        for (String value : split) {
            list.add(new Integer(value));
        }
        if (list.size() != sizeOfVector){
            throw new PermutationException("data for permutation with" + numberOfVectors + " vectors of size "
                    + sizeOfVector + " contains permutation vector of different size");
        }
        return list;
    }

    private String getPathUri(){
        return "./permutations_" + sizeOfVector + "_" + numberOfVectors + ".perm";
    }

}

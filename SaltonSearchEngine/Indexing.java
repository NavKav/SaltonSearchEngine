import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;


public class Indexing {
    /************************************************************************************************************/
    /*********************************      PUBLIC       ********************************************************/
    /************************************************************************************************************/

    /////////////////
    /* Constructors */
    /////////////////

    public Indexing() {
    }

    public Indexing(String ... filePath) {
        for (String name : filePath) {
            this.addDocument(name);
        }
    }

    /////////////////
    /*   Methods   */
    /////////////////

    public void addDocument(String filePath) {
        documentArray.add(filePath);
    }

    public ArrayList<HashMap<String, Double>> getIndex() throws IOException {
        return CreateIndex(documentArray.toArray(new String[documentArray.size()]));
    }

    /************************************************************************************************************/
    /*********************************      PRIVATE      ********************************************************/
    /************************************************************************************************************/

    /////////////////
    /*  Variables  */
    /////////////////

    private ArrayList<String> documentArray = new ArrayList<String>();
    private Set<Character> h = new HashSet<Character>();

    /////////////////
    /*   Methods   */
    /////////////////

    private ArrayList<HashMap<String, Double>> CreateIndex(String[] args) throws IOException {
        ArrayList<HashMap<String, Integer>> HashMapList = new ArrayList<HashMap<String, Integer>>();

        /* Mapping des mots/nombre d'apparition */
        for (String fileName : args) {
            HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
            String s;
            String text = new String(Files.readAllBytes(Paths.get(fileName)));
            StringTokenizer tokenizer = new StringTokenizer(text, "://.-*\n '");
            while (tokenizer.hasMoreTokens()) {
                s = tokenizer.nextToken();
                addHashMapEntry(wordsMap, s);
            }
            HashMapList.add(wordsMap);
        }

        // Total words count
        double totalWords[] = new double[HashMapList.size()];
        for (int i = 0; i < totalWords.length; i++) {
            for (Map.Entry<String, Integer> pair : HashMapList.get(i).entrySet()) {
                totalWords[i] += pair.getValue();
            }
        }

        ArrayList<HashMap<String, Double>> index = InitializeIndex(HashMapList, totalWords);
        return index;

    }

    private ArrayList<HashMap<String, Double>> InitializeIndex(ArrayList<HashMap<String, Integer>> HashMapList, double[] totalWords) {
        ArrayList<HashMap<String, Double>> index = new ArrayList<HashMap<String, Double>>();
        int nbDocuments = HashMapList.size();
        for (int i = 0; i < nbDocuments; i++) {
            HashMap<String, Double> documentHashMap = new HashMap<String, Double>();
            for (Map.Entry<String, Integer> values : HashMapList.get(i).entrySet()) {
                double logResult = appearsText(HashMapList, values.getKey());
                logResult = logResult == 0 ? Double.valueOf(nbDocuments) : logResult;
                double result = (values.getValue().doubleValue() / totalWords[i]) * Math.log(Double.valueOf(nbDocuments) / logResult);

                    documentHashMap.put(values.getKey(), result);
            }
            index.add(documentHashMap);
        }
        return index;
    }

    private void addHashMapEntry(HashMap<String, Integer> hashMap, String s) {
        if (hashMap.containsKey(s)) {
            hashMap.put(s, hashMap.get(s) + 1);
        } else {
            hashMap.put(s, 1);
        }
    }

    private double appearsText(ArrayList<HashMap<String, Integer>> arrayList, String s) {
        int n = arrayList.size();
        double res = 0.;
        for (int i = 0; i < n; i++) {
            if (arrayList.get(i).containsKey(s)) {res ++;}
        }
        return res;
    }
}

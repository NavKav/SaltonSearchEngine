import java.util.*;
import java.io.*;


public class Indexing {

    public void f(String[] args) throws IOException {
        FileReader fileReader;
        ArrayList<HashMap<String, Integer>> HashMapList = new ArrayList<HashMap<String, Integer>>();

        /* Mapping des mots/nombre d'apparition */
        for (String fileName : args) {
            fileReader = new FileReader(fileName);
            HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
            String s;
            do {
                s = extractOneWord(fileReader);
                addHashMapEntry(wordsMap, s);
            } while (s.length() != 0);
            HashMapList.add(wordsMap);
        }

        /* Calcul  du TF IDF*/
        // Total words count
        double totalWords[] = new double[HashMapList.size()];
        for (int i = 0; i < totalWords.length; i++) {
            for (Map.Entry<String, Integer> pair : HashMapList.get(i).entrySet()) {
                totalWords[i] += pair.getValue();
            }
        }

        ArrayList<HashMap<String, Double>> index = InitializeIndex(HashMapList, totalWords);
        System.out.print(index.size());
        debug(index);
        // Total frequency count

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
    private String extractOneWord(FileReader fileReader) throws IOException {
        String res = new String("");
        int c = fileReader.read();
        if (c != 1) {
            while (!(c != ' ' && c != '\n' && c != ',' && c!= '.')) {c = fileReader.read();}

            while (c != -1 && c != ' ' && c != '\n' && c != ',' && c!= '.') {
                res += (char)c;
                c = fileReader.read();
            }
        }
        return res;
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
        //System.out.println("                                     " + s + res);
        return res;
    }

    private void debug(ArrayList<HashMap<String, Double>> m) {

        for (int i = 0; i < m.size(); i++) {
            HashMap<String, Double> h = m.get(i);
            for (Map.Entry<String, Double> values : h.entrySet()) {
                System.out.print("\n" + values.getKey() + " " + values.getValue());
            }
        }
    }
}

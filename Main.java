import javafx.util.Pair;
import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class Main {
    public static void main(String[] args) throws IOException {
        f(args);
    }

    public static void f(String[] args) throws IOException {
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

        HashMap<String, double[]> TFIDFHashMap = getWordsPerText(HashMapList, totalWords);
        debug(TFIDFHashMap);
        // Total frequency count

    }

    public static String extractOneWord(FileReader fileReader) throws IOException {
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

    public static void addHashMapEntry(HashMap<String, Integer> hashMap, String s) {
        if (hashMap.containsKey(s)) {
            hashMap.put(s, hashMap.get(s) + 1);
        } else {
            hashMap.put(s, 1);
        }
    }

    /* Pointless */
    public static HashMap<String, double[]> getWordsPerText(ArrayList<HashMap<String, Integer>> arrayList, double totalWords[]) {
        HashMap<String, double[]> res = new HashMap<String, double[]>();
        int nbText = arrayList.size();
        for (int i = 0; i < nbText; i++) {
            for (Map.Entry<String, Integer> values : arrayList.get(i).entrySet()) {
                double logResult = appearsText(arrayList, values.getKey());
                logResult = logResult == 0 ? Double.valueOf(nbText) : logResult;
                double result = (values.getValue().doubleValue() / totalWords[i]) * Math.log(Double.valueOf(nbText) / logResult);
                if (res.containsKey(values.getKey())) {
                    res.get(values.getKey())[i] = result;
                } else {
                    double aux[] = new double[nbText];
                    Arrays.fill(aux, 0);
                    aux[i] = result;
                    res.put(values.getKey(), aux);
                }
            }
        }
        return res;
    }

    public static double appearsText(ArrayList<HashMap<String, Integer>> arrayList, String s) {
        int n = arrayList.size();
        double res = 0.;
        for (int i = 0; i < n; i++) {
            if (arrayList.get(i).containsKey(s)) {res ++;}
        }
        //System.out.println("                                     " + s + res);
        return res;
    }

    public static void debug(HashMap<String, double[]> h) {

        for (Map.Entry<String, double[]> values : h.entrySet()) {
            System.out.print("\n" + values.getKey() + " ");
            for (double x : values.getValue()) {
                System.out.print(x + " ");
            }
        }
    }

    // end of Main class
}
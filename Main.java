import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unchecked")
public class Main {
    public static void main(String[] args) throws IOException {
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
            for (Map.Entry<String, Integer> pair : HashMapList.get(0).entrySet()) {
                totalWords[i] += pair.getValue();
            }
        }

        // Total frequency count
        

        System.out.println(HashMapList.get(0).size() == HashMapList.get(0).size());
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
    // end of Main class


}
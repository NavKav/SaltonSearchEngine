import javafx.util.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Research {

    /************************************************************************************************************/
    /*********************************      PUBLIC       ********************************************************/
    /************************************************************************************************************/

    //////////////////
    /* Constructors */
    //////////////////

    public Research(String fileName, ArrayList<HashMap<String, Double>> index) throws IOException {
        FileReader fileReader;
        fileReader = new FileReader(fileName);
        HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
        request = new HashMap<String, Integer>();
        this.documents = new ArrayList<HashMap<String, Double>>(index);

        for (HashMap<String, Double> stringDoubleHashMap : index) {
            for (Map.Entry mapentry : stringDoubleHashMap.entrySet()) {
                request.put(mapentry.getKey().toString(), 0);
            }
        }

        String s;
        String text = new String(Files.readAllBytes(Paths.get(fileName)));
        StringTokenizer tokenizer = new StringTokenizer(text, "://.-*\n '");
        while (tokenizer.hasMoreTokens()) {
            s = tokenizer.nextToken();
            if (request.containsKey(s)) {
                request.put(s, 1);
            }
        }
    }

    /////////////////
    /*   Methods   */
    /////////////////

    public ArrayList<Pair<Integer, Double>> documentsPertinents(int k) {
        ArrayList<Pair<Integer, Double>> res = new ArrayList<Pair<Integer, Double>>();
        for (int i = 0; i < this.documents.size(); i++) {
            if (res.size() == k) {
                if (coefficientSalton(i) > res.get(k-1).getValue()) {
                    res.set(k-1, new Pair<Integer, Double>(i, coefficientSalton(i)));
                    res.sort(Comparator.comparing(p -> -p.getValue()));
                }
            }
            else {
                res.add(new Pair<Integer, Double>(i, coefficientSalton(i)));
                res.sort(Comparator.comparing(p -> -p.getValue()));
            }
        }
        return res;
    }


    /************************************************************************************************************/
    /*********************************      PRIVATE      ********************************************************/
    /************************************************************************************************************/

    /////////////////
    /*  Variables  */
    /////////////////

    private HashMap<String, Integer> request;
    private ArrayList<HashMap<String, Double>> documents;

    /////////////////
    /*   Methods   */
    /////////////////

    private double coefficientSalton(int document) {
        double numerator = 0.0;
        double denominatorDocument = 0.0;
        double denominatorRequest = 0.0;
        for (Map.Entry mapentry : this.documents.get(document).entrySet()) {
            numerator += this.request.get(mapentry.getKey()) * this.documents.get(document).get(mapentry.getKey());
            denominatorDocument += Math.pow(this.documents.get(document).get(mapentry.getKey()), 2);
            denominatorRequest += Math.pow(this.request.get(mapentry.getKey()), 2);
        }
        return numerator/Math.sqrt(denominatorDocument * denominatorRequest);
    }

    private String extractOneWord(FileReader fileReader) throws IOException {
        String res = "";
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

}

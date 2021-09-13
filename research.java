import java.util.Vector;
import java.util.*;

public class research {
    Vector<Integer> request = new Vector<Integer>();
    HashMap<String, Vector<Double>> documentsVectors = new HashMap<String, Vector<Double>>();

    /* Création Du Vecteur Document */
    public void vectorsDocuments(HashMap<String, HashMap<String, Float>> documentsMap) {
        Vector document = new Vector();
        for (Map.Entry documentsMapEntry : documentsMap.get(0).entrySet()) {
            for (Map.Entry mapEntry : documentsMap.get(documentsMapEntry.getValue()).entrySet()) {
                document.add(mapEntry.getValue());
            }
            this.documentsVectors.put(documentsMapEntry.getKey().toString(), document);
        }
    }

    /* Calcul Coefficient Salton */
    public double CoefficientSalton(String d) {
        double numerator = 0.0;
        double denominatorDocument = 0.0;
        double denominatorRequest = 0.0;
        for (int i=0 ;i < this.documentsVectors.get(d).size(); i++) {
            numerator += this.request.get(i) * this.documentsVectors.get(d).get(i);
            denominatorDocument += Math.pow(this.documentsVectors.get(d).get(i), 2);
            denominatorRequest += Math.pow(this.request.get(i), 2);
        }
        return numerator/Math.sqrt(denominatorDocument * denominatorRequest);
    }

    /* K Documents les plus pertinents */
    public ArrayList<String> DocumentsPertinents(int k) {
        ArrayList<HashMap<String, Integer>> documents = new ArrayList<HashMap<String, Integer>>();
        for (Map.Entry mapEntry : this.documentsVectors.entrySet()) {
            if (documents.size() == k) {
                if (mapEntry.getValue() > documents.get(0).get(0).getValue()) {
                    documents.set(k-1, new HashMap<String, Integer>(mapEntry.getKey(), mapEntry.getValue()));
                    Collections.sort(documents, Comparator.comparing(p -> p.getValue()));
                }
            }
            else {
                documents.add(new HashMap<String, Integer>(mapEntry.getKey(), mapEntry.getValue()));
                Collections.sort(documents, Comparator.comparing(p -> p.getValue()));
            }
        }
        return DocumentsPertinents;
    }
}

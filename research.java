import java.util.Vector;

public class research {
    Vector request = new Vector();
    HashMap<String, Vector> documentsVectors = new HashMap<String, Vector>();

    /* Création Du Vecteur Document */
    public void vectorsDocuments(HashMap documentsMap) {
        Vector document = new Vector();
        for (Map.Entry documentsMapEntry : documentsMap.entrySet()) {
            for (Map.Entry mapEntry : documentsMap.get(documentsMapEntry.getValue()).entrySet()) {
                document.add(mapEntry.getValue());
            }
            this.documentsVectors.put(documentsMapEntry.getKey(), document);
        }
    }

    /* Calcul Coefficient Salton */
    public Float CoefficientSalton(String d) {
        Float numerator = 0;
        Float denominatorDocument = 0;
        Float denominatorRequest = 0;
        for (int i=0 ;i<len(this.documentsVectors.get(d)); i++) {
            numerator += this.request.get(i) * this.documentsVectors.get(d).get(i);
            denominatorDocument += Math.pow(this.documentsVectors.get(d).get(i), 2);
            denominatorRequest += Math.pow(this.request.get(i), 2);
        }
        return numerator/Math.sqrt(numerator, denominatorDocument * denominatorRequest);
    }

    /* K Documents les plus pertinents */
    public ArrayList<String> DocumentsPertinents(int k) {
        ArrayList<String> DocumentsPertinents = new ArrayList<String>;
        for (Map.Entry mapEntry : this.documentsVectors.entrySet()) {
            if (len(DocumentsPertinents) == 5) {
                if (mapEntry.getValue() > DocumentsPertinents) {
                    DocumentsPertinents.set(4, mapEntry.getValue());
                    Collection.sort(DocumentsPertinents);
                }
            }
            else {
                DocumentsPertinents.add(mapEntry.getValue());
                Collection.sort(DocumentsPertinents);
            }
        }
        return DocumentsPertinents;
    }
}

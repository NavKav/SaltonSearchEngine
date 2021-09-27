import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Indexing x = new Indexing("test.txt", "test2.txt", "test3.txt");
        ArrayList<HashMap<String, Double>> index =  x.getIndex();

        Research research = new Research("request.txt", index);

        ArrayList<Pair<Integer, Double>> a = research.documentsPertinents(2);
        System.out.print(a);
    }
    // end of Main class
}
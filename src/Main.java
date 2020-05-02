import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\MLS-full-cell-export-2020-05-01T000000.csv";

        MozCsvFile mozFile = new MozCsvFile(path);
        System.out.println("Loaded instance");

        Map<Short, Map<Integer, MozEnb>> cont = mozFile.getFileContents();
        System.out.println("Got arraylist");

        //System.out.println(cont.size());
    }

}

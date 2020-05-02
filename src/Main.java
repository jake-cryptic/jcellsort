import java.util.TreeMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\MLS-full-cell-export-2020-05-01T000000.csv";

        MozCsvFile mozFile = new MozCsvFile(path);
        System.out.println("Loaded instance");

        Map<Short, Map<Integer, MozEnb>> cont = mozFile.getFileContents();
        System.out.println("Copied data to local variable");

        Map<Short, Map<Integer, MozEnb>> sorted = new TreeMap<Short, Map<Integer, MozEnb>>(cont);
        System.out.println("Converted to TreeMap object");

        for (Map.Entry<Short, Map<Integer, MozEnb>> mnc : sorted.entrySet()) {
            Map<Integer, MozEnb> enbList = mnc.getValue();
            System.out.println(mnc.getKey() + " has " + enbList.size() + " eNBs");
        }


    }

}

import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\MLS-full-cell-export-2020-05-03T000000.csv";

        MozCsvFile mozFile = new MozCsvFile(path);
        System.out.println("Loaded instance");

        Map<Short, Map<Integer, MozEnb>> cont = mozFile.getFileContents();
        System.out.println("Copied data to local variable");

        Map<Short, Map<Integer, MozEnb>> sorted = new TreeMap<Short, Map<Integer, MozEnb>>(cont);
        System.out.println("Converted to TreeMap object");

        CellDatabase db = new CellDatabase();
        db.prepareStatement("INSERT INTO sectors (id, mnc, enodeb_id, sector_id, pci, lat, lng, samples, created, updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        for (Map.Entry<Short, Map<Integer, MozEnb>> mnc : sorted.entrySet()) {
            Map<Integer, MozEnb> enbList = mnc.getValue();
            System.out.println(mnc.getKey() + " has " + enbList.size() + " eNBs");

            for (Map.Entry<Integer, MozEnb> enb : enbList.entrySet()) {
                System.out.println(enb.getValue());
            }
        }
    }

}

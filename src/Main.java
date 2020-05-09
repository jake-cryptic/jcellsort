import java.util.TreeMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String mlsFile = "D:\\MLS-full-cell-export-2020-05-08T000000.csv";
        String ociFile = "D:\\cell_towers_2020-05-08-T000000.csv";

        MozCsvFile mozFile = new MozCsvFile();
        mozFile.parseCsvFile(mlsFile);
        //mozFile.parseCsvFile(ociFile);
        System.out.println("Loaded instance");

        Map<Short, Map<Integer, MozEnb>> cont = mozFile.getFileContents();
        System.out.println("Copied data to local variable");

        Map<Short, Map<Integer, MozEnb>> sorted = new TreeMap<Short, Map<Integer, MozEnb>>(cont);
        System.out.println("Converted to TreeMap object");

        CellDatabase cells = new CellDatabase();
        CellDatabase sites = new CellDatabase();
		cells.prepareStatement("INSERT INTO sectors (mnc, enodeb_id, sector_id, pci, lat, lng, samples, created, updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sites.prepareStatement("INSERT INTO masts (mnc, enodeb_id, lat, lng, updated) VALUES (?, ?, ?, ?, ?)");

        for (Map.Entry<Short, Map<Integer, MozEnb>> mnc : sorted.entrySet()) {
            Map<Integer, MozEnb> enbList = mnc.getValue();
            System.out.println(mnc.getKey() + " has " + enbList.size() + " eNBs");

            for (Map.Entry<Integer, MozEnb> enb : enbList.entrySet()) {
                MozEnb thisEnb = enb.getValue();

                for (Map.Entry<Short, MozCsvCell> sector : thisEnb.sectors.entrySet()) {
					cells.insertSectors(sector.getValue());
				}

				sites.insertEnb(thisEnb, mnc.getKey());
            }
        }

		cells.finish();
		sites.finish();
    }

}

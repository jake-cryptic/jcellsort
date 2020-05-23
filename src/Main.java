import java.util.TreeMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
    	String[] files = new String[]{
			"D:\\MLS-full-cell-export-2020-05-23T000000.csv",
    		"C:\\WebServer73\\htdocs\\mls_mapper\\new\\data\\234-2019-04-20.csv",
    		"C:\\WebServer73\\htdocs\\mls_mapper\\new\\data\\234-2019-05-15.csv",
    		"C:\\WebServer73\\htdocs\\mls_mapper\\new\\data\\234-2019-06-18.csv",
    		"C:\\WebServer73\\htdocs\\mls_mapper\\new\\data\\234-2019-10-12.csv",
    		"C:\\WebServer73\\htdocs\\mls_mapper\\new\\data\\234-2020-01-02.csv",
			"D:\\cell_towers_2020-05-21-T000000.csv",
			"D:\\cell_towers_2020-05-13-T000000.csv"
		};

        MozCsvFile mozFile = new MozCsvFile();
        for (String file : files) {
			mozFile.parseCsvFile(file);
		}
        System.out.println("Loaded instance");

        Map<Short, Map<Integer, MozEnb>> cont = mozFile.getFileContents();
        System.out.println("Copied data to local variable");

        Map<Short, Map<Integer, MozEnb>> sorted = new TreeMap<Short, Map<Integer, MozEnb>>(cont);
        System.out.println("Converted to TreeMap object");

        CellDatabase cells = new CellDatabase();
        CellDatabase sites = new CellDatabase();
		cells.prepareStatement("INSERT INTO sectors (mcc, mnc, enodeb_id, sector_id, pci, lat, lng, samples, created, updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sites.prepareStatement("INSERT INTO masts (mcc, mnc, enodeb_id, lat, lng, updated) VALUES (?, ?, ?, ?, ?, ?)");

        for (Map.Entry<Short, Map<Integer, MozEnb>> mnc : sorted.entrySet()) {
            Map<Integer, MozEnb> enbList = mnc.getValue();
            System.out.println(mnc.getKey() + " has " + enbList.size() + " eNBs");

            for (Map.Entry<Integer, MozEnb> enb : enbList.entrySet()) {
                MozEnb thisEnb = enb.getValue();

                for (Map.Entry<Short, MozCsvCell> sector : thisEnb.sectors.entrySet()) {
					cells.insertSectors(sector.getValue());
				}

				sites.insertEnb(thisEnb, (short)234, mnc.getKey());
            }
        }

        System.out.println("Saving database...");
		cells.finish();
		sites.finish();
    }

}

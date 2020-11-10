import java.util.TreeMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
    	String[] files = new String[]{
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2019-04-20.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2019-05-15.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2019-06-18.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2019-10-12.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2019-11-29.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2020-01-02.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2020-02-17.csv",
			"E:\\Program Files\\WebServer74\\htdocs\\mls_mapper\\new\\data\\234-2020-04-19.csv",
			"E:\\MLS-full-cell-export-2020-07-02T000000.csv",
			"E:\\MLS-full-cell-export-2020-08-01T000000.csv",
			"E:\\MLS-full-cell-export-2020-09-03T000000.csv",
			"E:\\MLS-full-cell-export-2020-10-04T000000.csv",
			"E:\\MLS-full-cell-export-2020-11-10T000000.csv",
			"E:\\cell_towers_2020-07-04-T000000.csv",
			"E:\\cell_towers_2020-08-01-T000000.csv",
			"E:\\cell_towers_2020-09-04-T000000.csv",
			"E:\\cell_towers_2020-09-21-T000000.csv",
			"E:\\234-2020-09-28.csv",
			"E:\\234-2020-10-20.csv",
			"E:\\234-2020-11-10.csv",
			"E:\\cell_towers_2020-10-09-T000000.csv"
		};

    	// TODO: Check data files exist

        MozCsvFile mozFile = new MozCsvFile();
        for (String file : files) {
			mozFile.parseCsvFile(file);
			System.out.println("Parsed file: " + file);
		}
        System.out.println("All files loaded");

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

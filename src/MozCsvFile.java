import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MozCsvFile {

	public Map<Short, Map<Integer, MozEnb>> mncList;
	public ArrayList<MozCsvCell> cellList;
	public CellFilter cf = new CellFilter();

	public MozCsvFile(String filepath) {
		mncList = new HashMap<Short, Map<Integer, MozEnb>>();
		cellList = new ArrayList<MozCsvCell>();
		String line;
		String[] data;

		try {
			FileReader fr = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				data = line.split(",");

				if (!data[0].contentEquals("LTE") || !data[1].contains("234")) continue;
				if (data[4].isEmpty() || data[5].isEmpty() || data[6].isEmpty() || data[7].isEmpty()) continue;

				// Discard cellIds below 256 as they cause issues.
				int cellId = Integer.parseUnsignedInt(data[4]);
				if (cellId <= 256) continue;

				MozCsvCell tmpCell = new MozCsvCell(data);

				// Check that sector ID and eNB are valid for UK
				if (!tmpCell.isValid(cf)) continue;

				// Check if MNC exists (if not add it)
				short mnc = tmpCell.getMnc();
				if (!mncList.containsKey(mnc)) {
					System.out.println("Added new MNC to list " + mnc);
					mncList.put(mnc, new HashMap<Integer, MozEnb>());
				}

				Map<Integer, MozEnb> tmpEnbList = mncList.get(mnc);

				int enbId = tmpCell.getEnb();
				short sectorId = tmpCell.getSectorId();
				MozEnb enb;

				if (!tmpEnbList.containsKey(enbId)) {
					enb = new MozEnb(enbId);
				} else {
					enb = tmpEnbList.get(enbId);
				}

				enb.addSector(sectorId, tmpCell);
				tmpEnbList.put(enbId, enb);

				mncList.put(mnc, tmpEnbList);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public Map<Short, Map<Integer, MozEnb>> getFileContents() {
		return this.mncList;
	}

}

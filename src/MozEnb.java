import java.util.HashMap;
import java.util.Map;

public class MozEnb {

	public int eNb;
	public int lastUpdated;
	public Map<Short, MozCsvCell> sectors;

	public MozEnb(int eNb) {
		this.eNb = eNb;
		this.lastUpdated = 0;
		this.sectors = new HashMap<Short, MozCsvCell>();
	}

	public int getEnb() {
		return eNb;
	}

	public int getLastUpdated() {
		return lastUpdated;
	}

	public void addSector(short id, MozCsvCell row) {
		if (row.getUpdated() > this.lastUpdated) {
			this.lastUpdated = row.getUpdated();
		}
		this.sectors.put(id, row);
	}

	public float[] calculateLocation() {
		float[] output = new float[2];
		float latTotal = 0, lngTotal = 0;
		int divisor = 0;
		double samplesAdjust, cellRange, cellWeight;

		for (Map.Entry<Short, MozCsvCell> sector : this.sectors.entrySet()) {
			MozCsvCell thisCell = sector.getValue();

			// Account for large number of samples
			samplesAdjust = Math.ceil(Math.log(thisCell.getSamples())) + 1;

			// Account for cell with huge range
			//cellRange = (Math.log(thisCell.getRange() + 1) + 1);

			// Create a weight for each sector on a cell
			cellWeight = samplesAdjust; // * cellRange + 1;

			latTotal = latTotal + (thisCell.getLat() * (int) cellWeight);
			lngTotal = lngTotal + (thisCell.getLng() * (int) cellWeight);

			divisor = divisor + (int) cellWeight;
		}

		output[0] = latTotal / divisor;
		output[1] = lngTotal / divisor;

		return output;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<MozEnb::")
			.append(eNb)
			.append("->")
			.append(sectors.size())
			.append(">");
		return sb.toString();
	}
}

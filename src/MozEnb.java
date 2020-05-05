import java.util.HashMap;
import java.util.Map;

public class MozEnb {

	public int eNb;
	public int lastUpdated;
	public Map<Short, MozCsvCell> sectors;

	private float[] sectMean;
	private float[] sectStdDev;

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

	private void calculateSectorStats() {
		float[] latSet = new float[this.sectors.size()],
				lngSet = new float[this.sectors.size()];

		float latTotal = 0, lngTotal = 0;

		int i = 0;
		for (Map.Entry<Short, MozCsvCell> sector : this.sectors.entrySet()) {
			MozCsvCell thisCell = sector.getValue();

			latSet[i] = thisCell.getLat();
			lngSet[i] = thisCell.getLng();

			latTotal = latTotal + thisCell.getLat();
			lngTotal = lngTotal + thisCell.getLng();

			i++;
		}

		this.sectStdDev = new float[] {
			Misc.calculateStandardDeviation(latSet),
			Misc.calculateStandardDeviation(lngSet)
		};

		this.sectMean = new float[] {
			latTotal / i,
			lngTotal / i
		};
	}

	private float[] calculateDeviationsFromMean(float[] coords) {
		return new float[] {
			(this.sectMean[0] - coords[0]) / this.sectStdDev[0],
			(this.sectMean[1] - coords[1]) / this.sectStdDev[1]
		};
	}

	public float[] calculateLocation() {
		this.calculateSectorStats();

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

			// Account for bad sectors
			float[] deviations = this.calculateDeviationsFromMean(new float[] {thisCell.getLat(), thisCell.getLng()});
			float accountedDistance = 4;
			if (deviations[0] > 0.1 || deviations[1] > 0.1) {
				float totalDeviation = (deviations[0] + deviations[1]);
				accountedDistance = -1 * (float) Math.abs(0.2 * Math.pow(totalDeviation, 2)) + 3;
				if (accountedDistance < 0) {
					accountedDistance = 0;
				}
			}

			// Create a weight for each sector on a cell
			cellWeight = samplesAdjust + accountedDistance; // * cellRange + 1;

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

public class MozCsvCell {

	private String rat;
	private short mcc;
	private short mnc;
	private int cellId;
	private int eNb;
	private short sectorId;
	private int tac;
	private short pci;
	private float lat;
	private float lng;
	private int samples;
	private int range;
	private int created;
	private int updated;

	public MozCsvCell(String[] data) {
		this.rat = data[0];
		this.mcc = Short.parseShort(data[1]);
		this.mnc = Short.parseShort(data[2]);
		this.tac = Integer.parseUnsignedInt(data[3]);
		this.cellId = Integer.parseUnsignedInt(data[4]);
		this.pci = Short.parseShort(data[5]);
		this.lng = Float.parseFloat(data[6]);
		this.lat = Float.parseFloat(data[7]);
		this.range = Integer.parseUnsignedInt(data[8]);
		this.samples = Integer.parseUnsignedInt(data[9]);
		this.created = Integer.parseUnsignedInt(data[11]);
		this.updated = Integer.parseUnsignedInt(data[12]);

		int[] cellData = CellID.convertToEnb(this.cellId);
		this.eNb = cellData[0];
		this.sectorId = (short) cellData[1];
	}

	public boolean isValid() {
		return CellFilter.filter_uk(this.mcc, this.eNb, this.sectorId);
	}

	public String getRat() {
		return rat;
	}

	public short getMcc() {
		return mcc;
	}

	public short getMnc() {
		return this.mnc;
	}

	public int getEnb() {
		return this.eNb;
	}

	public short getSectorId() {
		return this.sectorId;
	}

	public short getPci() {
		return pci;
	}

	public float getLat() {
		return lat;
	}

	public float getLng() {
		return lng;
	}

	public int getSamples() {
		return samples;
	}

	public int getRange() {
		return range;
	}

	public int getCreated() {
		return created;
	}

	public int getUpdated() {
		return updated;
	}
}

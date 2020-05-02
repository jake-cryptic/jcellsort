public class MozCsvCell {

	private String rat;
	private short mcc;
	private short mnc;
	private int cellId;
	private int enodeb;
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
		//this.pci = Short.parseShort(data[5]);
		this.lng = Float.parseFloat(data[6]);
		this.lat = Float.parseFloat(data[7]);
		this.range = Integer.parseUnsignedInt(data[8]);
		this.samples = Integer.parseUnsignedInt(data[9]);
		this.created = Integer.parseUnsignedInt(data[11]);
		this.updated = Integer.parseUnsignedInt(data[12]);

		int[] cellData = CellID.convertToEnb(this.cellId);
		this.enodeb = cellData[0];
		this.sectorId = (short) cellData[1];
	}

	public short getMnc() {
		return this.mnc;
	}

	public int getEnb() {
		return this.enodeb;
	}

	public short getSectorId() {
		return this.sectorId;
	}

}

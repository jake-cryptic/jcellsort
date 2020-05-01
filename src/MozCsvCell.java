public class MozCsvCell {

	private String rat;
	private short mcc;
	private short mnc;
	private int cellId;
	private short tac;
	private short pci;
	private float lat;
	private float lng;
	private short samples;
	private short range;
	private int created;
	private int updated;

	public MozCsvCell(String[] data) {
		this.rat = data[0];
		this.mcc = Short.parseShort(data[1]);
		this.mnc = Short.parseShort(data[2]);
		this.tac = Short.parseShort(data[3]);
		this.cellId = Integer.parseUnsignedInt(data[4]);
		this.pci = Short.parseShort(data[5]);
		this.lng = Float.parseFloat(data[6]);
		this.lat = Float.parseFloat(data[7]);
		this.range = Short.parseShort(data[8]);
		this.samples = Short.parseShort(data[9]);
		this.created = Integer.parseUnsignedInt(data[9]);
		this.updated = Integer.parseUnsignedInt(data[9]);
	}

}

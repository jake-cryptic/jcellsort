import java.util.HashMap;
import java.util.Map;

public class MozEnb {

	public short eNb;
	public Map<Short, MozCsvCell> sectors;

	public MozEnb(short eNb) {
		this.eNb = eNb;
		this.sectors = new HashMap<Short, MozCsvCell>();
	}

	public void addSector(short id, MozCsvCell row) {
		this.sectors.put(id, row);
	}

}

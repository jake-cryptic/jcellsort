import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.TimeZone;

public class CellDatabase {

	private Connection conn = null;
	private PreparedStatement statement = null;

	public CellDatabase() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/lte_jcellsort?user=root&useLegacyDatetimeCode=false&serverTimezone=" + TimeZone.getDefault().getID());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public void prepareStatement(String sql) {
		try {
			statement = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void insertSectors(MozCsvCell cell) {
		try {
			statement.setShort(1, cell.getMnc());
			statement.setInt(2, cell.getEnb());
			statement.setShort(3, cell.getSectorId());
			statement.setShort(4, cell.getPci());
			statement.setFloat(5, cell.getLat());
			statement.setFloat(6, cell.getLng());
			statement.setInt(7, cell.getSamples());
			statement.setInt(8, cell.getCreated());
			statement.setInt(9, cell.getUpdated());
			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void insertEnb(MozEnb enb, short mnc) {
		try {
			float[] enbLoc = enb.calculateLocation();
			statement.setShort(1, mnc);
			statement.setInt(2, enb.getEnb());
			statement.setFloat(3, enbLoc[0]);
			statement.setFloat(4, enbLoc[1]);
			statement.setFloat(5, enb.getLastUpdated());
			statement.executeUpdate();
		} catch (SQLException ex) {
			for (Map.Entry<Short, MozCsvCell> sector : enb.sectors.entrySet()) {
				System.out.println("- " + sector.getValue().getRange());
			}
			ex.printStackTrace();
		}
	}

	public void finish() {
		try {
			conn.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void insertSectors(MozCsvCell cell) {
		try {
			statement.setShort(0, cell.getMnc());
			statement.setInt(1, cell.getEnb());
			statement.setShort(2, cell.getSectorId());
			statement.setShort(3, cell.getPci());
			statement.setFloat(4, cell.getLat());
			statement.setFloat(5, cell.getLng());
			statement.setInt(6, cell.getSamples());
			statement.setInt(7, cell.getCreated());
			statement.setInt(8, cell.getUpdated());

			if (statement.execute()) {
				System.out.println("Inserted eNB: " + cell.getEnb());
			}
		} catch (SQLException ex) {

		}
	}

}

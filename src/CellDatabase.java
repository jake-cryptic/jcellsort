import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CellDatabase {

	private Connection conn = null;
	private PreparedStatement statement = null;

	public CellDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost/lte_jcellsort?user=root");
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
		//statement.setString()
	}

}

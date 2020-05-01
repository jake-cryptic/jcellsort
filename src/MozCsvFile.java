import java.io.*;
import java.util.ArrayList;

public class MozCsvFile {

	private final ArrayList<MozCsvCell> fc;

	public MozCsvFile(String filepath) {
		fc = new ArrayList<MozCsvCell>();
		String line;
		String[] data;

		try {
			FileReader fr = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				data = line.split(",");
				if (data[1].contentEquals("LTE") && data[1].contains("234")){
					fc.add(new MozCsvCell(data));
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public ArrayList<MozCsvCell> getFileContents() {
		return this.fc;
	}

}

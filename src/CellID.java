public class CellID {

	public static int[] convertToEnb(int cellId) {
		String tmp = Integer.toBinaryString(cellId);

		int[] ret = new int[2];
		ret[0] = Integer.parseInt(tmp.substring(0, tmp.length() - 8), 2);
		ret[1] = Integer.parseInt(tmp.substring(tmp.length() - 8, tmp.length()), 2);

		return ret;
	}

}

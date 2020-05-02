public class CellID {

	public static int[] convertToEnb(int cellId) {
		String tmp = Integer.toBinaryString(cellId);

		int[] ret = new int[2];
		int lim = tmp.length() - 8;
		try {
			ret[0] = Integer.parseInt(tmp.substring(0, lim), 2);
			ret[1] = Integer.parseInt(tmp.substring(lim), 2);
		} catch (StringIndexOutOfBoundsException e){
			e.printStackTrace();
			System.out.println(lim);
			System.out.println(cellId);
			System.out.println(tmp);
			System.out.println(tmp.length());
			System.exit(-2);
		}

		return ret;
	}

}

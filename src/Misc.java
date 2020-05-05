public class Misc {

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

	public static float calculateStandardDeviation(float[] array) {
		double total = 0, totalSquared = 0, standardDeviation = 0;
		int numCount = array.length;

		for (float val : array) {
			total = total + val;
			totalSquared = totalSquared + Math.pow(val, 2);
		}

		standardDeviation = Math.sqrt(numCount * totalSquared - Math.pow(total, 2)) / numCount;

		return (float) standardDeviation;
	}

}

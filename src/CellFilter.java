public class CellFilter {

	private final short[] valid_ee_sectors = {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
	};
	private final short[] valid_h3_sectors = {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 71, 72, 73, 74, 76
	};
	private final short[] valid_vf_sectors = {
		10, 12, 14, 15, 16, 18, 19, 20, 22, 24, 25, 26, 28, 29, 30, 32, 34, 35, 36, 38, 39, 40, 44, 46, 48, 50, 54, 56, 58, 60, 64, 66, 68
	};
	private final short[] valid_o2_sectors = {
		110, 112, 114, 115, 116, 117, 120, 122, 124, 125, 126, 127, 130, 132, 134, 135, 136, 137
	};

	public static boolean filter_uk(short mnc, int eNb, short sectorId) {


		return false;
	}

}

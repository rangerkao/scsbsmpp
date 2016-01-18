package com.scsb.function;

public class RandomCode {

    /**
	 * 產生隨機碼(字母+數字)
	 * 
	 * @param length
	 *            隨機碼長度
	 * @return 隨機碼
	 */
	public static String getRandomCode(int length) {
		String randomCode = "";
		for (int i = 0; i < length; i++) {
			randomCode += getRandChar();
		}
		return randomCode;
	}
	public  static String getRandChar() {
		int rand = (int) Math.round(Math.random() * 2);
		long itmp = 0;
		char ctmp = '\u0000';
		// 根據rand的值來決定來生成一個大寫字母、小寫字母和數字
		switch (rand) {
		// 生成大寫字母
		case 1:
			itmp = Math.round(Math.random() * 25 + 65);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);
			// 生成小寫字母
		case 2:
			itmp = Math.round(Math.random() * 25 + 97);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);
			// 生成數字
		default:
			itmp = Math.round(Math.random() * 9);
			return String.valueOf(itmp);
		}
	}
}

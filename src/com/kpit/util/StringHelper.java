package com.kpit.util;

/**
 * 
 * @author sachinc3 this class accept numerical value with decimal format and
 *         string at the end.
 */
public class StringHelper {

	/**
	 * @author sachinc3
	 * @param expression
	 * @return numeric value with decimal and string.
	 */
	public static String getDigit(String expression) {
		String numberOnly = "";
		try {
			numberOnly = expression.replaceAll("([^0-9]+[[.][0-9][a-z]])", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOnly;
	}
}

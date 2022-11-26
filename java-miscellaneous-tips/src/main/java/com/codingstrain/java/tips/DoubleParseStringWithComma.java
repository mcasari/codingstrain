package com.codingstrain.java.tips;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class DoubleParseStringWithComma {

	public static void main(String[] args) {


		
		try {
			String numStr = "1,234";
			Double doubleNum = Double.valueOf(numStr.replaceAll(",", ".")); 
			System.out.println(doubleNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {						
			String numStr = "123.111,234";
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			Number number = format.parse(numStr);
			double doubleNum = number.doubleValue();
			System.out.println(doubleNum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {						
			String numStr = "123.111,234";
			DecimalFormat decimalFormat = new DecimalFormat();
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator(',');
			symbols.setGroupingSeparator('.');
			decimalFormat.setDecimalFormatSymbols(symbols);
			Number num = decimalFormat.parse(numStr);	
			System.out.println(num.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {						
			String numStr = "123.111,234";
			DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
			DecimalFormat decimalFormat = new DecimalFormat("#,###.##", symbols);
			Number num = decimalFormat.parse(numStr);	
			System.out.println(num.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}


}

package com.codingstrain.java.tips;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class DoubleParseStringWithComma {

	public static void main(String[] args) {


		
		try {
			String p = "1,234";
			Double d = Double.valueOf(p.replaceAll(",", ".")); 
			System.out.println(d);
			
			p = "123.111,234";
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			Number number = format.parse(p);
			double dd = number.doubleValue();
			System.out.println(dd);
			
			p = "123.111,234";
			DecimalFormat df = new DecimalFormat();
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator(',');
			symbols.setGroupingSeparator('.');
			df.setDecimalFormatSymbols(symbols);
			Number num = df.parse(p);	
			System.out.println(num.toString());
			
			
			p = "123.111,234";
			symbols = new DecimalFormatSymbols(Locale.ITALY);
			df = new DecimalFormat("#,###.##", symbols );
			num = df.parse(p);	
			System.out.println(num.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}


}

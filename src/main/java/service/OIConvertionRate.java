package service;

import java.text.DecimalFormat;
import java.text.ParseException;

public class OIConvertionRate {
	
	static double backP = 0;
	static double frontP = 10684;

	public static void backAvg() {

		int[] OIs = {77007	,3207, 1707	,845};

		int[] Ps = { 10672, 10634, 10585, 10200 };

		double sum_P = 0;
		double sum_OI = 0;

		for (int i = 0; i < OIs.length; i++) {

			sum_P += OIs[i] * Ps[i];
			sum_OI += OIs[i];

		}

		backP = sum_P / sum_OI;

	}

	public static double calFront(double val, double vol, String name) {

		double avgPrice = val / vol * 1000 / 200D;

		System.out.printf(name + " Price:\t %.1f%n", avgPrice);

		double frontPct = (avgPrice - backP) / (frontP - backP) * 100;
		double frontOI = vol * frontPct / 100;

		System.out.printf(name + " Front Percent:\t %.2f%%%n", frontPct);
		System.out.printf(name + " Front OI:\t %.1f%n", frontOI);

		return frontOI;
	}

	public static void main(String[] args) throws ParseException {

		backAvg();
		System.out.printf("Back Price:\t %.1f%n", backP);
		System.out.printf("Front Price:\t %.1f%n", frontP);

		DecimalFormat df = new DecimalFormat();

		// long
		double val_l = df.parse("176,163,815").doubleValue();
		double vol_l = df.parse("82,523").doubleValue();

		// short
		double val_s = df.parse("81,331,278").doubleValue();
		double vol_s = df.parse("38,111").doubleValue();
		
		//total
		double val_n = df.parse("94,832,537").doubleValue();
		double vol_n = df.parse("44,412").doubleValue();

		double net_p = val_n / vol_n * 1000 / 200D;
		
		double oi_l = calFront(val_l, vol_l, "Long");
		double oi_s = calFront(val_s, vol_s, "Short");

		System.out.printf("NET price:\t %.1f%n", net_p);
		System.out.printf("FRONT OI(L-S):\t %.1f%n", (oi_l - oi_s));
		

	}

}

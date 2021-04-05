package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.Selector;
import entity.TxDaily;
import entity.TxMiTrader;
import util.ConstantString;

public class DataAnalyzer {
	
	Selector selector = new Selector();

	private double calFront(double val,double vol, double frontP, double backP, String name) {

		double avgPrice = val / vol * 1000 / 200D;

		System.out.printf(name + " Price:\t %.1f%n", avgPrice);

		double frontPct = (avgPrice - backP) / (frontP - backP) * 100;
		double frontOI = vol * frontPct / 100;

		System.out.printf(name + " Front Percent:\t %.2f%%%n", frontPct);
		System.out.printf(name + " Front OI:\t %.1f%n", frontOI);

		return frontOI;
	}
	
	public void getFrontOIDif(String date) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd);
		
		Date tDate = sdf.parse(date);
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(tDate);
		
		cal.add(Calendar.DATE, 1);
		
		List<TxDaily> data = selector.getTXDailyData(tDate);
		TxMiTrader miData = selector.getMIData(tDate).get(0);
		
		List<TxDaily> nextDayInfo = selector.getTXDailyData(cal.getTime());
		
		
		
		//calculate back avg price
		double sum_P = 0;
		double sum_OI = 0;

		for (int i = 1; i < data.size(); i++) {
			
			TxDaily rec = data.get(i);

			sum_P += rec.getOi().doubleValue() * rec.getSettlePrice().doubleValue();
			sum_OI += rec.getOi().doubleValue();

		}

		double backP = sum_P / sum_OI;
		double frontP = data.get(0).getSettlePrice().doubleValue();
		
		System.out.printf("Back Price:\t %.1f%n", backP);
		System.out.printf("Front Price:\t %.1f%n", frontP);
		
		double oi_l = calFront(miData.getlOiVal(), miData.getlOi(),frontP,backP, "Long");
		double oi_s = calFront(miData.getsOiVal(), miData.getsOi(),frontP,backP, "Short");

		double net_p = miData.getNetOiVal()/ miData.getNetOi() * 1000 / 200D;
		
		System.out.printf("NET price:\t %.1f%n", net_p);
		System.out.printf("FRONT OI(L-S):\t %.1f%n", (oi_l - oi_s));
		
		if(!nextDayInfo.isEmpty())
			System.out.printf("Next Day Change:\t %d%n", nextDayInfo.get(0).getChange().intValue());

	}
}

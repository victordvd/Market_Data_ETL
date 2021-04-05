package service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import dao.Dao;
import entity.TxDaily;
import entity.TxLTrader;
import entity.TxMiTrader;
import entity.TxoLTrader;
import entity.TxoMiTrader;
import util.ConstantString;

public class LogicService {
	
	SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);

	public void saveTxLTraderData(List<TxLTrader> data) {
		try {
			Dao dao = new Dao();

			dao.saveTxLTraderData(data);

			System.out.println("Insert Success:\ntotal large trader records: " + data.size());

		} catch (Exception e) {

			if (e.getMessage().contains("ORA-00001")) {

				// SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);
				System.out.print("Insert Failed: " + e.getMessage());
				// System.out.println(sdf.format(data.get(0).getDataDate())+" ~
				// "+sdf.format(data.get(data.size()-1).getDataDate()));
			} else
				e.printStackTrace();
		}
	}
	
	public void saveTxoLTraderData(List<TxoLTrader> data) {
		
		System.out.println("insert data..");
		
		try {
			Dao dao = new Dao();

			dao.saveTxoLTraderData(data);

			System.out.println("Insert Success:\ntotal TXO large trader records: " + data.size());

		} catch (Exception e) {

			if (e.getMessage().contains("ORA-00001")) {
				System.out.print("Insert Failed: " + e.getMessage());
			} else
				e.printStackTrace();
		}
	}
	
	public void saveTxMiTraderData(List<TxMiTrader> data) {
		try {
			Dao dao = new Dao();

			dao.saveTxMITraderData(data);

			System.out.println("Insert Success:\ntotal TX large trader records: " + data.size());

		} catch (Exception e) {

			if (e.getMessage().contains("ORA-00001")) {
				System.out.print("Insert Failed: " + e.getMessage());
			} else
				e.printStackTrace();
		}
	}
	
	public void saveTxoMiTraderData(List<TxoMiTrader> data) {
		try {
			Dao dao = new Dao();

			dao.saveTxoMITraderData(data);

			System.out.println("Insert Success:\ntotal TXO large trader records: " + data.size());

		} catch (Exception e) {

			if (e.getMessage().contains("ORA-00001")) {
				System.out.print("Insert Failed: " + e.getMessage());
			} else
				e.printStackTrace();
		}
	}

	public void saveTxDailyData(List<TxDaily> data) {
		try {
			Dao dao = new Dao();

			dao.saveTxDailyData(data);

			System.out.println("Insert Success:\ntotal daily info records: " + data.size());

		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().contains("ORA-00001")) {

				// SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);
				System.out.print("Insert Failed: " + e.getMessage());
				// System.out.println(sdf.format(data.get(0).getDataDate())+" ~
				// "+sdf.format(data.get(data.size()-1).getDataDate()));
			} else
				e.printStackTrace();
		}
	}

	public TxLTrader putRawToVo_largeTrader(String rec) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);

		TxLTrader vo = new TxLTrader();
		String[] fields = rec.split(",");

		for (int i = 0; i < fields.length; i++) {

			if (i >= 3 && fields[i].length() == 1 && fields[i].trim().equals("-"))
				fields[i] = "-1";
			else
				fields[i] = fields[i].trim();

		}

		// vo.setDate(fields[0]);
		// vo.setContract(fields[1]);
		// if (!lang.equals("cn")) {
		//
		// vo.setSettlementMonth(fields[2]);
		// vo.setTypeOfTraders(fields[3]);
		// vo.setTop5_Buy(fields[4]);
		// vo.setTop5_Sell(fields[5]);
		// vo.setTop10_Buy(fields[6]);
		// vo.setTop10_Sell(fields[7]);
		// vo.setOiOfMarket(fields[8]);
		// } else {
		//
		// vo.setContractCname(fields[2]);
		// vo.setSettlementMonth(fields[3]);
		// vo.setTypeOfTraders(fields[4]);
		// vo.setTop5_Buy(fields[5]);
		// vo.setTop5_Sell(fields[6]);
		// vo.setTop10_Buy(fields[7]);
		// vo.setTop10_Sell(fields[8]);
		// vo.setOiOfMarket(fields[9]);
		//
		// }

		vo.setDataDate(sdf.parse(fields[0]));
		vo.setContract(fields[1]);
		vo.setConCname(fields[2]);
		vo.setSettlement(fields[3]);
		vo.setTrader(fields[4]);

		vo.setTop5Buy(Long.valueOf(fields[5]));
		vo.setTop5Sell(Long.valueOf(fields[6]));
		vo.setTop10Buy(Long.valueOf(fields[7]));
		vo.setTop10Sell(Long.valueOf(fields[8]));
		vo.setOi(Long.valueOf(fields[9]));

		return vo;
	}

	public TxoLTrader putRawToVo_txoLargeTrader(String rec) throws ParseException {

		TxoLTrader vo = new TxoLTrader();
		String[] fields = rec.split(",");

		for (int i = 0; i < fields.length; i++) {

			if (i >= 3 && fields[i].length() == 1 && fields[i].trim().equals("-"))
				fields[i] = "-1";
			else
				fields[i] = fields[i].trim();

		}

		String type = fields[3];
		
		if(type!=null) {
			if(type.equals("∂R≈v"))
				type = "C";
			else if(type.equals("ΩÊ≈v"))
				type = "P";
		}
		
		vo.setDataDate(sdf.parse(fields[0]));
		vo.setContract(fields[1]);
		vo.setConCname(fields[2]);
		vo.setType(type);
		vo.setSettlement(fields[4]);
		vo.setTrader(fields[5]);

		vo.setTop5Buy(Long.valueOf(fields[6]));
		vo.setTop5Sell(Long.valueOf(fields[7]));
		vo.setTop10Buy(Long.valueOf(fields[8]));
		vo.setTop10Sell(Long.valueOf(fields[9]));
		vo.setOi(Long.valueOf(fields[10]));

		return vo;
	}
	
	public TxDaily putRawToVo_dailyInfo(String rec) throws ParseException {

		String dash = "-";

		TxDaily vo = new TxDaily();
		String[] fields = rec.split(",");

//		try {
//			System.out.println(" "+new String(rec.getBytes("ISO-8859-1"), "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		for (int i = 0; i < fields.length; i++) {

			if (i >= 3 && fields[i].length() == 1 && fields[i].trim().equals("-"))
				fields[i] = "-1";
			else
				fields[i] = fields[i].trim();

		}
		
		try {

			vo.setRptDate(sdf.parse(fields[0].toString()));
			vo.setContract(fields[1].toString());
			vo.setContractMon(fields[2].trim().toString());
			vo.setOpen(new BigDecimal(fields[3].toString()));
			vo.setHigh(new BigDecimal(fields[4].toString()));
			vo.setLow(new BigDecimal(fields[5].toString()));
			vo.setLast(new BigDecimal(fields[6].toString()));

			vo.setChange(new BigDecimal(fields[7].toString()));
			vo.setChangePercent(fields[8].toString());
			vo.setVolumn(new BigDecimal(fields[9].toString()));
			vo.setSettlePrice(fields[10].toString().equals(dash) ? null : new BigDecimal(fields[10].toString()));
			vo.setOi(fields[11].toString().equals(dash) ? null : new BigDecimal(fields[11].toString()));

			vo.setTradingSession(fields[17].toString());
			
//			System.out.println("VO: " + vo.getContract()+" "+vo.getContractMon()+" "+vo.getRptDate());

			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public TxMiTrader putRawToVo_txMITrader(String rec) throws ParseException {

		TxMiTrader vo = new TxMiTrader();
		String[] fields = rec.split(",");

		for (int i = 0; i < fields.length; i++) {

			if (i >= 3 && fields[i].length() == 1 && fields[i].trim().equals("-"))
				fields[i] = "-1";
			else
				fields[i] = fields[i].trim();

		}
	
		vo.setDataDate(sdf.parse(fields[0]));
		vo.setContract(fields[1]);
		vo.setTrader(fields[2]);
		vo.setlTVol(Long.valueOf(fields[3]));
		vo.setlTVal(Long.valueOf(fields[4]));
		vo.setsTVol(Long.valueOf(fields[5]));
		vo.setsTVal(Long.valueOf(fields[6]));
		vo.setNetTVol(Long.valueOf(fields[7]));
		vo.setNetTVal(Long.valueOf(fields[8]));
		vo.setlOi(Long.valueOf(fields[9]));
		vo.setlOiVal(Long.valueOf(fields[10]));
		vo.setsOi(Long.valueOf(fields[11]));
		vo.setsOiVal(Long.valueOf(fields[12]));
		vo.setNetOi(Long.valueOf(fields[13]));
		vo.setNetOiVal(Long.valueOf(fields[14]));

		return vo;
	}
	
	public TxoMiTrader putRawToVo_txoMITrader(String rec) throws ParseException {

		TxoMiTrader vo = new TxoMiTrader();
		String[] fields = rec.split(",");

		for (int i = 0; i < fields.length; i++) {

			if (i >= 3 && fields[i].length() == 1 && fields[i].trim().equals("-"))
				fields[i] = "-1";
			else
				fields[i] = fields[i].trim();

		}

		String type = fields[2];
		
		if(type!=null) {
			if(type.equals("CALL"))
				type = "C";
			else if(type.equals("PUT"))
				type = "P";
		}
		
		vo.setDataDate(sdf.parse(fields[0]));
		vo.setContract(fields[1]);
		vo.setType(type);
		vo.setTrader(fields[3]);
		vo.setlTVol(Long.valueOf(fields[4]));
		vo.setlTVal(Long.valueOf(fields[5]));
		vo.setsTVol(Long.valueOf(fields[6]));
		vo.setsTVal(Long.valueOf(fields[7]));
		vo.setNetTVol(Long.valueOf(fields[8]));
		vo.setNetTVal(Long.valueOf(fields[9]));
		vo.setlOi(Long.valueOf(fields[10]));
		vo.setlOiVal(Long.valueOf(fields[11]));
		vo.setsOi(Long.valueOf(fields[12]));
		vo.setsOiVal(Long.valueOf(fields[13]));
		vo.setNetOi(Long.valueOf(fields[14]));
		vo.setNetOiVal(Long.valueOf(fields[15]));

		return vo;
	}
}

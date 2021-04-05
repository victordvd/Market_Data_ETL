package excute;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import entity.TxoLTrader;
import service.LogicService;
import util.ConstantString;
import util.PropertyUtil;
import util.UrlLoader;


public class TxoLargeTraderExecuter{
	   static final String dlDir = "D:\\Users\\iii\\desktop\\specuproj";
	
	   static LogicService logiServ = new LogicService() ;
	  
	public static void main(String[] args) throws Exception {
			
		System.out.println("<<<<<<<<<<< TXO Large Trader ETL start >>>>>>>>>>>");
		
		Calendar cal_now = Calendar.getInstance();
		Calendar cal_s = Calendar.getInstance();
		
//		cal_s.set(2005,1,1,0,0,0);//st 20050201
		cal_s.set(2018,0,1,0,0,0);//st
		
		Calendar cal_e = (Calendar)cal_s.clone();
		cal_e.add(Calendar.MONTH, 3);//max download range = 3 months
		cal_e.add(Calendar.DAY_OF_MONTH, -1);
		
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);
		
		while(cal_s.get(Calendar.YEAR)<=cal_now.get(Calendar.YEAR)){
			if(cal_s.get(Calendar.YEAR)==cal_now.get(Calendar.YEAR)&&cal_s.get(Calendar.MONTH)>cal_now.get(Calendar.MONTH))
				break;
			
			System.out.println("start: "+sdf.format(cal_s.getTime())+" ,end: "+sdf.format(cal_e.getTime()));

				downloadTxData(sdf.format(cal_s.getTime()),sdf.format(cal_e.getTime()));
				
				cal_s.add(Calendar.MONTH,3);
				cal_e = (Calendar)cal_s.clone();
				cal_e.add(Calendar.MONTH, 3);
				cal_e.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		System.out.println("<<<<<<<<<<< TXO Large Trader ETL end >>>>>>>>>>>");
	}
	
	public static void loadDailyData(Calendar cal_s) throws Exception {			
			System.out.println("<<<<<<<<<<< TXO Large Trader ETL start >>>>>>>>>>>");
			
			Calendar cal_now = Calendar.getInstance();

			Calendar cal_e = (Calendar)cal_s.clone();
			cal_e.add(Calendar.MONTH, 3);//max download range = 3 months
			cal_e.add(Calendar.DAY_OF_MONTH, -1);
			
			SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);
			
			while(cal_s.get(Calendar.YEAR)<=cal_now.get(Calendar.YEAR)){
				if(cal_s.get(Calendar.YEAR)==cal_now.get(Calendar.YEAR)&&cal_s.get(Calendar.MONTH)>cal_now.get(Calendar.MONTH))
					break;
				
				System.out.println("start: "+sdf.format(cal_s.getTime())+" ,end: "+sdf.format(cal_e.getTime()));

					downloadTxData(sdf.format(cal_s.getTime()),sdf.format(cal_e.getTime()));
					
					cal_s.add(Calendar.MONTH,3);
					cal_e = (Calendar)cal_s.clone();
					cal_e.add(Calendar.MONTH, 3);
					cal_e.add(Calendar.DAY_OF_MONTH, -1);
			}
			
			System.out.println("<<<<<<<<<<< TXO Large Trader ETL end >>>>>>>>>>>");	
	}
	
	
	/**
	 * download OI of large traders as file
	 */
	private static void downloadTxData(String st,String ed) throws Exception{

		String exUrl = PropertyUtil.getValue("URL_TXO_L_TRADER_INFO");
		String downloadPath = dlDir;

		StringBuilder sb = new StringBuilder();
		StringBuilder fNameSb = new StringBuilder();
		
		//TAIEX's data (the max period of month for download is 3)
		
		fNameSb.append("TXO-top10_");
		fNameSb.append(st.replace("/", ""));
		fNameSb.append("-");
		fNameSb.append(ed.replace("/", ""));
		fNameSb.append(".txt");

		sb.append("queryStartDate=");
		sb.append(st);
		sb.append("&queryEndDate=");
		sb.append(ed);

		boolean success = false;
		do {
			success = new UrlLoader().downloadFile(exUrl, downloadPath,"POST",sb.toString(),fNameSb.toString());

		} while (!success);
		
		analyzeData(downloadPath+"\\"+fNameSb);
	}

	private static void analyzeData(String fDir) throws IOException, ParseException{
		
		System.out.println("analyzing data..");
		
		//D:\Users\iii\Desktop\specuproj\TOP10_20161202-20161203_2.txt
		File file = new File(fDir);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		// header
//		String header = new String(br.readLine());//.getBytes(),"MS950"
		
//		System.out.println(header);

		List<TxoLTrader> data = new ArrayList<>();
//		TreeSet<String> settleSet = new TreeSet<>();
		
		while (br.ready()) {

			String rec = new String(br.readLine());
//			System.out.println(rec);//show record content
			
			if (rec.length()>=4&&rec.charAt(4) == '/') {

				String[] items = rec.split(",");

				if(items[1].trim().equals("TXO")){
					TxoLTrader vo = new LogicService().putRawToVo_txoLargeTrader(rec);
					
					data.add(vo);
				}
			}
		}
	
//		for(String settle:settleSet)
//			System.out.println(settle);
		
		br.close();
		
		//insert data to db
		logiServ.saveTxoLTraderData(data);
		
	}

}
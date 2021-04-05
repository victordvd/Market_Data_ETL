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
import java.util.TreeSet;

import entity.TxDaily;
import service.LogicService;
import util.ConstantString;
import util.PropertyUtil;
import util.UrlLoader;

public class TxDailyExecuter {
	   static final String dlDir = "D:\\Users\\iii\\desktop\\specuproj";
	
	   static LogicService logiServ = new LogicService() ;
	   
	   
	public static void main(String[] args) throws Exception {
		
		System.out.println("<<<<<<<<<<< TX DAILY INFO ETL start >>>>>>>>>>>");
			
		String  url = PropertyUtil.getValue("URL_TX_DAILY_INFO");
		System.out.println("DAILY SRC URL: "+url);
		
		
		Calendar cal_now = Calendar.getInstance();
		Calendar cal_s = Calendar.getInstance();
		
//		cal_s.set(2005,1,1,0,0,0);//st 20050201
		cal_s.set(2018,0,1,0,0,0);//st
		
		//daily interval less than 1 month
		Calendar cal_e = (Calendar)cal_s.clone();
		cal_e.add(Calendar.MONTH, 1);//max download range = 1 months
		cal_e.add(Calendar.DAY_OF_MONTH, -1);
		
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);
		
		
		while(cal_s.get(Calendar.YEAR)<=cal_now.get(Calendar.YEAR)){
			if(cal_s.get(Calendar.YEAR)==cal_now.get(Calendar.YEAR)&&cal_s.get(Calendar.MONTH)>cal_now.get(Calendar.MONTH))
				break;
			
			System.out.println("start: "+sdf.format(cal_s.getTime())+" ,end: "+sdf.format(cal_e.getTime()));

				downloadDailyInfoData(sdf.format(cal_s.getTime()),sdf.format(cal_e.getTime()));
				
				cal_s.add(Calendar.MONTH,1);
				cal_e = (Calendar)cal_s.clone();
				cal_e.add(Calendar.MONTH, 1);
				cal_e.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		System.out.println("<<<<<<<<<<< Daily TX ETL end >>>>>>>>>>>");
	}
	
	public static void loadDailyData(Calendar cal_s) throws Exception {
		
		System.out.println("<<<<<<<<<<< TX DAILY INFO ETL start >>>>>>>>>>>");
			
		String  url = PropertyUtil.getValue("URL_TX_DAILY_INFO");
		System.out.println("DAILY SRC URL: "+url);
		
		Calendar cal_now = Calendar.getInstance();
		
		//daily interval less than 1 month
		Calendar cal_e = (Calendar)cal_s.clone();
		cal_e.add(Calendar.MONTH, 1);//max download range = 1 months
		cal_e.add(Calendar.DAY_OF_MONTH, -1);
		
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);
		
		
		while(cal_s.get(Calendar.YEAR)<=cal_now.get(Calendar.YEAR)){
			if(cal_s.get(Calendar.YEAR)==cal_now.get(Calendar.YEAR)&&cal_s.get(Calendar.MONTH)>cal_now.get(Calendar.MONTH))
				break;
			
			System.out.println("start: "+sdf.format(cal_s.getTime())+" ,end: "+sdf.format(cal_e.getTime()));

				downloadDailyInfoData(sdf.format(cal_s.getTime()),sdf.format(cal_e.getTime()));
				
				cal_s.add(Calendar.MONTH,1);
				cal_e = (Calendar)cal_s.clone();
				cal_e.add(Calendar.MONTH, 1);
				cal_e.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		System.out.println("<<<<<<<<<<< Large Trader ETL end >>>>>>>>>>>");
		
		
	}
	
	private static void downloadDailyInfoData(String st,String ed) throws Exception{
		//TAIEX
		String exUrl = PropertyUtil.getValue("URL_TX_DAILY_INFO");
		String downloadPath = dlDir;

		StringBuilder sb = new StringBuilder();
		StringBuilder fNameSb = new StringBuilder();

		String datestart = st;
		String dateend = ed;
		String COMMODITY_ID = "TX";
		
		
		fNameSb.append("TXDAILY_");
		fNameSb.append(datestart.replace("/", ""));
		fNameSb.append("-");
		fNameSb.append(dateend.replace("/", ""));
		fNameSb.append(".csv");

		sb.append("commodity_id=");
		sb.append(COMMODITY_ID);
		sb.append("&down_type=1");
		sb.append("&queryStartDate=");//2017/05/01
		sb.append(datestart);
		sb.append("&queryEndDate=");
		sb.append(dateend);
		
//		System.out.println("DAILY INFO PARAM: "+sb.toString());

		boolean success = false;
		do {
			success = new UrlLoader().downloadFile(exUrl, downloadPath,"POST",sb.toString(),fNameSb.toString());

		} while (!success);
		
		analyzeData(downloadPath+"\\"+fNameSb);
	}

	private static void analyzeData(String fDir) throws IOException, ParseException{
		
		String aimCon = "TX";

		File file = new File(fDir);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		int recCount = 0;

		// header
		String header = new String(br.readLine());//.getBytes(),"MS950"
		
		String unicode = "";
		
		 for(int i = 0; i < header.length(); i++) {
	            String tmp = Integer.toHexString((int) header.charAt(i));
	            unicode = unicode + "\\u" + tmp;  
	           
	       }

		List<TxDaily> data = new ArrayList<>();
		TreeSet<String> settleSet = new TreeSet<>();
		
		while (br.ready()) {

			String rec = new String(br.readLine());//.getBytes(),"MS950"

//			System.out.println(rec);
			
			if (rec.length()>=4&&rec.charAt(4) == '/') {

				String[] items = rec.split(",");

				if(items[1].trim().equals(aimCon)&&items[2].trim().length()==6){
					TxDaily vo = new LogicService().putRawToVo_dailyInfo(rec);
					
					data.add(vo);
					recCount++;	
				}
			}

		}

		System.out.println("total: "+recCount);
		
		for(String settle:settleSet)
			System.out.println(settle);
		
		br.close();
		
		logiServ.saveTxDailyData(data);
		
	}

}
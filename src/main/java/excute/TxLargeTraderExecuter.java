package excute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import entity.TxLTrader;
import service.LogicService;
import util.ConstantString;
import util.PropertyUtil;
import util.UrlLoader;

public class TxLargeTraderExecuter {
	   static final String dlDir = "D:\\Users\\iii\\desktop\\specuproj";
	
	   static LogicService logiServ = new LogicService() ;
	  
	public static void main(String[] args) throws Exception {
			
		System.out.println("<<<<<<<<<<< Large Trader ETL start >>>>>>>>>>>");
		
		Calendar cal_now = Calendar.getInstance();
		Calendar cal_s = Calendar.getInstance();
		
//		cal_s.set(2005,1,1,0,0,0);//st 20050201
		cal_s.set(2018,11,1,0,0,0);//st
		
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
		
		System.out.println("<<<<<<<<<<< Large Trader ETL end >>>>>>>>>>>");
	}
	
	public static void loadData(Calendar cal_s) throws Exception {
		System.out.println("<<<<<<<<<<< Large Trader ETL start >>>>>>>>>>>");
		
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
		
		System.out.println("<<<<<<<<<<< Large Trader ETL end >>>>>>>>>>>");	
	}
	
	
	/**
	 * download OI of large traders as file
	 */
	private static void downloadTxData(String st,String ed) throws Exception{
		//TAIEX
		String exUrl = PropertyUtil.getValue("URL_TX_L_TRADER_INFO");
		String downloadPath = dlDir;

		StringBuilder sb = new StringBuilder();
		StringBuilder fNameSb = new StringBuilder();
		
		//TAIEX's data (the max period of month for download is 3)

		fNameSb.append("TX-top10_");
		fNameSb.append(st.replace("/", ""));
		fNameSb.append("-");
		fNameSb.append(ed.replace("/", ""));
		fNameSb.append(".txt");

		
		sb.append("queryStartDate=");
		sb.append(st);
		sb.append("&queryEndDate=");
		sb.append(ed);;

		boolean success = false;
		do {
			success = new UrlLoader().downloadFile(exUrl, downloadPath,"POST",sb.toString(),fNameSb.toString());

		} while (!success);
		
		analyzeData(downloadPath+"\\"+fNameSb);
	}

	private static void analyzeData(String fDir) throws IOException, ParseException{
		
		//D:\Users\iii\Desktop\specuproj\TOP10_20161202-20161203_2.txt
		File file = new File(fDir);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		
		
		// header
//		String header = new String(br.readLine().getBytes());//,"MS950"
		
//		System.out.println("Encoding: "+fr.getEncoding()+"\n"+ header );

		List<TxLTrader> data = new ArrayList<>();
//		TreeSet<String> settleSet = new TreeSet<>();
		
		while (br.ready()) {

			String rec = new String(br.readLine());//.getBytes(),"MS950"

//			System.out.println("ANAL:"+rec);
			
			if (rec.length()>=4&&rec.charAt(4) == '/') {

				String[] items = rec.split(",");

				if(items[1].trim().equals("TX")){
					TxLTrader vo = new LogicService().putRawToVo_largeTrader(rec);
					data.add(vo);
				}
				
				TxLTrader vo = new LogicService().putRawToVo_largeTrader(rec);
				data.add(vo);
			}
		}
	
//		for(String settle:settleSet)
//			System.out.println(settle);
		
		br.close();
		
		//insert data to db
		logiServ.saveTxLTraderData(data);
		
	}

}
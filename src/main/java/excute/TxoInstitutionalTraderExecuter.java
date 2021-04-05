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

import entity.TxoMiTrader;
import service.LogicService;
import util.ConstantString;
import util.PropertyUtil;
import util.UrlLoader;

public class TxoInstitutionalTraderExecuter{
	   static final String dlDir = "D:\\Users\\iii\\desktop\\specuproj";
	
	   static LogicService logiServ = new LogicService() ;
	  
	public static void main(String[] args) throws Exception {
		
		Calendar cal_s = Calendar.getInstance();
		
		cal_s.set(2017,8,7,0,0,0);//the first data on 2014/09/02
		
		loadData(cal_s,null);

	}
	
	public static void loadData(Calendar cal_s,Calendar cal_e) throws Exception {			
			System.out.println("<<<<<<<<<<< TXO Institutional Trader ETL start1 >>>>>>>>>>>");

			if(cal_e == null)
				cal_e = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);

			System.out.println("start: "+sdf.format(cal_s.getTime())+" ,end: "+sdf.format(cal_e.getTime()));

			downloadTxData(sdf.format(cal_s.getTime()),sdf.format(cal_e.getTime()));

			System.out.println("<<<<<<<<<<< TXO Institutional Trader ETL end >>>>>>>>>>>");	
	}
	
	
	/**
	 * download OI of large traders as file
	 */
	private static void downloadTxData(String st,String ed) throws Exception{

		String exUrl = PropertyUtil.getValue("URL_TXO_MI_TRADER_INFO");
		String downloadPath = dlDir;

		StringBuilder sb = new StringBuilder();
		StringBuilder fNameSb = new StringBuilder();
		
		fNameSb.append("TXO-MI_");
		fNameSb.append(st.replace("/", ""));
		fNameSb.append("-");
		fNameSb.append(ed.replace("/", ""));
		fNameSb.append(".txt");

		sb.append("commodityId=TXO");
		sb.append("&queryStartDate=");
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

		File file = new File(fDir);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		// header
		String header = new String(br.readLine());//.getBytes(),"MS950"
		if(header.contains("<!DOCTYPE")) {
			System.out.println("file format is not correct!");
		}
//		System.out.println(header);

		List<TxoMiTrader> data = new ArrayList<>();
//		TreeSet<String> settleSet = new TreeSet<>();
		
		while (br.ready()) {

			String rec = new String(br.readLine());
//			System.out.println(rec);
			
			if (rec.length()>=4&&rec.charAt(4) == '/') {

				String[] items = rec.split(",");

				if(items[1].trim().equals("TXO")||items[1].trim().equals("\u81FA\u6307\u9078\u64C7\u6B0A")){
					TxoMiTrader vo = new LogicService().putRawToVo_txoMITrader(rec);
					
					data.add(vo);
				}
			}
		}
	
//		for(String settle:settleSet)
//			System.out.println(settle);
		
		br.close();
		
		//insert data to db
		logiServ.saveTxoMiTraderData(data);
		
	}

}
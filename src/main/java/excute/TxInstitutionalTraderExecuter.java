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

import entity.TxMiTrader;
import service.LogicService;
import util.ConstantString;
import util.PropertyUtil;
import util.UrlLoader;

public class TxInstitutionalTraderExecuter {
	static final String dlDir = "D:\\Users\\iii\\desktop\\specuproj";

	static LogicService logiServ = new LogicService();

	public static void main(String[] args) throws Exception {

		Calendar cal_s = Calendar.getInstance();

		cal_s.set(2018, 11, 1, 0, 0, 0);// the first data on 2014/09/02

		loadData(cal_s);

	}

	public static void loadData(Calendar cal_s) throws Exception {
		System.out.println("<<<<<<<<<<< TX Institutional Trader ETL start >>>>>>>>>>>");
		
		Calendar cal_e = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(ConstantString.DATE_FORMAT_yMd_splash);

		System.out.println("start: " + sdf.format(cal_s.getTime()) + " ,end: " + sdf.format(cal_e.getTime()));

		downloadTxData(sdf.format(cal_s.getTime()), sdf.format(cal_e.getTime()));

		System.out.println("<<<<<<<<<<< TX Institutional Trader ETL end >>>>>>>>>>>");
	}

	/**
	 * download OI of large traders as file
	 */
	private static void downloadTxData(String st, String ed) throws Exception {

		String exUrl = PropertyUtil.getValue("URL_TX_MI_TRADER_INFO");
		String downloadPath = dlDir;

		StringBuilder sb = new StringBuilder();
		StringBuilder fNameSb = new StringBuilder();

		fNameSb.append("TX-MI_");
		fNameSb.append(st.replace("/", ""));
		fNameSb.append("-");
		fNameSb.append(ed.replace("/", ""));
		fNameSb.append(".txt");

	
		sb.append("queryStartDate=");
		sb.append(st);
		sb.append("&queryEndDate=");
		sb.append(ed);
//		sb.append("&commodityId=TXF");

		boolean success = false;
		do {
			success = new UrlLoader().downloadFile(exUrl, downloadPath, "POST", sb.toString(), fNameSb.toString());

		} while (!success);

		analyzeData(downloadPath + "\\" + fNameSb);
	}

	private static void analyzeData(String fDir) throws IOException, ParseException {

		File file = new File(fDir);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		// header
		// String header = new String(br.readLine());//.getBytes(),"MS950"

		// System.out.println(header);

		List<TxMiTrader> data = new ArrayList<>();
		// TreeSet<String> settleSet = new TreeSet<>();

		while (br.ready()) {

			String rec = new String(br.readLine());
			// System.out.println(rec);

			if (rec.length() >= 4 && rec.charAt(4) == '/') {

				String[] items = rec.split(",");

				if (items[1].trim().equals("TX") || items[1].trim().equals("»OªÑ´Á³f")) {
					TxMiTrader vo = new LogicService().putRawToVo_txMITrader(rec);

					data.add(vo);
				}
			}
		}

		// for(String settle:settleSet)
		// System.out.println(settle);

		br.close();

		// insert data to db
		logiServ.saveTxMiTraderData(data);

	}


}
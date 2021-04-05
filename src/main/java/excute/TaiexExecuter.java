package excute;

import java.io.IOException;

import util.PropertyUtil;
import util.UrlLoader;

/**
 * undo
 * @author iii
 *
 */
public class TaiexExecuter {

	public static void main(String[] args) throws IOException {
		downloadTxData();

	}
	
	private static void downloadTxData() throws IOException{
		
		String  url = PropertyUtil.getValue("URL_TAIEX_DAILY_INFO");
	
		
		String downloadPath = "D:\\Users\\iii\\desktop\\specuproj";

		StringBuilder fNameSb = new StringBuilder();
		StringBuilder sb = new StringBuilder();

		
		//test twse
		sb.append("myear=");
		sb.append("96");
		sb.append("&mmon=");
		sb.append("04");

		boolean success = false;
		do {
			success = new UrlLoader().downloadFile(url, downloadPath,"POST",sb.toString(),null);

		} while (!success);
		
	}

	
}

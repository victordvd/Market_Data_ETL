package excute;

import java.util.Calendar;

public class GlobalExcuter {

	public static void main(String[] args) throws Exception {
		Calendar cal_s = Calendar.getInstance();
		
//		cal_s.set(2005,1,1,0,0,0);//st 20050201
		cal_s.set(2018,10,1,0,0,0);
		
		TxDailyExecuter.loadDailyData((Calendar)cal_s.clone());
		TxLargeTraderExecuter.loadData((Calendar)cal_s.clone());
		TxInstitutionalTraderExecuter.loadData((Calendar)cal_s.clone());
		TxoLargeTraderExecuter.loadDailyData((Calendar)cal_s.clone());
		TxoInstitutionalTraderExecuter.loadData((Calendar)cal_s.clone(),null);


	}

}

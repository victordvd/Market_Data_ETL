package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import entity.TxDaily;
import entity.TxLTrader;
import entity.TxMiTrader;
import entity.TxoLTrader;
import entity.TxoMiTrader;

public class Dao {

	final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	final String user = "system";
	final String pass = "fcop";
	Connection conn = null;

	public Dao() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			conn = DriverManager.getConnection(DB_URL, user, pass);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveTxLTraderData(List<TxLTrader> data) throws SQLException {

		StringBuffer sqlTmpl = new StringBuffer();
	
//		sqlTmpl.append("MERGE INTO tx_l_trader t");
//		sqlTmpl.append(" USING(select f.data_date,f.contract,f.settlement,f.trader from tx_l_trader f)");
//		sqlTmpl.append(" ON(1=1)");
////		sqlTmpl.append(" WHEN MATCHED THEN UPDATE SET t.contract = t.contract");
//		sqlTmpl.append(" WHEN NOT MATCHED THEN");
//		sqlTmpl.append(" INSERT(t.data_date,t.contract,t.settlement,t.trader,t.top_5_buy,t.top_5_sell,t.top_10_buy,t.top_10_sell,t.oi)");
//		sqlTmpl.append(" VALUES(?,?,?,?,?,?,?,?,?)");
		
		sqlTmpl.append("INSERT /*+IGNORE_ROW_ON_DUPKEY_INDEX(tx_l_trader,TX_L_TRADER_PK)*/ INTO tx_l_trader");
		sqlTmpl.append("(data_date,contract,settlement,trader,top_5_buy,top_5_sell,top_10_buy,top_10_sell,oi,con_cname)");
		sqlTmpl.append("VALUES(?,?,?,?,?,?,?,?,?,?)");
		
		conn.setAutoCommit(false);  
		
		PreparedStatement ps = conn.prepareStatement(sqlTmpl.toString());	
		
		for (int i = 0; i < data.size(); i++) {
	
			TxLTrader rec = data.get(i);
			
//			System.out.println("saveTxTraderData: "+rec.getDataDate()+" "+rec.getSettlement()+" "+rec.getTrader());
			
			if(rec.getTrader().equals("-1"))
				continue;
			
			ps.setDate(1, new java.sql.Date(rec.getDataDate().getTime()));
			ps.setString(2, rec.getContract());
			ps.setString(3, rec.getSettlement());
			ps.setString(4, rec.getTrader());
			ps.setLong(5, rec.getTop5Buy());
			ps.setLong(6, rec.getTop5Sell());
			ps.setLong(7, rec.getTop10Buy());
			ps.setLong(8, rec.getTop10Sell());
			ps.setLong(9, rec.getOi());
			ps.setString(10, rec.getConCname());
			
			ps.addBatch();
		
		}

//		System.out.println(sql.toString());
		
		ps.executeBatch();
		conn.commit(); 
	}
	
	public void saveTxoLTraderData(List<TxoLTrader> data) throws SQLException {

		StringBuffer sqlTmpl = new StringBuffer();
	
		sqlTmpl.append("INSERT /*+IGNORE_ROW_ON_DUPKEY_INDEX(txo_l_trader,TXO_L_TRADER_PK)*/ INTO txo_l_trader");
		sqlTmpl.append("(data_date,contract,con_cname,type,settlement,trader,top_5_buy,top_5_sell,top_10_buy,top_10_sell,oi)");
		sqlTmpl.append("VALUES(?,?,?,?,?,?,?,?,?,?,?)");
		
		conn.setAutoCommit(false);  
		
		PreparedStatement ps = conn.prepareStatement(sqlTmpl.toString());	
		
		for (int i = 0; i < data.size(); i++) {
	
			TxoLTrader rec = data.get(i);
			
//			System.out.println("saveTxTraderData: "+rec.getDataDate()+" "+rec.getSettlement()+" "+rec.getTrader());
			
			if(rec.getTrader().equals("-1"))
				continue;
			
			ps.setDate(1, new java.sql.Date(rec.getDataDate().getTime()));
			ps.setString(2, rec.getContract());
			ps.setString(3, rec.getConCname());
			ps.setString(4, rec.getType());
			ps.setString(5, rec.getSettlement());
			ps.setString(6, rec.getTrader());
			ps.setLong(7, rec.getTop5Buy());
			ps.setLong(8, rec.getTop5Sell());
			ps.setLong(9, rec.getTop10Buy());
			ps.setLong(10, rec.getTop10Sell());
			ps.setLong(11, rec.getOi());
					
			ps.addBatch();
		}

//		System.out.println(sql.toString());
		
		ps.executeBatch();
		conn.commit(); 
	}
	
	public void saveTxDailyData(List<TxDaily> data) throws SQLException {

		StringBuilder sqlTmpl = new StringBuilder();
		
		sqlTmpl.append("INSERT /*+IGNORE_ROW_ON_DUPKEY_INDEX(tx_daily,TX_DAILY_PK)*/ INTO tx_daily");
		sqlTmpl.append("(contract,contract_mon,rpt_date,open,high,low,last,change,change_percent,volumn,settle_price,oi,trading_session)");
		sqlTmpl.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		conn.setAutoCommit(false);  
		PreparedStatement ps = conn.prepareStatement(sqlTmpl.toString());	
		
		for (int i = 0; i < data.size(); i++) {
	
			TxDaily rec = data.get(i);
			
//			System.out.println("DAO: " + rec.getContract()+" "+rec.getContractMon()+" "+rec.getRptDate());

			ps.setString(1,rec.getContract());
			ps.setString(2,rec.getContractMon());
			ps.setDate(3,new java.sql.Date(rec.getRptDate().getTime()));
			ps.setBigDecimal(4,rec.getOpen());
			ps.setBigDecimal(5,rec.getHigh());
			ps.setBigDecimal(6,rec.getLow());
			ps.setBigDecimal(7,rec.getLast());
			ps.setBigDecimal(8,rec.getChange());
			ps.setString(9,rec.getChangePercent());
			ps.setBigDecimal(10,rec.getVolumn());
			ps.setBigDecimal(11,rec.getSettlePrice());
			ps.setBigDecimal(12,rec.getOi());
			ps.setString(13,rec.getTradingSession());
			
			ps.addBatch();
		}

//		System.out.println(sql.toString());
		
		ps.executeBatch();
		conn.commit(); 
	}

	public void saveTxMITraderData(List<TxMiTrader> data) throws SQLException {

		StringBuffer sqlTmpl = new StringBuffer();
	
		sqlTmpl.append("INSERT /*+IGNORE_ROW_ON_DUPKEY_INDEX(TX_MI_TRADER,TX_MI_TRADER_PK)*/ INTO TX_MI_TRADER");
		sqlTmpl.append("(data_date,contract,trader,l_t_vol,l_t_val,s_t_vol,s_t_val,net_t_vol,net_t_val,l_oi,l_oi_val,s_oi,s_oi_val,net_oi,net_oi_val)");
		sqlTmpl.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		conn.setAutoCommit(false);  
		
		PreparedStatement ps = conn.prepareStatement(sqlTmpl.toString());	
		
		for (int i = 0; i < data.size(); i++) {
	
			TxMiTrader rec = data.get(i);
						
			if(rec.getTrader().equals("-1"))
				continue;

			ps.setDate(1, new java.sql.Date(rec.getDataDate().getTime()));
			ps.setString(2, rec.getContract());
			ps.setString(3, rec.getTrader());
			ps.setLong(4, rec.getlTVol());
			ps.setLong(5, rec.getlTVal());
			ps.setLong(6, rec.getsTVol());
			ps.setLong(7, rec.getsTVal());
			ps.setLong(8, rec.getNetTVol());
			ps.setLong(9, rec.getNetTVal());
			ps.setLong(10, rec.getlOi());
			ps.setLong(11, rec.getlOiVal());
			ps.setLong(12, rec.getsOi());
			ps.setLong(13, rec.getsOiVal());
			ps.setLong(14, rec.getNetOi());
			ps.setLong(15, rec.getNetOiVal());
					
			ps.addBatch();
		}

//		System.out.println(sql.toString());
		
		ps.executeBatch();
		conn.commit(); 
	}
	
	public void saveTxoMITraderData(List<TxoMiTrader> data) throws SQLException {

		StringBuffer sqlTmpl = new StringBuffer();
	
		sqlTmpl.append("INSERT /*+IGNORE_ROW_ON_DUPKEY_INDEX(TXO_MI_TRADER,TXO_MI_TRADER_PK)*/ INTO TXO_MI_TRADER");
		sqlTmpl.append("(data_date,contract,type,trader,l_t_vol,l_t_val,s_t_vol,s_t_val,net_t_vol,net_t_val,l_oi,l_oi_val,s_oi,s_oi_val,net_oi,net_oi_val)");
		sqlTmpl.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		conn.setAutoCommit(false);  
		
		PreparedStatement ps = conn.prepareStatement(sqlTmpl.toString());	
		
		for (int i = 0; i < data.size(); i++) {
	
			TxoMiTrader rec = data.get(i);
						
			if(rec.getTrader().equals("-1"))
				continue;
			
//			System.out.println("Degug"+rec.getType());
			
			ps.setDate(1, new java.sql.Date(rec.getDataDate().getTime()));
			ps.setString(2, rec.getContract());
			ps.setString(3, rec.getType());
			ps.setString(4, rec.getTrader());
			ps.setLong(5, rec.getlTVol());
			ps.setLong(6, rec.getlTVal());
			ps.setLong(7, rec.getsTVol());
			ps.setLong(8, rec.getsTVal());
			ps.setLong(9, rec.getNetTVol());
			ps.setLong(10, rec.getNetTVal());
			ps.setLong(11, rec.getlOi());
			ps.setLong(12, rec.getlOiVal());
			ps.setLong(13, rec.getsOi());
			ps.setLong(14, rec.getsOiVal());
			ps.setLong(15, rec.getNetOi());
			ps.setLong(16, rec.getNetOiVal());
					
			ps.addBatch();
		}

//		System.out.println(sqlTmpl.toString());
		
		ps.executeBatch();
		conn.commit(); 
	}
	
}

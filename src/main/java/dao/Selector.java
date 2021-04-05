package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.TxDaily;
import entity.TxMiTrader;

public class Selector {

	final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	final String user = "system";
	final String pass = "fcop";
	Connection con = null;
	Statement statement = null;

	public Selector() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			con = DriverManager.getConnection(DB_URL, user, pass);
			statement = con.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<TxDaily> getTXDailyData(Date date){
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT *");
		sql.append(" FROM tx_daily");
		sql.append(" WHERE 1=1");
		sql.append(" AND rpt_date = ?");
		sql.append(" ORDER BY contract_mon");
		
		ArrayList<TxDaily> r = new ArrayList<TxDaily>();
		
		try {
			
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setDate(1, new java.sql.Date(date.getTime()));
			
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
			
				TxDaily vo = new TxDaily();
				
				vo.setOi(rs.getBigDecimal("OI"));
				vo.setLast(rs.getBigDecimal("LAST"));
				vo.setSettlePrice(rs.getBigDecimal("SETTLE_PRICE"));
				vo.setChange(rs.getBigDecimal("CHANGE"));
				
				r.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
		
	}
	
	public List<TxMiTrader> getMIData(Date date){
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT *");
		sql.append(" FROM tx_mi_trader");
		sql.append(" WHERE 1=1");
		sql.append(" AND data_date = ?");
		sql.append(" AND trader = '外資及陸資'");
		
		ArrayList<TxMiTrader> r = new ArrayList<TxMiTrader>();
		
		try {
			
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setDate(1, new java.sql.Date(date.getTime()));
			
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
			
				TxMiTrader vo = new TxMiTrader();
				
				vo.setlOi(rs.getLong("L_OI"));
				vo.setsOi(rs.getLong("S_OI"));
				vo.setlOiVal(rs.getLong("L_OI_VAL"));
				vo.setsOiVal(rs.getLong("S_OI_VAL"));
				vo.setNetOi(rs.getLong("NET_OI"));
				vo.setNetOiVal(rs.getLong("NET_OI_VAL"));
				
				r.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
		
	}
	
	public List<TxMiTrader> get(Date date){
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT *");
		sql.append(" FROM tx_mi_trader");
		sql.append(" WHERE 1=1");
		sql.append(" AND data_date = ?");
		sql.append(" AND trader = '外資及陸資'");
		
		ArrayList<TxMiTrader> r = new ArrayList<TxMiTrader>();
		
		try {
			
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setDate(1, new java.sql.Date(date.getTime()));
			
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
			
				TxMiTrader vo = new TxMiTrader();
				
				vo.setlOi(rs.getLong("L_OI"));
				vo.setsOi(rs.getLong("S_OI"));
				vo.setlOiVal(rs.getLong("L_OI_VAL"));
				vo.setsOiVal(rs.getLong("S_OI_VAL"));
				vo.setNetOi(rs.getLong("NET_OI"));
				vo.setNetOiVal(rs.getLong("NET_OI_VAL"));
				
				r.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
		
	}
	
}

package entity;

import java.util.Date;

public class TxLTrader {

	private Date dataDate;
	
	private String contract;
	
	private String settlement;
	
	private String conCname;
	
	private String description;
	
	private String trader;
	
	private long top5Buy;
	
	private long top5Sell;
	
	private long top10Buy;
	
	private long top10Sell;
	
	private long oi;

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public String getConCname() {
		return conCname;
	}

	public void setConCname(String conCname) {
		this.conCname = conCname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}



	public long getTop5Buy() {
		return top5Buy;
	}

	public void setTop5Buy(long top5Buy) {
		this.top5Buy = top5Buy;
	}

	public long getTop5Sell() {
		return top5Sell;
	}

	public void setTop5Sell(long top5Sell) {
		this.top5Sell = top5Sell;
	}

	public long getTop10Buy() {
		return top10Buy;
	}

	public void setTop10Buy(long top10Buy) {
		this.top10Buy = top10Buy;
	}

	public long getTop10Sell() {
		return top10Sell;
	}

	public void setTop10Sell(long top10Sell) {
		this.top10Sell = top10Sell;
	}

	public long getOi() {
		return oi;
	}

	public void setOi(long oi) {
		this.oi = oi;
	}
	
	
}

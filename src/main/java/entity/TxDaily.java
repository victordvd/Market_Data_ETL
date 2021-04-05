package entity;

import java.math.BigDecimal;

public class TxDaily {

	private java.util.Date rptDate;

	private String contract;

	private String contractMon;
	
	private BigDecimal change;

	private String changePercent;

	private BigDecimal high;

	private BigDecimal last;

	private BigDecimal low;

	private BigDecimal oi;

	private BigDecimal open;

	private BigDecimal settlePrice;

	private String tradingSession;
	
	private BigDecimal volumn;
	
	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public java.util.Date getRptDate() {
		return rptDate;
	}

	public void setRptDate(java.util.Date rptDate) {
		this.rptDate = rptDate;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getContractMon() {
		return contractMon;
	}

	public void setContractMon(String contractMon) {
		this.contractMon = contractMon;
	}

	public BigDecimal getChange() {
		return this.change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public String getChangePercent() {
		return this.changePercent;
	}

	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}

	public BigDecimal getHigh() {
		return this.high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLast() {
		return this.last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public BigDecimal getLow() {
		return this.low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getOi() {
		return this.oi;
	}

	public void setOi(BigDecimal oi) {
		this.oi = oi;
	}

	public BigDecimal getOpen() {
		return this.open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getSettlePrice() {
		return this.settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public String getTradingSession() {
		return this.tradingSession;
	}

	public void setTradingSession(String tradingSession) {
		this.tradingSession = tradingSession;
	}

}
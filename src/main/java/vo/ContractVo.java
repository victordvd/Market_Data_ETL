package vo;

import java.util.List;

public class ContractVo {

	private String contract;
	
	private String settlementMonth;
	
	private String typeOfTraders;
	
	private List<OpenInterestVo> data;

	

	public ContractVo(String contract, String settlementMonth, String typeOfTraders, List<OpenInterestVo> data) {
		super();
		this.contract = contract;
		this.settlementMonth = settlementMonth;
		this.typeOfTraders = typeOfTraders;
		this.data = data;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getSettlementMonth() {
		return settlementMonth;
	}

	public void setSettlementMonth(String settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	public List<OpenInterestVo> getData() {
		return data;
	}

	public void setData(List<OpenInterestVo> data) {
		this.data = data;
	}

	public String getTypeOfTraders() {
		return typeOfTraders;
	}

	public void setTypeOfTraders(String typeOfTraders) {
		this.typeOfTraders = typeOfTraders;
	}
	
	
	
}

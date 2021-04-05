package query;

import java.text.ParseException;

import service.DataAnalyzer;

public class DataViewer {

	public static void main(String[] args) throws ParseException {

		DataAnalyzer da = new DataAnalyzer();
		
		da.getFrontOIDif("20171114");
		

	}

}

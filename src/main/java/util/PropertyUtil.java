package util;

import java.util.ResourceBundle;

public class PropertyUtil {
	
	static ResourceBundle rb = ResourceBundle.getBundle("market_data");
	
	public static String getValue(String key){
		
		return rb.getString(key);
	}
	
	
	
	
	

}

package apihelper;

import java.util.HashMap;
import java.util.Map;

public class APIParams {
	
	private Map<String, String> httpParams = new HashMap<String, String>();

	public Map<String, String> getHttpParams() {
		return httpParams;
	}

	public void setHttpParams(Map<String, String> httpParams) {
		this.httpParams = httpParams;
	}
	
	public void addHttpParams(Map<String, String> httpParams) {
		this.httpParams = httpParams;
	}
	
	public void addParams(String key,String value)
	{
		httpParams.put(key, value);
	}

}

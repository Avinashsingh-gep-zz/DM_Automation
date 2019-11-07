package apihelper;

import java.util.HashMap;
import java.util.Map;

public class APIHeaders {
	
	private Map<String, String> headers = new HashMap<String, String>();

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeaders(Map<String, String> headers){
		this.headers = headers;
	}
	
	public void addHeader(String key,String value)
	{
		headers.put(key, value);
	}

}

package apihelper;

import java.util.HashMap;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class APIRequest {
	
	Map<String, String> headers = new HashMap<String, String>();
	Map<String, String> params = new HashMap<String, String>();
	Object jsonBody;
	
	public APIRequest(APIHeaders headers)
	{
		this.headers = headers.getHeaders();
	}
	public APIRequest(APIParams params)
	{
		this.params = params.getHttpParams();
	}
	public APIRequest(APIHeaders headers,APIParams params)
	{
		this.headers = headers.getHeaders();
		this.params = params.getHttpParams();
	}
	public APIRequest(APIHeaders headers,APIParams params,Object jsonBody)
	{
		this.headers = headers.getHeaders();
		this.params = params.getHttpParams();
		this.jsonBody = jsonBody;
	}
	public APIRequest(APIHeaders headers,Object jsonBody)
	{
		this.headers = headers.getHeaders();
		this.jsonBody = jsonBody;
	}
	public APIRequest(APIParams params,Object jsonBody)
	{
		this.params = params.getHttpParams();
		this.jsonBody = jsonBody;
	}
	public APIRequest(Object jsonBody)
	{
		this.jsonBody = jsonBody;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public RequestSpecification getRequestSpec()
	{
		RequestSpecBuilder builder = new RequestSpecBuilder();
		EncoderConfig ec = new EncoderConfig();
        ec.appendDefaultContentCharsetToContentTypeIfUndefined(false);
		if(!params.isEmpty())
		{
			builder.addParams(params);
		}
		if(!headers.isEmpty())
		{
			builder.addHeaders(headers);
		}
		if(null!=jsonBody){
			builder.setBody(jsonBody);
			builder.setContentType(ContentType.JSON);
			builder.setConfig(RestAssured.config().encoderConfig(ec.appendDefaultContentCharsetToContentTypeIfUndefined(false)).sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation("TLS")));
			
			//builder.setUrlEncodingEnabled(false);
		}
		
		return builder.build();
	}

}

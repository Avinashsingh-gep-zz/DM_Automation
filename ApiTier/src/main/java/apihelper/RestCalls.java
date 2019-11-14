package apihelper;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestCalls {
	
	private RequestSpecificationImpl spec;

	static RestCalls restCalls = null;

	public String responseAsString;
	public static String URLPath = System.getProperty("user.dir")+File.separator + "jsonFile"+File.separator+"country.json";

	public static RequestSpecBuilder requestSpecBuilder;
	public static RequestSpecification requestSpecification;

	public static ResponseSpecBuilder responseSpecBuilder;
	public static ResponseSpecification responseSpecification;

	//	public static RequestSpecBuilderCustomized requestSpecBuilderCustomized;
	//public static FilterableRequestSpecification filterableRequestSpecification;
	public enum APIMethodType {
		GET,
		POST,
		PUT,
		FILEPOST,
		DELETE;
	}

	public static RestCalls getRestCalls(){
		if(restCalls!=null)
		{
			return restCalls;
		}else{
			return new RestCalls();
		}
	}

	/**
	 *  Function to create the request specification depending upon on the type of the request.
	 * @param post  :- The type of the request (i.e. post/patch/get/delete
	 * @param body :- The Json Request Body
	 * @param headersToAdd :- The key-value pair of additional headers which are specific to the individual request and are not covered in below function.  
	 * @return
	 * @throws IOException 
	 */

	public RequestSpecification getGenericRequestSpec(APIMethodType requestType,String body,Map<String, String> headersToBeModified , String BasicToken) throws IOException{
		//requestSpecBuilder.addParam ("query", "restaurants in mumbai");
		requestSpecBuilder = new RequestSpecBuilder();
		//requestSpecBuilderCustomized = new RequestSpecBuilderCustomized();

		//filterableRequestSpecification = new FilterableRequestSpecification();

		EncoderConfig ec = new EncoderConfig();
		ec.appendDefaultContentCharsetToContentTypeIfUndefined(false);

		// Setting headers for the XML Payload
		if(body.startsWith("<?xml version="))
		{
			requestSpecBuilder.addHeader("Content-Type","text/xml");

		}
		else {
			// Setting DEFAULT headers for Json Payload
			requestSpecBuilder.setContentType(ContentType.JSON);
			requestSpecBuilder.addHeader("Accept", "application/json");
			requestSpecBuilder.setConfig(RestAssured.config().encoderConfig(ec.appendDefaultContentCharsetToContentTypeIfUndefined(false)).sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation("TLS")));
			//	requestSpecBuilder.addHeader("Authorization",BaseTest.tokenGenerated);
			requestSpecBuilder.addHeader("Access-Control-Allow-Origin", "*");
			if(BasicToken != null && !BasicToken.isEmpty())
			{


				String ValOrigin = PropertyReader.readConfig(ConfigurationProperties.Origin);
				String ValUserExecutionContext = PropertyReader.readConfig(ConfigurationProperties.UserExecutionContext);
				String ValOcpApimSubscriptionKey = PropertyReader.readConfig(ConfigurationProperties.OcpApimSubscriptionKey);
				String ValReferer = PropertyReader.readConfig(ConfigurationProperties.Referer);
				String ValUserAgent = PropertyReader.readConfig(ConfigurationProperties.UserAgent);

				requestSpecBuilder.addHeader("Authorization",BasicToken);
				requestSpecBuilder.addHeader("Origin", ValOrigin);
				requestSpecBuilder.addHeader("UserExecutionContext", ValUserExecutionContext);
				requestSpecBuilder.addHeader("Ocp-Apim-Subscription-Key", ValOcpApimSubscriptionKey);
				requestSpecBuilder.addHeader("Referer", ValReferer);
				requestSpecBuilder.addHeader("User-Agent", ValUserAgent);


			}

		}

		if(requestType.equals("POST"))
		{
			//TODO :- Here add the headers which would be common for all the put requests within project 
			//requestSpecBuilder.addHeader("Access-Control-Allow-Origin", "*");

		}
		else if(requestType.equals("GET"))
		{
			//TODO :- Here add the headers which would be common for all the put requests within project 

		}

		else if(requestType.equals("PUT"))
		{
			//TODO :- Here add the headers which would be common for all the put requests within project 

		}
		else if(requestType.equals("DELETE"))
		{
			//TODO :- Here add the headers which would be common for all the put requests within project 

		}



		// Code for modifying the default headers which are specific to the individual requests.
		if(headersToBeModified.size() > 0)
		{
			RequestSpecification  modifiedrequestSpecification = modifyDefaultHeaders(headersToBeModified, requestSpecBuilder, requestSpecification , body );
			return modifiedrequestSpecification;
		} 
		requestSpecBuilder.setBody(body);
		requestSpecification = requestSpecBuilder.build();
		return requestSpecification;
	}

	/**
	 * // Code for modifying the default headers which are specific to the individual requests
	 * @param headersToBeModified  :- The 'Key - Value' map of headers which needs to be modified
	 * @param requestSpecBuilder  :- The existing requestSpecBuilder which contains the default headers added.
	 * @param post  :-
	 * @param post  :-
	 */
	public static RequestSpecification modifyDefaultHeaders(Map<String, String> headersToBeModified, RequestSpecBuilder requestSpecBuilder,RequestSpecification requestSpecification , String body )
	{
		try {
			if(headersToBeModified.size() > 0)
			{
				// Iterating through the values to add other headers provided by user. 
				//headersToAdd.forEach((key,vaule) -> requestSpecBuilderCustomized.addHeader(key,vaule)); 

				// Removing the 'DEFAULT' headers which user wants to remove and has provided as parameters prefixed with 'REMOVE##' keyword. Eg:- 'REMOVE##Authorization'
				//RequestSpecBuilderCustomized customRequestSpecBuilder = ""
				for (Entry<String, String> entrySet : headersToBeModified.entrySet()) {
					String key = entrySet.getKey();
					// Performing the modification operations on the default headers.
					if (key.toUpperCase().startsWith("REMOVE##") || key.toUpperCase().startsWith("REPLACE##")) {
						String[] headersToModify = key.split("##");
						requestSpecBuilder.setBody(body);
						requestSpecification = requestSpecBuilder.build();
						//		RequestSpecBuilderCustomized customRequestSpecBuilder = new RequestSpecBuilderCustomized(requestSpecification); 

						// Removing one of the default headers that the user has prefixed with 'REMOVE##' keyword.
						//Eg:- 'REMOVE##Authorization##NewValue --> This will remove the existing header named as 'Authorization' 
						if (key.toUpperCase().startsWith("REMOVE##"))
						{
							//requestSpecification = customRequestSpecBuilder.removeHeaderFromSpec(headersToModify[1]);
						}
						// Replacing the values of the default headers with the user defined values that user has provided as parameters prefixed with 'REPLACE##' keyword. '
						//Eg:- 'REPLACE##Authorization##NewValue --> This will replace the existing value of 'Authorization' with the 'NewValueOfAuthorizationToBeReplaced' 
						/*else if (key.toUpperCase().startsWith("REPLACE##"))
							{
								requestSpecification = customRequestSpecBuilder.replaceHeaderFromSpecs(headersToModify[1], entrySet.getValue());
							}*/
						return requestSpecification;
					}
					else {
						//requestSpecBuilder.addHeader(entrySet.getKey(), entrySet.getValue());
						requestSpecBuilder.setBody(body);
						requestSpecBuilder.addHeader(entrySet.getKey(), entrySet.getValue());
						requestSpecification = requestSpecBuilder.build();
						//requestSpecification = customRequestSpecBuilder.addHeader(entrySet.getKey(), entrySet.getValue());
					}
				}
			} 
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return requestSpecification;
	}

	/**
	 *  Function to create the request specification(with no pre-defined headers) depending upon on the type of the request.
	 * @param post  :- The type of the request (i.e. post/patch/get/delete) 
	 * @param body :- The Json Request Body
	 * @param headersToAdd :- The key-value pair of additional headers which are specific to the individual request and are not covered in below function.  
	 * @return
	 */

	public static RequestSpecification getUserDefinedRequestSpec(APIMethodType requestType,String body,Map<String, String> headersToAdd){
		//requestSpecBuilder.addParam ("query", "restaurants in mumbai");
		//requestSpecBuilder = new RequestSpecBuilder();
		//		requestSpecBuilderCustomized = new RequestSpecBuilderCustomized();

		// Setting content type
		EncoderConfig ec = new EncoderConfig();
		ec.appendDefaultContentCharsetToContentTypeIfUndefined(false);
		//	requestSpecBuilderCustomized.setContentType(ContentType.JSON);
		//	requestSpecBuilderCustomized.setConfig(RestAssured.config().encoderConfig(ec.appendDefaultContentCharsetToContentTypeIfUndefined(false)).sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation("TLS")));

		// Below lines are for adding the additional headers which are specific to the individual requests.
		if(headersToAdd.size() > 0)
		{
			// ITERATING THROUGH THE VALUES OF THE MAP USING LEMBDA EXPRESSIONS.
			//		headersToAdd.forEach((key,vaule) -> requestSpecBuilderCustomized.addHeader(key,vaule)); 
		} 
		//	requestSpecBuilderCustomized.setBody(body);
		//	requestSpecification = requestSpecBuilderCustomized.build();
		return requestSpecification;
	}


	//public APIResponse sendRequest(Enum<APIMethodType> method, String path, APIRequest requestSpec)
	public APIResponse createRequest(Enum<APIMethodType> method, String path, RequestSpecification requestSpec)
	{
		return toAPIResponse(sendRequest(method, path, requestSpec));
	}

	//private Response doRequest(Enum<APIMethodType> method, String path, APIRequest requestSpec)
	public Response sendRequest(Enum<APIMethodType> method, String path, RequestSpecification requestSpec)
	{
		String urlPath;
		Response responseReceived = null;
		try {
			urlPath = path;
			System.out.println("## Endpoint URL formed ===> "+urlPath);

			if(method.equals(APIMethodType.GET))
			{
				//responseReceived = RestAssured.given().urlEncodingEnabled(false).spec(requestSpec).when().get(urlPath);
				responseReceived = RestAssured.given().spec(requestSpec).when().get(urlPath);
			}else if(method.equals(APIMethodType.POST))
			{
				//return given().spec(requestSpec.getRequestSpec()).when().post(urlPath);
				responseReceived = RestAssured.given().spec(requestSpec).when().post(urlPath);
			}else if(method.equals(APIMethodType.PUT))
			{
				//return given().spec(requestSpec.getRequestSpec()).when().put(urlPath);
				responseReceived = RestAssured.given().spec(requestSpec).when().put(urlPath);
			}
			else if(method.equals(APIMethodType.DELETE))
			{
				//return given().spec(requestSpec.getRequestSpec()).when().put(urlPath);
				responseReceived = RestAssured.given().spec(requestSpec).when().delete(urlPath);
			}
			else if(method.equals(APIMethodType.FILEPOST))
			{
				responseReceived = RestAssured.given().spec(requestSpec).auth().basic("nehamagguapi", "123qa123!").when().post(urlPath);
			}

			// Setting the variable value as blank to remove any previous iteration's assigned values. 
			if(responseAsString!="" || responseAsString!=null)
			{
				responseAsString = "";
			}
			responseAsString = responseReceived.asString();
			System.out.println(responseAsString);
		}
		catch(Exception ex)
		{
			System.out.println("Exception found in sending the request to the server is ===> " + ex);
			responseAsString = "";
		}
		return responseReceived;
	}

	private APIResponse toAPIResponse(Response resp)
	{
		APIResponse apiResponse = new APIResponse(resp.body().asString()); // or resp.getbody().asString()
		apiResponse.setStatusCode(resp.getStatusCode());
		apiResponse.setReasonPhrase(resp.getStatusLine());
		apiResponse.setContentType(resp.getContentType());
		apiResponse.setResponseHeaders(resp.getHeaders().asList());
		apiResponse.setResponseCookies(resp.getCookies());
		apiResponse.setResponseJsonPath(resp.getBody().jsonPath());
		apiResponse.setResponse(resp);
		return apiResponse;
	}

	/**
	 * 
	 * @param responseDetails :- This is the list that contains the data in the form of parameterToVerify(i.e.Description) , expectedValue, pathToTheParameter(i.e.Actual Value) 
	 * @return
	 */
	public boolean validateResponseDetails(List<String> responseDetails){
		boolean verificationResult = true;
		for(String individualData : responseDetails)
		{
			String[] verificationData = individualData.split(",");
			//verificationResult = dataIsEqual(verificationData[0], verificationData[1], verificationData[2]);
			if(!dataIsEqual(verificationData[0], verificationData[1], verificationData[2]))
			{
				verificationResult = false;
			}
		}
		return verificationResult;
	}


	/**
	 * 
	 * @param responseDetails :- This is the list that contains the data in the form of parameterToVerify(i.e.Description) , expectedValue, pathToTheParameter(i.e.Actual Value) 
	 * @return
	 * The functional is to be used in below form in the test class
	 * Eg:-  verificationData.add("responseCode ###"+expErrorCode+"###"+ responseData.getStatusCode());
	 * Note that the below functions is to be used when there are special caracters or multiple commas in 
	 * 												in the 'expectedErrorCode' and the 'actualErrorCode'
	 */
	public boolean validateResponseContainingSpecialChars(List<String> responseDetails){
		boolean verificationResult = true;
		for(String individualData : responseDetails)
		{
			String[] verificationData = individualData.split("###");
			//verificationResult = dataIsEqual(verificationData[0], verificationData[1], verificationData[2]);
			if(!dataIsEqual(verificationData[0], verificationData[1], verificationData[2]))
			{
				verificationResult = false;
			}
		}
		return verificationResult;
	}

	/**
	 * <pre>
	 * Function to compare two values.*
	 * </pre>
	 * <P/>
	 * @return true if actual and expected matches else false
	 * @example isEqual( "Actual Value", "Expected Value","*** Comparing Values
	 *          ***")
	 */
	public boolean dataIsEqual( String Description, String ExpectedValue , String ActualValue) {
		try {

			if (ActualValue.trim().equals(ExpectedValue.trim())) {
				System.out.println("Pass : Actual Value - " + ActualValue + " matches with the expected value - " + ExpectedValue + ".");
				//Assert.assertEquals(ActualValue, ExpectedValue);

				return true;
			} else {
				System.out.println("Fail : Actual Value - " + ActualValue + " doesnot matches with the expected value - " + ExpectedValue + ".");
				//Assert.assertEquals(ActualValue, ExpectedValue);

				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getResposeString(Response response){
		String strResponse = "";
		try {

			strResponse = response.getBody().asString();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return strResponse;
	}

	public static JsonPath jsonParser(String response){

		JsonPath jsonResponse = new JsonPath(response);

		return jsonResponse;
	}


	public static XmlPath xmlParser(String response){
		try {

			XmlPath xmlResponse = new XmlPath(response);

			return xmlResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Function to fecth the payload from the xml file and convert it to string.
	 * **/
	public static String xml_convertXmlPayloadToString(String filename) throws IOException{
		String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public HashMap<String, String> getResponsedata(APIResponse responseData, String Uniqueid) {
		HashMap ResponseData = new HashMap<String, String>();

		try{
			ResponseData.put("StatusCode", responseData.getStatusCode());
			ResponseData.put("uniqueid", responseData.getResponseJsonPath().get(Uniqueid));
			ResponseData.put("Status", responseData.getReasonPhrase());
		}
		catch(Exception e)
		{
			System.out.println("The Response data is not computed "+e);
		}
		return ResponseData;
	}

	public HashMap<String, String> getResponsedata(APIResponse responseData) {
		HashMap ResponseData = new HashMap<String, String>();

		try{
			int Statuscode = responseData.getStatusCode();
			String code =Integer.toString(Statuscode);
			ResponseData.put("StatusCode", code);
			ResponseData.put("Status", responseData.getReasonPhrase());
		}
		catch(Exception e)
		{
			System.out.println("The Response data is not computed "+e);
		}
		return ResponseData;
	}

}



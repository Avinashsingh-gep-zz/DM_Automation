package apihelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SampleApiReadJsonFile {
	/**
	 * Read json.
	 *
	 * @param jsonFilePath the json file path
	 * @return the json node
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JsonNode readJson(String jsonFilePath) throws IOException {
		JSONParser parser = new JSONParser();
		Object obj;
		JsonNode rootNode = null;
		BufferedReader in = null;
		try {
			FileInputStream inputFile = new FileInputStream(
					new File(jsonFilePath));
			in = new BufferedReader(
					new InputStreamReader(inputFile, "ISO-8859-1"));
			obj = parser.parse(in);
			String jsonString = obj.toString();
			rootNode = new ObjectMapper().readTree(new StringReader(jsonString));
		} catch (Exception ex) {
			System.out.println("Exception while reading Json file : " + ex.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return rootNode;
	}
	
	public static String readJsonFile(String jsonFilePath, String Environment, String Client, String App) throws IOException {
		String Value = null;
		Object obj;
		BufferedReader in = null;
		try {
			FileInputStream inputFile = new FileInputStream(
					new File(jsonFilePath));
			in = new BufferedReader(
					new InputStreamReader(inputFile, "ISO-8859-1"));
			
			obj = new JSONParser().parse(in);
			JSONObject jo = (JSONObject) obj;			
			Map address1 = ((Map) jo.get(Client));
			Map address = (Map) address1.get(Environment);
			Iterator<Map.Entry> itr1 = address.entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();				
				if (pair.getKey().toString().equalsIgnoreCase(App)) {
					Value = pair.getValue().toString();
					break;
				}
			}
		} catch (Exception ex) {
			System.out.println("Exception while reading Json file : " + ex.getMessage());
		}
		 {
				if (in != null) {
					in.close();
				}
			}
		
		return Value;
	}
	
	public static String JSONtoString(String jsonFilePath) throws IOException {
		String payload = null;
		Object obj;
		try {
			obj = new JSONParser().parse(new FileReader(jsonFilePath));
			JSONObject jo = (JSONObject) obj;
			payload = jo.toString();
			
		} catch (Exception ex) {
			System.out.println("Exception while reading Json file : " + ex.getMessage());
		}
		return payload;
	}

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI ="https://payments.testing.digital.travelex.net/v1/remittance";
		RequestSpecification request = RestAssured.given();

		// read from .json file
		ObjectNode requestJson = (ObjectNode) new SampleApiReadJsonFile().readJson("src/test/java/jsonFile/country.json");
		
		request.body(requestJson.toString());

		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.header("X-Auth", "BASIC Y3NjLXRlc3RzOmlhNHVWMUVlS2FpdA==");
		request.header("Accept", "application/json");

		// Add the JSON to the body of the request
		request.body(requestJson.toString());

		// Post the request and check the response
		Response response = request.post("/getCountryDetails");

		System.out.println(response.body().prettyPrint());
		
		int statusCode = response.getStatusCode();
		String statusLine = response.getStatusLine();
		System.out.println("\nStatusCode : " + statusCode + "\nMessage : " + statusLine);
		
	}
}

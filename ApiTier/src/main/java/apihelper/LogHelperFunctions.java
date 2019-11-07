/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apihelper;

import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.http.Header;

/**
 *
 * 
 */
public class LogHelperFunctions 
{
	public  static String S3Path = "https://s3.amazonaws.com/gfetool-logs/";
	public final static boolean s3Flag = false;
	public  static String projectPath = System.getProperty("user.dir");
	public  static String resultDirName = "Test_Results";
	public  static String requestResponseLogsFolderName = "Test_Payloads";


	/**
	 * <p>
	 * The function is to format the JSON string in proper JSON format and will return it.
	 * @param json String JSON which needed to be formatted.
	 * @return prettyJSON File String
	 * @throws IOException 
	 */
	public  static String printPrettyJsonFromString( String json ) throws IOException
	{
		Object jsonObject;
		String prettyJson = null;
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try 
		{
			jsonObject = mapper.readValue(json, Object.class);    
			prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
			System.out.println(prettyJson);
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();    
		}
		return prettyJson;
	}


	/**
	 * <p>
	 * This Function will write the JSON to a file. It will be a .txt file.
	 *  @param json String JSON Response or Request that need to be written to a text file.
	 * @param fileName Name of the text file.
	 * @param buildNumber Build number which corresponds to the Jenkins Build and will read from pom system variables. 
	 * @return String HTML File path for the text file created.
	 */
	public static String  writeJSONFile( String json, String fileName, String buildNumber )
	{
		String prettyJson;
		String htmlFilePath = null;
		try
		{
			prettyJson = printPrettyJsonFromString( json );

			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
			String currentDateTime = dateFormat.format(new Date());

			//htmlFilePath = S3Path + buildNumber + "/" + "test-output/RequestResponseLogs/" + fileName+ "_"+ currentDateTime +".txt";
			htmlFilePath = S3Path + buildNumber + "/" + resultDirName +"/RequestResponseLogs/" + fileName+ "_"+ currentDateTime +".txt";
			//String filePath = "./test-output/RequestResponseLogs/" + fileName+ "_"+ currentDateTime +".txt";
			String filePath = System.getProperty("user.dir") +"/" + requestResponseLogsFolderName + fileName+ "_"+ currentDateTime +".txt";
			

			System.out.println(filePath);
			File oResponseInputFile = new File( filePath );
			try ( FileOutputStream fileOutputStream = new FileOutputStream( oResponseInputFile, true ); 
					PrintWriter printWriter = new PrintWriter( fileOutputStream )) 
			{
				printWriter.write( prettyJson );
			}

		}
		catch (IOException e)
		{
			e.getMessage();
		}
		return htmlFilePath;
	}
	/**
	 * <p>
	 * This Function will write the JSON to a file but it has a extra Boolean flag parameter.
	 * If this boolean flag parameter is set as false then execution request and response logs will be displayed in the HTML file 
	 * locally. If it is set as true request and response logs will be displayed from the S3 bucket in the HTML file.
	 * @param json String JSON Response or Request that need to be written to a text file.
	 * @param fileName Name of the text file.
	 * @param buildNumber Build number which corresponds to the Jenkins Build and will read from pom system variables.
	 * @param flag boolean flag so as to switch on or off to store the request and response logs in S3 bucket.
	 * @return String HTML File path for the text file created.
	 */
	public static String  writeJSONFile( String json, String fileName,  boolean flag )
	{
		String prettyJson;
		String htmlFilePath = null;
		//String relativeLogPath = null;
		try
		{
			prettyJson = printPrettyJsonFromString( json );

			Date oDate = new Date();
			SimpleDateFormat formatter =  new SimpleDateFormat( "yyyy-MM-dd'T'HHmmss.SSS" );
			String currentDateTime = formatter.format( oDate );

			//String filePath = projectPath + "/" + resultDirName + "/" + requestResponseLogsFolderName + "/" + fileName  + "_" + currentDateTime + ".txt";
			String filePath = projectPath + "/" + requestResponseLogsFolderName + "/" + fileName  + "_" + currentDateTime + ".txt";
			if (flag == true )            
				htmlFilePath = S3Path +  "/" + requestResponseLogsFolderName + "/" + fileName + "_" + currentDateTime + ".txt";

			else
				htmlFilePath = filePath;
			System.out.println(htmlFilePath);
			File oResponseInputFile = new File( filePath );
			try ( FileOutputStream fileOutputStream = new FileOutputStream( oResponseInputFile, true ); 
					PrintWriter printWriter = new PrintWriter( fileOutputStream )) 
			{
				printWriter.write( prettyJson );
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}
		return htmlFilePath;
	}

	public String addJsonDetailsToReport(String json, String responsePayload, String fileName,   boolean flag)
	{
		String payloadPath = null;
		try {
			System.out.println("json report creation for report");
			try {
				//String payloadPath = logHelperFunctions.writeJSONFile( payload, "Post_Request", buildNumber, s3Flag );
				payloadPath = writeJSONFile( json, fileName ,  flag );
			//	TestRunner.extentTest.log(Status.INFO, "Request Payload is ===> <a href= " + payloadPath + ">Click here to see the Request payload.</a>");
				//Reporter.addStepLog("Request Payload is ===> <a href= " + payloadPath + ">Click here to see the Request payload.</a>");
			} catch(Exception e) {}
			System.out.println(responsePayload);
			String resFilePath = writeJSONFile(responsePayload, "Post_Response",  s3Flag );
		//	TestRunner.extentTest.log(Status.INFO, "Response payload is ===> <a href= " + resFilePath + ">Click here to see the Response.</a>");	
		}
		catch(Exception e)
		{
			System.out.println("There was exception in creating json file for reports log." );
			e.printStackTrace();
		//	TestRunner.extentTest.log(Status.INFO, "There was exception in creating json file for reports log.");
		}
		return payloadPath;
	}
	/**
	 * <p>
	 * Function to log a HTML Link in the Execution Report.
	 * @param oTest ExtentTest Object to which link need to be logged.
	 * @param testStepName String test step name in the test case.
	 * @param filePath String file path of the HTML Execution report file.
	 */    
	/*public static void logLink(String requestPayload, String responsePayload, String jsonDataFileName, String buildNumber, boolean flag )
	{
		try
		{
			String payloadPath = logHelperFunctions.writeJSONFile(requestPayload, jsonDataFileName , buildNumber, flag );
			BaseTest.extentTest.log(Status.INFO, "Post_Request Payload is ===> <a href= " + payloadPath + ">Click here to see the Request payload.</a>");
			String resFilePath = logHelperFunctions.writeJSONFile(responsePayload, "jsonDataFileName", buildNumber, s3Flag );
			//String resFilePath = logHelperFunctions.writeJSONFile(restCalls.responseAsString, "Post_Report", buildNumber, s3Flag );
			BaseTest.extentTest.log(Status.INFO, "Post_Response payload is ===> <a href= " + resFilePath + ">Click here to see the Response.</a>"); 
		}
		catch (Exception e)
		{
			e.getMessage();
		}
	}*/
	
	public static String  writeJSONFormat( String json )
	{
		String prettyJson = null;
	
		try
		{
			prettyJson = printPrettyJsonFromString( json );
		}
		catch(Exception e)
		{
			System.out.println("There was exception in creating json file for reports log." + e);
		}
		return prettyJson;
	}

	
}
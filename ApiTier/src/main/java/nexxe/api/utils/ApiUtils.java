package nexxe.api.utils;

import static io.restassured.RestAssured.given;

import java.util.Optional;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import nexxe.api.dto.requestDTO.AuthenticationRequestDTO;

public class ApiUtils {

	private static Response resp;
	private static String accessToken;

	public static Object retriveResponse(Response response, Object className) {
		Gson gson = new Gson();
		return gson.fromJson(response.getBody().asString(), className.getClass());
	}

	public static String getAccessToken(String userName, String buyerPartnerCode, String partnerCode, String url) {

		AuthenticationRequestDTO newBlog = new AuthenticationRequestDTO().setBpc(buyerPartnerCode)
				.setPartnerCode(partnerCode).setUserName(userName);

		EncoderConfig ec = new EncoderConfig();
		ec.appendDefaultContentCharsetToContentTypeIfUndefined(false);
		RequestSpecification requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Accept", "application/json").addHeader("Access-Control-Allow-Origin", "*")
				.setConfig(RestAssured.config()
						.encoderConfig(ec.appendDefaultContentCharsetToContentTypeIfUndefined(false))
						.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation("TLS")))
				.setBody(newBlog).build();

		resp = given().spec(requestSpec).when().post(url);
		System.out.println(resp.getBody());
		accessToken = resp.getBody().jsonPath().get("IssueJWTTokenResult.securityJWTToken");
		return accessToken;
	}

	Object defaultBody = null;
	Optional<Object> body = Optional.ofNullable(defaultBody);

	public static Response getGenericRequest(API_Verbs APIMethodType, Object baseUrl, String url,
			Optional<Object> body) {
		String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6Imh5ektyUkgzRFNZTzkwN2lGbThEalBxYmxlUSIsIng1dCI6Imh5ektyUkgzRFNZTzkwN2lGbThEalBxYmxlUSIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IkNoZXZyb25XYXJlaG91c2VDbGVyayIsImh0dHA6Ly93d3cuZ2VwLmNvbS9jLzIiOiI0MDEwMDAwMyw0MDEwMDAwNCIsImh0dHA6Ly93d3cuZ2VwLmNvbS9jLzEzIjoiNDgwMzM1IiwiaHR0cDovL3d3dy5nZXAuY29tL2MvNCI6IjcwMDIxNzA0MDQwMDEwODEiLCJodHRwOi8vd3d3LmdlcC5jb20vYy8xNSI6IjcwMDIxNzA0IiwiaHR0cDovL3d3dy5nZXAuY29tL2MvMTYiOiJlbi1VUyIsImh0dHA6Ly93d3cuZ2VwLmNvbS9jLzE3IjoiMSIsImh0dHA6Ly93d3cuZ2VwLmNvbS9jLzE4IjoiNjAwIiwiaHR0cDovL3d3dy5nZXAuY29tL2MvMTkiOiIxMTUuMTEwLjE3OS4yMzQiLCJodHRwOi8vd3d3LmdlcC5jb20vYy8yMSI6IjAiLCJodHRwOi8vd3d3LmdlcC5jb20vYy8yMiI6IjAiLCJodHRwOi8vd3d3LmdlcC5jb20vYy8yMyI6IkNoZXZyb24iLCJodHRwOi8vd3d3LmdlcC5jb20vYy8yNCI6Im56UFF2T1hzaHplRFJ4d0RQT29DU1Z3Q3lidnhGNnhYNTQxekJsMVZWbEdXRXNRbHFjR3RtVXh3cHFTQ01EUUM1c1ZEc1UwZXN4cExWWHBHaDNkR3dnPT0iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiVUQxSFJWQk1iMmRwYmlaVlNVUTlRMmhsZG5KdmJsZGhjbVZvYjNWelpVTnNaWEpySmxOSlJEMG1Va1ZHUFY5bE16WmhOMlF3WW1SbE5EWTFZVFExWVdGbVpnMiIsImp0aSI6IjQ4NzU3YzkzLTRiODYtNDAyYS05MTk2LTkxNGIxNGExZjQ0YiIsIm5iZiI6MTU3MzcxNTMxNCwiZXhwIjoxNTczNzUxMzE0LCJpYXQiOjE1NzM3MTUzMTQsImlzcyI6Imh0dHBzOi8vc21hcnRkZXYtc3RzLmdlcC5jb20vIiwiYXVkIjoiWGh2VTZRTmY5eHpDSTEzV212cXhuc1FyWngxcFVlRjVwNEpKNkk0QnVvY0pWcVRuQ2ZKblRqTDdDbDk1N3pIV1lQNUlMTGhJN0RReGl5QWYySys0WFN4TjRZU05OZHVBeWt0OFZOdUxEdlE9In0.sShvSAn-Pn1SyFxxtZZ_UAc6enSdC0Bw-uHzfhJHfLLKAqoMFCkoGMNHAjwLpbsFcTxsFtj7bmQ5QANnfFzyd6tMIhBJsfOB1lnbn7LlGcyoNukPr9apzVFkwFjjRcipg9Ug17WxJneIVO8cnpWU_Wk04DPFV3pYju_aNHBqngZviF_xabNO-wBQszP2OI5Tf3ZxUa0x-Eu_I_XCUUE-SQ8BArTJsiw34NNIoM5TI-bzDfeFHw_Imw2WXL-IExgjYM6zAloMo6QbzicUdF5PBEpeeQM8H89j9yfKl2B7gzNXlrvzInmDu35cSS35RtwlGxZNOFksiE3IKBvTWTDT4Q";
		RestAssured.baseURI = baseUrl.toString();
		EncoderConfig ec = new EncoderConfig();
		ec.appendDefaultContentCharsetToContentTypeIfUndefined(false);
		RequestSpecification requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Accept", "application/json").addHeader("Access-Control-Allow-Origin", "*")
				// .addHeader("Authorization", accessToken) // add it when
				// authentication mechanism is ready
				.addHeader("Authorization", token)//temp
				.setConfig(RestAssured.config()
						.encoderConfig(ec.appendDefaultContentCharsetToContentTypeIfUndefined(false))
						.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation("TLS")))
				.build();

		switch (APIMethodType.toString()) {
		case "POST":
			resp = given().body(body.get()).spec(requestSpec).when().post(url);
			break;

		case "GET":
			resp = given().header("Authorization", token).when().get(url);
			break;
		}

		return resp;
	}

}

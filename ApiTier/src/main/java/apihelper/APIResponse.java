package apihelper;



	import java.util.List;
	import java.util.Map;
	import io.restassured.http.Header;
	import io.restassured.path.json.JsonPath;
	import io.restassured.response.Response;

	public class APIResponse {
		
		private String contentType;
		private int StatusCode;
		private String ReasonPhrase;
		private List<Header> responseHeaders = null;
		private Map<String, String> responseCookies = null;
		private JsonPath responseJsonPath;
		private Response response;
		
		private APIBody body = new APIBody();
		
		public APIResponse(String bodyText)
		{
			this.body.setBodyText(bodyText);
		}
		
		public APIBody getBody() {
			return body;
		}
		public void setBody(APIBody body) {
			this.body = body;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public int getStatusCode() {
			return StatusCode;
		}
		public void setStatusCode(int statusCode) {
			StatusCode = statusCode;
		}
		public String getReasonPhrase() {
			return ReasonPhrase;
		}
		public void setReasonPhrase(String reasonPhrase) {
			ReasonPhrase = reasonPhrase;
		}
		public List<Header> getResponseHeaders() {
			return responseHeaders;
		}
		public void setResponseHeaders(List<Header> responseHeaders) {
			this.responseHeaders = responseHeaders;
		}
		public Map<String, String> getResponseCookies() {
			return responseCookies;
		}
		public void setResponseCookies(Map<String, String> responseCookies) {
			this.responseCookies = responseCookies;
		}
		public JsonPath getResponseJsonPath() {
			return responseJsonPath;
		}
		public void setResponseJsonPath(JsonPath responseJsonPath) {
			this.responseJsonPath = responseJsonPath;
		}
		public Response getResponse() {
			return response;
		}
		public void setResponse(Response response) {
			this.response = response;
		}
		

	

}

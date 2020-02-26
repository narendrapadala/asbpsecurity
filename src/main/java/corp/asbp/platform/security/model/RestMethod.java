package corp.asbp.platform.security.model;


import org.springframework.http.HttpMethod;

public enum RestMethod {
	GET(HttpMethod.GET),
	POST(HttpMethod.POST),
	PUT(HttpMethod.PUT),
	DELETE(HttpMethod.DELETE),
	PATCH(HttpMethod.PATCH);
	
	private HttpMethod httpMethod;
	
	private RestMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	public HttpMethod getHttpMethod() {
		return this.httpMethod;
	}
}

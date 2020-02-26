package corp.asbp.platform.security.util;

import java.util.regex.Pattern;

public interface Constants {

	public int MAX_EDIT_DISTANCE = 3;
	public String GUEST_ROLE = "GUEST";
	public String SS_HEADER = "ss-header";
	public String USER_HEADER = "user";
	public String REQUEST_URI = "requestUri";
	public String CONTENT_TYPE_KEY = "content-type";
	public String CONTENT_TYPE_VALUE = "application/json";
	public String REQUEST_METHOD = "requestMethod";

	// uris to exclude from filter
	public String SWAGGER_URI = "swagger";
	public String OAUTH2_URI = "oauth2";
	public String OAUTH_URI = "oauth";
	public String AUTH_URI = "auth";


	public String SWAGGER_DOCS_URI = "api-docs";
	public String SWAGGER_CONFIGURATION = "configuration";
	public String JAVA_MELODY_URI = "monitoring";
	public String OPTIONS_HEADER = "OPTIONS";
	public String HTML_PAGE = ".html";

	public String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error Occured!";
	public Pattern STATIC_CONTENTS_REGEX = Pattern.compile("(.*\\.(htm|html|css|js|ico|png|jpg|jpeg){1})");
	public String STATIC_CONTENTS = "webResources";
	public String AUTH_TOKEN = "auth-token";
	public String HEALTH_CHECK_API = "health";
	public String TIMEZONE = "timezone";

	public String ERROR_CODE_DELIMITER = "::";
	public String CLIENT_TOKEN = "client-token";
	public String ENTERPRISE_ID = "enterprise_id";
}

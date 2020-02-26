package corp.asbp.platform.security.model;

import org.springframework.http.HttpStatus;

import corp.asbp.platform.security.util.Constants;


public class InternalRestApiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message = Constants.INTERNAL_SERVER_ERROR_MESSAGE;
	private Integer statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
	private String api; 
	
	public InternalRestApiException() {
        super(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
    }
	
	public InternalRestApiException(String message) {
		super(transformedMessage(message));
		this.message = transformedMessage(message);
    }
	
	public InternalRestApiException(String message, Throwable cause) {
        super(transformedMessage(message), cause);
    }
	
	public InternalRestApiException(String message, Integer statusCode, String api) {
		this(message);
		this.statusCode = statusCode;
		this.api = api;
	}
	
	public InternalRestApiException(Integer statusCode, String api) {
		this();
		this.statusCode = statusCode;
		this.api = api;
	}
	
	public InternalRestApiException(Throwable cause) {
        this(Constants.INTERNAL_SERVER_ERROR_MESSAGE, cause);
    }
	
	public InternalRestApiException(Throwable cause, String api) {
        this(Constants.INTERNAL_SERVER_ERROR_MESSAGE, cause);
        this.api = api;
    }
	
	public InternalRestApiException(Throwable cause, Integer statusCode, String api) {
		this(cause);
        this.statusCode = statusCode; 
        this.api = api;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}
	
	private static String transformedMessage(String message2) {
		return (!isEmptyString(message2)) ? Constants.INTERNAL_SERVER_ERROR_MESSAGE : message2;
	}
	
	private static boolean isEmptyString(String string) {
		return string == null || string.isEmpty();
	}
}

package corp.asbp.platform.security.util;



import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import corp.asbp.platform.security.model.UserResponseDto;





@Component
public class ServletUtil {
	

	public boolean hasIgnoreCriteria(String uri, String method) {
		return uri.contains(Constants.SWAGGER_URI) 
			|| uri.contains(Constants.SWAGGER_DOCS_URI) 
			//|| uri.contains(Constants.AUTH_URI) 
			|| uri.contains(Constants.OAUTH_URI) 
			|| uri.contains(Constants.OAUTH2_URI) 
			|| uri.contains(Constants.SWAGGER_CONFIGURATION) 
			|| uri.contains(Constants.JAVA_MELODY_URI) 
			|| method.equals(Constants.OPTIONS_HEADER)
			|| uri.contains(Constants.STATIC_CONTENTS)
			|| uri.contains(Constants.HEALTH_CHECK_API)
			|| Constants.STATIC_CONTENTS_REGEX.matcher(uri).matches();
			
	}
	
	public boolean isEmptyString(String string) {
		return string == null || string.isEmpty();
	}
	
	public void setServletRequestObject(UserResponseDto userResponseDto, HttpServletRequest request) {
		request.setAttribute(Constants.USER_HEADER, userResponseDto);
	}

	public String getRequestUriFromAttribute(HttpServletRequest request) {
		return (String) request.getAttribute(Constants.REQUEST_URI); 
	}
	
	public String getRequestUri(HttpServletRequest request) {
		return request.getHeader(Constants.REQUEST_URI); 
	}
	
	public HttpMethod getRequestMethod(HttpServletRequest request) {
		return HttpMethod.valueOf(request.getHeader(Constants.REQUEST_METHOD)); 
	}
	
	public HttpServletRequest getHttpServletRequest() {
	    RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
	    if (attribs instanceof NativeWebRequest) {
	        return (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
	    }
		return null;
	}
}

package corp.asbp.platform.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import corp.asbp.platform.security.filter.condition.CommonFilterCondition;
import corp.asbp.platform.security.model.GenericResponseDto;
import corp.asbp.platform.security.model.InternalRestApiException;
import corp.asbp.platform.security.model.RestMethod;
import corp.asbp.platform.security.model.UserResponseDto;
import corp.asbp.platform.security.util.CommonUtilProperties;
import corp.asbp.platform.security.util.Constants;
import corp.asbp.platform.security.util.RestApiUtilInternal;
import corp.asbp.platform.security.util.ServletUtil;

@Component
@Conditional(CommonFilterCondition.class)
@Order(1)
public class CommonFilter implements Filter {

	private static Logger LOGGER = LoggerFactory.getLogger(CommonFilter.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private CommonUtilProperties properties;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ServletUtil servletUtil;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("Initiating common filter..");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			String headerToken = httpServletRequest.getHeader(Constants.AUTH_TOKEN);
			String uri = httpServletRequest.getRequestURI();
			String method = httpServletRequest.getMethod();

			try {

				
				if (servletUtil.hasIgnoreCriteria(uri, method)) {
					LOGGER.info("Sending request as it passed ignore criteria...");
					chain.doFilter(request, response);
				} else if (!servletUtil.isEmptyString(headerToken)) {

					if (headerToken.equals(properties.getAuthToken())) {
						LOGGER.info("Sending request via auth token...");
						if (!servletUtil.isEmptyString(httpServletRequest.getHeader(Constants.SS_HEADER))) {
							setUserAttributes(httpServletRequest, httpServletResponse, uri, method);
						}
						chain.doFilter(request, response);
					}else {
						httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			            writeErrorResponse(httpServletResponse, HttpStatus.UNAUTHORIZED, uri, 
			            		"Invalid token. You are not authorized to access this resource");
					}
				} else {
					setUserAttributes(httpServletRequest, httpServletResponse, uri, method);
					chain.doFilter(request, response);
				}

			} catch (Exception e) {
				LOGGER.error("An error occurred while validating the user or api access, please check the logs for more details.", e.getMessage());
				//e.printStackTrace();
				//throw new IOException("An error occurred while validating the user or api access, please check the logs for more details.",e);
			}
			

		}

	}

	@Override
	public void destroy() {
		LOGGER.info("Destroying common filter..");

	}

	@SuppressWarnings("unused")
	private void writeErrorResponse(HttpServletResponse httpServletResponse, HttpStatus httpStatus, String uri,
			String msg) throws IOException, JsonProcessingException {
		httpServletResponse.getWriter().write(mapper.writeValueAsString(
				new GenericResponseDto.GenericResponseDtoBuilder<UserResponseDto>(msg, httpStatus.value(), uri, null)
						.build()));
	}

	private void setUserAttributes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String uri, String method) throws JsonProcessingException, IOException, InternalRestApiException {
		httpServletRequest.setAttribute(Constants.REQUEST_URI, uri);
		httpServletRequest.setAttribute(Constants.REQUEST_METHOD, method);

		LOGGER.info("Sending request to asbp for getting user object via session and doing other validations...");
		GenericResponseDto<UserResponseDto> genericResponseDto = getGenericResponseDto(httpServletRequest);
		UserResponseDto userResponseDto = genericResponseDto.getResponse();
		servletUtil.setServletRequestObject(userResponseDto, httpServletRequest);
	}

	private GenericResponseDto<UserResponseDto> getGenericResponseDto(HttpServletRequest httpServletRequest)
			throws InternalRestApiException {
		RestApiUtilInternal<RequestEntity<Object>, UserResponseDto> util = new RestApiUtilInternal<>(
				properties.getAuthToken());
		return util.requestAndGetGenericResponseObject(restTemplate, properties.getAsbpSessionValidationUrl(),
				RestMethod.POST, UserResponseDto.class, httpServletRequest);
	}

}

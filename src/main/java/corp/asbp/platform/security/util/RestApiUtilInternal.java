package corp.asbp.platform.security.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


import corp.asbp.platform.security.model.GenericResponseDto;
import corp.asbp.platform.security.model.InternalRestApiException;
import corp.asbp.platform.security.model.RestMethod;
import corp.asbp.platform.security.model.SsHeader;



public class RestApiUtilInternal<I, O> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestApiUtilInternal.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private String authToken;
	private Long moduleId;
	private Long enterpriseId;
	
	public RestApiUtilInternal(String authToken) {
		this.authToken = authToken;
	}
	
	public RestApiUtilInternal(Long enterpriseId, String authToken) {
		this(authToken);
		this.enterpriseId = enterpriseId;
	}
	
	public RestApiUtilInternal(String authToken, Long moduleId) {
		this.authToken = authToken;
		this.moduleId = moduleId;
	}
	
	public GenericResponseDto<O> requestAndGetGenericResponseObject(RestTemplate restTemplate, String url, RestMethod method, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, null, request);
		return GenericResponseConverterUtil.convertToGenericResponseDto(response, url, responseClazz);
	}
	
	public GenericResponseDto<O> requestAndGetGenericResponseObject(RestTemplate restTemplate, String url, RestMethod method, I input, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, input, request);
		return GenericResponseConverterUtil.convertToGenericResponseDto(response, url, responseClazz);
	}
	
	public GenericResponseDto<O> requestAndGetGenericResponseObject(RestTemplate restTemplate, String url, RestMethod method, I input, HttpHeaders headers, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, input, request);
		return GenericResponseConverterUtil.convertToGenericResponseDto(response, url, responseClazz);
	}
	
	public GenericResponseDto<List<O>> requestAndGetGenericResponseList(RestTemplate restTemplate, String url, RestMethod method, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, request);
		return GenericResponseConverterUtil.convertToGenericResponseDtoList(response, url, responseClazz);
	}
	
	public GenericResponseDto<List<O>> requestAndGetGenericResponseList(RestTemplate restTemplate, String url, RestMethod method, I input, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, input, request);
		return GenericResponseConverterUtil.convertToGenericResponseDtoList(response, url, responseClazz);
	}
	
	public GenericResponseDto<List<O>> requestAndGetGenericResponseList(RestTemplate restTemplate, String url, RestMethod method, I input, HttpHeaders headers, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, input, headers, request);
		return GenericResponseConverterUtil.convertToGenericResponseDtoList(response, url, responseClazz);
	}
	
	public GenericResponseDto<Page<O>> requestAndGetGenericResponsePage(RestTemplate restTemplate, String url, RestMethod method, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, request);
		return GenericResponseConverterUtil.convertToGenericResponseDtoPage(response, url, responseClazz);
	}
	
	public GenericResponseDto<Page<O>> requestAndGetGenericResponsePage(RestTemplate restTemplate, String url, RestMethod method, I input, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, input, request);
		return GenericResponseConverterUtil.convertToGenericResponseDtoPage(response, url, responseClazz);
	}
	
	public GenericResponseDto<Page<O>> requestAndGetGenericResponsePage(RestTemplate restTemplate, String url, RestMethod method, I input, HttpHeaders headers, Class<O> responseClazz, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = requestAndGetObject(restTemplate, url, method, input, headers, request);
		return GenericResponseConverterUtil.convertToGenericResponseDtoPage(response, url, responseClazz);
	}
	
	public ResponseEntity<Object> requestAndGetObject(RestTemplate restTemplate, String url, RestMethod method, HttpServletRequest request) throws InternalRestApiException {
		return requestAndGetObject(restTemplate, url, method, null, request);
	}
	
	private ResponseEntity<Object> requestAndGetObject(RestTemplate restTemplate, String url, RestMethod method, I input, HttpServletRequest request) throws InternalRestApiException {
		return requestAndGetObject(restTemplate, url, method, input, null, request);
	}

	public ResponseEntity<Object> requestAndGetObject(RestTemplate restTemplate, String url, RestMethod method, I input, HttpHeaders headers, HttpServletRequest request) throws InternalRestApiException {
		ResponseEntity<Object> response = null;
		HttpMethod httpMethod = method.getHttpMethod();
		MultiValueMap<String, String> map = getHeaderMap(request, headers);
		HttpEntity<Object> entity = new HttpEntity<>(input, map);
		try {
			response = restTemplate.exchange(url, httpMethod, entity, Object.class);
			LOGGER.info("Url: " + url + ", Rest call response: " + response.getStatusCodeValue());
		} catch (Exception e) {
			LOGGER.info("Url: " + url);
			LOGGER.error("Error while calling the internal service: " + e.getCause().getMessage(), e);
			GenericResponseConverterUtil.throwException(url, e, response);
		}
		return response;
	}
	
	private MultiValueMap<String, String> getHeaderMap(HttpServletRequest request, HttpHeaders headers) throws InternalRestApiException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		if (request != null) {
			try {
				if (shouldAddToHeadersMap(headers, Constants.SS_HEADER)) {
					String header = request.getHeader(Constants.SS_HEADER);
					if (moduleId != null) {
						SsHeader ssHeader;
						
							ssHeader = mapper.readValue(header, SsHeader.class);
							ssHeader.setModuleId(moduleId);
							map.add(Constants.SS_HEADER, mapper.writeValueAsString(ssHeader));
					} else {
						map.add(Constants.SS_HEADER, header);
					}
				}
				if (request.getHeader(Constants.CLIENT_TOKEN) != null) {
					map.add(Constants.CLIENT_TOKEN, request.getHeader(Constants.CLIENT_TOKEN));
				}
				if (shouldAddToHeadersMap(headers, Constants.REQUEST_URI)) {
					map.add(Constants.REQUEST_URI, (String) request.getAttribute(Constants.REQUEST_URI));
				}
				if (shouldAddToHeadersMap(headers, Constants.REQUEST_METHOD)) {
					map.add(Constants.REQUEST_METHOD, (String) request.getAttribute(Constants.REQUEST_METHOD));
				}
				if (shouldAddToHeadersMap(headers, Constants.CONTENT_TYPE_VALUE)) {
					map.add(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_VALUE);
				}
			} catch (IOException e) {
				throw new InternalRestApiException(e.getMessage(), e);
			} catch (IllegalStateException e) {
				//this means request is a not web request. add token,enterprise headers
				map.add(Constants.AUTH_TOKEN, authToken);
				if(enterpriseId !=null)
					map.add(Constants.ENTERPRISE_ID, enterpriseId.toString());
			}
		}
		else {
			if (shouldAddToHeadersMap(headers, Constants.AUTH_TOKEN)) {
				map.add(Constants.AUTH_TOKEN, authToken);
			}
			if(enterpriseId !=null && shouldAddToHeadersMap(headers, Constants.ENTERPRISE_ID)) {
				map.add(Constants.ENTERPRISE_ID, enterpriseId.toString());
			}
		}
		return map; 
	}

	private boolean shouldAddToHeadersMap(HttpHeaders headers, String key) {
		return headers == null || (!headers.containsKey(key));
	}
}

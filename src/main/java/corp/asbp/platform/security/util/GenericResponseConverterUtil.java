package corp.asbp.platform.security.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import corp.asbp.platform.security.model.GenericResponseDto;
import corp.asbp.platform.security.model.InternalRestApiException;



public class GenericResponseConverterUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(GenericResponseConverterUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <O> GenericResponseDto<O> convertToGenericResponseDto(ResponseEntity<Object> responseEntity, String url, Class<O> clazz) throws InternalRestApiException {
		GenericResponseDto<O> response = mapAndValidateGenericResponse(responseEntity, url);
		O o = convertToResponseClass(response.getResponse(), clazz, responseEntity.getStatusCodeValue(), url, response.getMessage());
		response.setResponse(o);
		return response;
	}

	public static <O> GenericResponseDto<List<O>> convertToGenericResponseDtoList(ResponseEntity<Object> responseEntity, String url, Class<O> clazz) throws InternalRestApiException {
		GenericResponseDto<List<O>> response = mapAndValidateGenericResponseList(responseEntity, url);
		List<O> list = new ArrayList<>();
		List<O> responses = response.getResponse();
		if (responses != null) {
			for(O o : responses) {
				list.add(convertToResponseClass(o, clazz, responseEntity.getStatusCodeValue(), url, response.getMessage()));
			}
			response.setResponse(list);
		}
		else throwException(url);
		return response;
	}
	
	public static <O> GenericResponseDto<Page<O>> convertToGenericResponseDtoPage(ResponseEntity<Object> responseEntity, String url, Class<O> clazz) throws InternalRestApiException {
		GenericResponseDto<Page<O>> response = mapAndValidateGenericResponsePage(responseEntity, url);
		Page<O> responses = response.getResponse();
		if (responses != null) {
			List<O> list = responses.getContent();
			int size = list.size();
			for (int i = 0 ; i < size ; ++i) {
				O response2 = list.get(i);
				list.add(convertToResponseClass(response2, clazz, responseEntity.getStatusCodeValue(), url, response.getMessage()));
			}
			int newListSize = list.size();
			for (int i = 0, j = 0; j < newListSize - size; j++) {
				list.remove(i);
			}
			response.setResponse(responses);
		}
		else throwException(url);
		return response;
	}

	public static void throwException(String url) throws InternalRestApiException {
		throw new InternalRestApiException(HttpStatus.NOT_FOUND.value(), url);
	}
	
	public static void throwException(String url, Exception e, ResponseEntity<Object> responseEntity) throws InternalRestApiException {
		LOGGER.error(e.getCause().getMessage(), e);
		if (responseEntity != null) {
			throw new InternalRestApiException(e, responseEntity.getStatusCodeValue(), url);
		}
		throw new InternalRestApiException(e, url);
	}
	
	private static <O> O convertToResponseClass(O response, Class<O> clazz, Integer statusCode, String url, String message) throws InternalRestApiException {
		if (response != null) {
			return mapper.convertValue(response, clazz);
		}
		
		LOGGER.info("Error message : " + message);
		LOGGER.info("Error status code  : " + statusCode);
		LOGGER.info("Error url : " + url);
		
		throw new InternalRestApiException(message, statusCode, url);
	}
	

	
	private static <O> GenericResponseDto<O> mapAndValidateGenericResponse(ResponseEntity<Object> responseEntity, String url) throws InternalRestApiException {
		GenericResponseDto<O> response = null;
		try {
			response = mapper.convertValue(responseEntity.getBody(), new TypeReference<GenericResponseDto<O>>() {});
			if (response == null) {
				throwException(url);
			}
		} catch (Exception e) {
			throwException(url, e);
		}
		return response;
	}
	
	private static void throwException(String url, Exception e) throws InternalRestApiException {
		if(e.getCause()!=null)
			LOGGER.error(e.getCause().getMessage(), e);
		else
			LOGGER.error(e.getMessage(), e);
		throw new InternalRestApiException(e, url);
	}

	private static <O> GenericResponseDto<List<O>> mapAndValidateGenericResponseList(ResponseEntity<Object> responseEntity, String url) throws InternalRestApiException {
		GenericResponseDto<List<O>> response = null;
		try {
			response = mapper.convertValue(responseEntity.getBody(), new TypeReference<GenericResponseDto<List<O>>>() {});
			if (response == null) {
				throwException(url);
			}
		} catch (Exception e) {
			throwException(url, e);
		}
		return response;
	}
	
	private static <O> GenericResponseDto<Page<O>> mapAndValidateGenericResponsePage(ResponseEntity<Object> responseEntity, String url) throws InternalRestApiException {
		GenericResponseDto<Page<O>> response = null;
		/*
		try {
			response = mapper.convertValue(responseEntity.getBody(), new TypeReference<GenericResponseDto<CustomPageImpl<O>>>() {});
			if (response == null) {
				throwException(url);
			}
		} catch (Exception e) {
			throwException(url, e);
		}
		*/
		return response;
	}
}
 
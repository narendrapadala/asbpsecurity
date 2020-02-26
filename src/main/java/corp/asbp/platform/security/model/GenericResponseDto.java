package corp.asbp.platform.security.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

/**
 * @author Narendra Padala
 *
 * @param <T>
 */
public class GenericResponseDto<T> {
	private String message = "Success";
	private Integer statusCode = HttpStatus.OK.value();
	private String api;
	private T response;

	public GenericResponseDto() {
	}

	/**
	 * @param builder
	 */
	public GenericResponseDto(GenericResponseDtoBuilder<T> builder) {
		this.message = builder.message;
		this.statusCode = builder.statusCode;
		this.api = builder.api;
		this.response = builder.response;
	}

	/**
	 * @param t
	 * @param request
	 */
	public GenericResponseDto(T t, HttpServletRequest request) {
		this.response = t;
		this.api = request.getRequestURI();
	}

	public static class GenericResponseDtoBuilder<T> {
		private String message;
		private Integer statusCode;
		private String api;
		private T response;

		public GenericResponseDtoBuilder() {
		}

		public GenericResponseDtoBuilder(T response) {
			this.response = response;
		}

		public GenericResponseDtoBuilder(String message, Integer statusCode, String api, T response) {
			this.message = message;
			this.statusCode = statusCode;
			this.api = api;
			this.response = response;
		}

		public GenericResponseDtoBuilder(String message, HttpServletRequest request, T response) {
			this.message = message;
			this.api = request.getRequestURI();
			this.response = response;
		}

		public GenericResponseDtoBuilder(String message, HttpServletRequest request, T response,
				HttpServletResponse servletResponse, HttpStatus status) {
			this.message = message;
			this.api = request.getRequestURI();
			this.response = response;
			servletResponse.setStatus(status.value());
		}

		public GenericResponseDtoBuilder(HttpServletRequest request, T response) {
			this.api = request.getRequestURI();
			this.response = response;
		}

		public GenericResponseDtoBuilder<T> message(String message) {
			this.message = message;
			return this;
		}

		public GenericResponseDtoBuilder<T> statusCode(Integer statusCode) {
			this.statusCode = statusCode;
			return this;
		}

		public GenericResponseDtoBuilder<T> api(String api) {
			this.api = api;
			return this;
		}

		public GenericResponseDtoBuilder<T> response(T response) {
			this.response = response;
			return this;
		}

		public GenericResponseDto<T> build() {
			return new GenericResponseDto<T>(this);
		}
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

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "GenericResponseDto [message=" + message + ", statusCode=" + statusCode + ", api=" + api + ", response="
				+ response + "]";
	}
}

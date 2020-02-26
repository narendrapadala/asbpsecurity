package corp.asbp.platform.security.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonUtilProperties {

	@Value("${java.melody.user.name}")
	private String javaMelodyUserName;

	@Value("${java.melody.user.password}")
	private String javaMelodyPassword;

	@Value("${rest.template.read.timeout:30}")
	private Integer readTimeout;

	@Value("${rest.template.connection.timeout:30}")
	private Integer connectionTimeout;

	@Value("${rest.template.connection.request.timeout:20}")
	private Integer connectionRequestTimeout;

	@Value("${base.package.name}")
	private String basePackageName;

	@Value("${common.filter.auth-token}")
	private String authToken;

	@Value("${common.filter.external.services.auth-token}")
	private String externalServicesAuthToken;

	@Value("${detailed.logging.enabled:false}")
	private Boolean detailedLoggingEnabled;

	@Value("${common.exception.handler.disabled:false}")
	private Boolean isCommonExceptionHandlerDisabled;

	@Value("${common.thread.pool.enabled:false}")
	private Boolean isCommonThreadPoolEnabled;

	@Value("${is.error.msg.from.db.enabled:false}")
	private Boolean isErrorMsgFromDbEnabled;

	@Value("${error.code.prefix:}")
	private String errorCodePrefix;

	@Value("${asbp.session.validation.url}")
	private String asbpSessionValidationUrl;

	public String getAsbpSessionValidationUrl() {
		return asbpSessionValidationUrl;
	}

	public void setAsbpSessionValidationUrl(String asbpSessionValidationUrl) {
		this.asbpSessionValidationUrl = asbpSessionValidationUrl;
	}

	public String getJavaMelodyUserName() {
		return javaMelodyUserName;
	}

	public String getJavaMelodyPassword() {
		return javaMelodyPassword;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public Integer getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public String getBasePackageName() {
		return basePackageName;
	}

	public String getAuthToken() {
		return authToken;
	}

	public String getExternalServicesAuthToken() {
		return externalServicesAuthToken;
	}

	public Boolean getDetailedLoggingEnabled() {
		return detailedLoggingEnabled;
	}

	public Boolean getIsCommonExceptionHandlerDisabled() {
		return isCommonExceptionHandlerDisabled;
	}

	public Boolean getIsCommonThreadPoolEnabled() {
		return isCommonThreadPoolEnabled;
	}

	public Boolean getIsErrorMsgFromDbEnabled() {
		return isErrorMsgFromDbEnabled;
	}

	public String getErrorCodePrefix() {
		return errorCodePrefix;
	}

}

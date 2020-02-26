package corp.asbp.platform.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SsHeader {
	private String sessionId;
	private String version;
	private String clientKey;
	private Long moduleId;
	
	public SsHeader() {
	}
	
	public SsHeader(SsHeaderBuilder builder) {
		this.sessionId = builder.sessionId;
		this.version = builder.version;
		this.clientKey = builder.clientKey;
		this.moduleId = builder.moduleId;
	}

	public static class SsHeaderBuilder {
		private String sessionId;
		private String version;
		private String clientKey;

		private Long moduleId;

		
		public SsHeaderBuilder() {
		}
		
		public SsHeaderBuilder sessionId(String sessionId) {
			this.sessionId = sessionId;
			return this;
		}
		
		public SsHeaderBuilder version(String version) {
			this.version = version;
			return this;
		}
		
		public SsHeaderBuilder clientKey(String clientKey) {
			this.clientKey = clientKey;
			return this;
		}
		
		
		public SsHeaderBuilder moduleId(Long moduleId) {
			this.moduleId = moduleId;
			return this;
		}
		
		
		public SsHeader build() {
			return new SsHeader(this);
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}



	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	

	@Override
	public String toString() {
		return "SsHeader [sessionId=" + sessionId + ", version=" + version
				+ ", clientKey=" + clientKey 
				+ ", moduleId=" + moduleId + "]";
	}
}

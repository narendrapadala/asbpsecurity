package corp.asbp.platform.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

	private String sessionId;
	private Long id;
	private String email;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserResponseDto(String sessionId, Long id, String email) {
		this.id = id;
		this.email = email;
		this.sessionId = sessionId;
	}

	public UserResponseDto() {
		// TODO Auto-generated constructor stub
	}
}
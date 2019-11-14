package requestDTO;

public class AuthenticationRequestDTO {

	public String getBpc() {
		return bpc;
	}

	public AuthenticationRequestDTO setBpc(String bpc) {
		this.bpc = bpc;
		return this;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public AuthenticationRequestDTO setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public AuthenticationRequestDTO setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	private String bpc;
	private String partnerCode;
	private String userName;

}

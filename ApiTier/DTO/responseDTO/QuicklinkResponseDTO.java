package responseDTO;

import java.util.List;

public class QuicklinkResponseDTO {
	private List<String> returnValue;

	private boolean isSuccess;

	private Exception exception;

	public void setReturnValue(List<String> returnValue) {
		this.returnValue = returnValue;
	}

	public List<String> getReturnValue() {
		return this.returnValue;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean getIsSuccess() {
		return this.isSuccess;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return this.exception;
	}
}

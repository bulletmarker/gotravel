package org.macs.gotravel.web.dto;

/**
* @ClassName: ReturnData
* @Description: Action请求返回对象
* @author Jason.ma
* @date 2013年10月24日 下午4:37:40
*
 */
public class ReturnData implements IGoTravelDTO{
	
	private static final long serialVersionUID = 1L;

	private Boolean success = true;
	
	private String error = null;
	
	private String errorCode = null;
	
	private IGoTravelDTO data = null;
	
	private String stringData = null;

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public IGoTravelDTO getData() {
		return data;
	}

	public void setData(IGoTravelDTO data) {
		this.data = data;
	}
	
}

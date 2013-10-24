package org.macs.gotravel.web.exception;

/**
 * 配置异常
 * @author Jason.ma
 */
public class ConfigException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ConfigException(String msg,Throwable e){
		super(msg,e);
	}

}

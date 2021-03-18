package com.dawson.common.exception;

/**
 * 领域服务异常
 * @author vigoss
 *
 */
public class DomainException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6891497747018322921L;
	
    public DomainException() {
        super();
    }

    
    public DomainException(String message) {
        super(message);
    }

    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    
    public DomainException(Throwable cause) {
        super(cause);
    }


    protected DomainException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

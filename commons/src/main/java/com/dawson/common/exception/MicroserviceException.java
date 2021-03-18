package com.dawson.common.exception;

/**
 * 领域服务异常
 * @author vigoss
 *
 */
public class MicroserviceException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -6891497747018322921L;

    public MicroserviceException() {
        super();
    }


    public MicroserviceException(String message) {
        super(message);
    }


    public MicroserviceException(String message, Throwable cause) {
        super(message, cause);
    }


    public MicroserviceException(Throwable cause) {
        super(cause);
    }


    protected MicroserviceException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

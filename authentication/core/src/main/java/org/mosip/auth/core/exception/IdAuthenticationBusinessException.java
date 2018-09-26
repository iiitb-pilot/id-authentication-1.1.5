package org.mosip.auth.core.exception;

import org.mosip.auth.core.constant.IdAuthenticationErrorConstants;
import org.mosip.kernel.core.exception.BaseCheckedException;
import org.mosip.kernel.core.exception.BaseUncheckedException;

/**
 * The base exception for ID Authentication which is always associated with an error code.
 * This exception is thrown at Service level, usually wrapped with root cause.
 *
 * @author Manoj SP
 */
public class IdAuthenticationBusinessException extends BaseCheckedException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7770924160513076138L;
	
	/**
	 * Instantiates a new id authentication business exception.
	 */
	public IdAuthenticationBusinessException() {
		super();
	}
	
	/**
	 * Constructs exception for the given error code and error message.
	 *
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 * @see BaseUncheckedException#BaseUncheckedException(String, String)
	 */
	public IdAuthenticationBusinessException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	/**
	 * Constructs exception for the given  error code, error message and {@code Throwable}.
	 *
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 * @param cause the cause
	 * @see BaseUncheckedException#BaseUncheckedException(String, String, Throwable)
	 */
	public IdAuthenticationBusinessException(String errorCode, String errorMessage, Throwable cause) {
		super(errorCode, errorMessage, cause);
	}


	/**
	 * Constructs exception for the given {@code IdAuthenticationErrorConstants}.
	 *
	 * @param exceptionConstant the exception constant
	 * @see BaseUncheckedException#BaseUncheckedException(String, String)
	 */
	public IdAuthenticationBusinessException(IdAuthenticationErrorConstants exceptionConstant) {
		this(exceptionConstant.getErrorCode(), exceptionConstant.getErrorMessage());
	}

	/**
	 * Constructs exception for the given {@code IdAuthenticationErrorConstants} and {@code Throwable}.
	 *
	 * @param exceptionConstant the exception constant
	 * @param rootCause the root cause
	 * @see BaseUncheckedException#BaseUncheckedException(String, String, Throwable)
	 */
	public IdAuthenticationBusinessException(IdAuthenticationErrorConstants exceptionConstant, Throwable rootCause) {
		this(exceptionConstant.getErrorCode(), exceptionConstant.getErrorMessage(), rootCause);
	}

}

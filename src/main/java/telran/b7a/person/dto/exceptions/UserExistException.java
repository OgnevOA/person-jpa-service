package telran.b7a.person.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT, reason = "person already exist")
public class UserExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7826699805120278586L;

}

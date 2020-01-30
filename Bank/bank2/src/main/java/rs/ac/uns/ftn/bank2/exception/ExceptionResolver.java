package rs.ac.uns.ftn.bank2.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity notFoundException(NotFoundException exception) {
    	logger.error("NOT_FOUND:> " + exception.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity badRequestException(BadRequestException exception) {
    	logger.error("BAD_REQUEST:> " + exception.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity httpClientErrorException(HttpClientErrorException exception){
    	logger.error("BAD_REQUEST:> " + exception.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity invalidDataException(InvalidDataException exception){
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    private ResponseEntity buildResponseEntity(ApiError apiError) {
    	logger.error("Api EROR:> " + apiError.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}

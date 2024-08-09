package cn.badminton.tool.support.exception;

import cn.badminton.tool.support.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;


/**
 * 异常处理器
 *
 */

@RestControllerAdvice
@Order(1)
public class ServerExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(ServerExceptionHandler.class);

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<ErrorCode> handleException(ServerException ex) {

        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public Result<ErrorCode> bindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        return Result.error(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<ErrorCode> handleAccessDeniedException(Exception ex) {
        return Result.error(ErrorCode.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public Result<ErrorCode> handleException(Exception ex) {
//        String trace = StackTraceUtil.getTrace(ex.getCause());
//        logger.info(trace);
        ex.printStackTrace();
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
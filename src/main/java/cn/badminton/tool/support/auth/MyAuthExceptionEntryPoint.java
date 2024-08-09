package cn.badminton.tool.support.auth;

import cn.badminton.tool.support.exception.ErrorCode;
import cn.badminton.tool.support.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class MyAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {


        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Result<ErrorCode> error = Result.error(ErrorCode.UNAUTHORIZED);

        String res = jsonMapper.writeValueAsString(error);
        response.getWriter().write(res);
        response.flushBuffer();

    }
}

package cn.badminton.tool.support.auth;

import cn.badminton.tool.tools.JWTUtil;
import cn.badminton.tool.web.dto.PayloadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class WXAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        WXAppletAuthenticationToken wxAppletAuthenticationToken =  (WXAppletAuthenticationToken)authentication;

        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setOpenid(wxAppletAuthenticationToken.getOpenid());
        payloadDTO.setSub("badminton-tool");
        payloadDTO.setIat(new Date().getTime());
//        SnowFlake snowFlake = new SnowFlake(1, 1);


        String token = null;
        try {
            token = JWTUtil.generateToken(payloadDTO);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
//        String refreshToken = JWTUtil.generateToken(wxAppletAuthenticationToken.getOpenid(), wxAppletAuthenticationToken.getId(), wxAppletAuthenticationToken.getNickName(),10);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("msg", "success");
        resMap.put("code", 0);
        resMap.put("data", token);

//        Result<String> ok = Result.ok(token);

        String resJson = jsonMapper.writeValueAsString(resMap);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(resJson);
        response.flushBuffer();
    }
}

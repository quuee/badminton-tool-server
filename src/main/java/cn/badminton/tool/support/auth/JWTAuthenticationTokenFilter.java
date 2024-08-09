package cn.badminton.tool.support.auth;

import cn.badminton.tool.web.entity.WXUserEntity;
import cn.badminton.tool.support.exception.ErrorCode;
import cn.badminton.tool.support.exception.ServerException;
import cn.badminton.tool.web.service.AuthUserService;
import cn.badminton.tool.tools.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.text.ParseException;

@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {


    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationTokenFilter.class);

    @Autowired
    private AuthUserService userService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info(request.getRequestURI());

        String token = request.getHeader("Authorization");
        logger.info("url:{},token:{}",request.getRequestURI(),token);
        if(!ObjectUtils.isEmpty(token)){
            LinkedTreeMap<String,Object> payload = null;
            try{
                // 全局异常只能拦截到controller层，解决方法：handlerExceptionResolver
                // handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.TOKENEXPIRED));
                payload = JWTUtil.verify(token); // 如果token过期，JWT解析时会抛出异常TokenExpiredException

                // TODO 如果token临近过期，再发个token，放response里和数据一起返回。
            }
            catch (ParseException e) {
                handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.UNAUTHORIZED));
                return;

            } catch (JOSEException e) {
                handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.UNAUTHORIZED));
                return;
//                throw new RuntimeException(e);
            }catch (ServerException e){
                if(e.getCode() == ErrorCode.TOKEN_EXPIRED.getCode()){
                    handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.TOKEN_EXPIRED));
                    return;
                }
            }

            if(!ObjectUtils.isEmpty(payload)){
                String openid = (String) payload.get("openid");
//                PayloadDTO payloadDTO = jsonMapper.readValue(string, PayloadDTO.class);
//                String openid = payloadDTO.getOpenid();
                WXUserEntity user = userService.getByOpenid(openid);
                WXAppletAuthenticationToken wxAppletAuthenticationToken = new WXAppletAuthenticationToken(user.getOpenid(), user.getUid(), user.getNickName(), user.getAvatarUrl());
                SecurityContextHolder.getContext().setAuthentication(wxAppletAuthenticationToken);
            }

        }

        filterChain.doFilter(request,response);
    }

}

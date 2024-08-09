package cn.badminton.tool.support.auth;

import cn.badminton.tool.tools.WXLoginUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;


public class WXAppletAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthenticationProcessingFilter.class);

    private ObjectMapper jsonMapper;

    private WXLoginUtil wxUtil;

    private static final String defaultFilterProcessesUrl="/wx_user/login";

    public WXAppletAuthenticationFilter() {
        super(defaultFilterProcessesUrl);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!"POST".equalsIgnoreCase(request.getMethod())){
            throw new HttpRequestMethodNotSupportedException(request.getMethod());
        }
        // 自定义ContentType
        if(!"application/json".equalsIgnoreCase(request.getContentType())){
            throw new HttpMediaTypeNotSupportedException(request.getContentType());
        }
        JsonNode params = getParams(request);
        JsonNode jsCode = params.get("jsCode");
//        JsonNode nickName = params.get("nickName");
//        JsonNode avatarUrl = params.get("avatarUrl");

        if(ObjectUtils.isEmpty(jsCode)){
            throw new MissingServletRequestParameterException("jsCode","String");
        }
        //微信code2session，获取openid session_key
        Map<String, Object> code2Session = wxUtil.code2Session(jsCode.asText());
        logger.info("code2Session:{}",code2Session);
        String sessionKey = String.valueOf(code2Session.get("session_key")==null?"":code2Session.get("session_key"));
        String openid = String.valueOf(code2Session.get("openid")==null?"":code2Session.get("openid"));
        if(ObjectUtils.isEmpty(sessionKey) || ObjectUtils.isEmpty(openid)){
            throw new RuntimeException("无法获取openid session_key");
        }

        WXAppletAuthenticationToken wxAppletAuthenticationToken = new WXAppletAuthenticationToken(openid,"","");
        return this.getAuthenticationManager().authenticate(wxAppletAuthenticationToken);
    }

    private JsonNode getParams(HttpServletRequest request) throws IOException{

        StringBuilder stringBuffer = new StringBuilder();
        String line = null;
        BufferedReader reader = request.getReader();
        while((line = reader.readLine())!=null){
            stringBuffer.append(line);
        }
        String jsonString = stringBuffer.toString().replaceAll("\\s", "").replaceAll("\n", "");
        JsonNode jsonNode = jsonMapper.readTree(jsonString);
        return jsonNode;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setWxLoginUtil(WXLoginUtil wxUtil) {
        this.wxUtil = wxUtil;
    }
}

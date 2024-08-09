package cn.badminton.tool.tools;

import cn.badminton.tool.config.WXProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WXLoginUtil {

    @Autowired
    private WXProperties wxProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper jsonMapper;


    public Map<String,Object> code2Session(String jsCode) throws JsonProcessingException {
        Map<String, Object> sendParam = new HashMap<>();
        sendParam.put("js_code",jsCode);
        sendParam.put("appid",wxProperties.getAppid());
        sendParam.put("secret",wxProperties.getSecret());
        sendParam.put("grant_type",wxProperties.getGrant_type());


        String url = "?js_code={js_code}&appid={appid}&secret={secret}&grant_type={grant_type}";

        String forObject = restTemplate.getForObject(wxProperties.getJscode2session_url() + url, String.class, sendParam);

        Map<String, Object> map
                = jsonMapper.readValue(forObject, new TypeReference<>(){});

        return map;
    }
}

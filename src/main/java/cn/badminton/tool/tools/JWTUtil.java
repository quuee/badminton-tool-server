package cn.badminton.tool.tools;


import cn.badminton.tool.support.exception.ServerException;
import cn.badminton.tool.web.dto.PayloadDTO;
import cn.badminton.tool.support.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class JWTUtil {

    public static String secret = "Badminton_tools_1234567890Badminton_tools_1234567890Badminton_tools_1234567890Badminton_tools_1234567890";

    /**
     * 生成
     *
     * @param payloadDTO
     * @return
     */
    public static String generateToken(PayloadDTO payloadDTO) throws JOSEException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60*24*7);
        //创建HMAC签名器
        JWSSigner jwsSigner = new MACSigner(secret);
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();

        JWTClaimsSet jwtUser = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString().replace("-", ""))
                .claim("jwtUser", payloadDTO)
                .expirationTime(calendar.getTime())
                .build();
        //创建JWS对象
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtUser);

        signedJWT.sign(jwsSigner);

        return signedJWT.serialize();
    }

    public static LinkedTreeMap<String,Object> verify(String token) throws ParseException, JOSEException {
        //从token中解析JWS对象
        SignedJWT signedJWT = SignedJWT.parse(token);
        //创建HMAC验证器
        JWSVerifier jwsVerifier = new MACVerifier(secret);

        //验证token是否正确
        boolean verify = signedJWT.verify(jwsVerifier);
        //验证是否过期
        boolean before = new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
        if (!verify || !before) {
            throw new ServerException(ErrorCode.TOKEN_EXPIRED.getCode()+" : "+ErrorCode.TOKEN_EXPIRED.getMsg());
        }

        LinkedTreeMap<String,Object> jwtUser = (LinkedTreeMap<String,Object>) signedJWT.getJWTClaimsSet().getClaim("jwtUser");
        return jwtUser;
    }
}

package com.suven.framework.http.validator;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {



    public static final String JWT_SECRET = "st_short_video_26995aec-acb4-430d-b9fd-c3d8527b2871";

    public static final  String JWT_ISSUER = "future";//签发人

    public static final long JWT_EXPIRE_TIME = 3 * 60 * 60 * 1000;


    /**
     * 创建token
     * @param subject
     * @return
     */
    public static String createToken(String subject) {

        // 生成JWT的时间
        Date now = new Date();
        Date expire = new Date(now.getTime() + JWT_EXPIRE_TIME);

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        Key key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        return Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setId(UUID.randomUUID().toString())                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           // iat: jwt的签发时间
                .setIssuer(JWT_ISSUER)          // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(key) // 设置签名使用的签名算法和签名使用的秘钥
                .setExpiration(expire) //设置过期时间
                .compact();
    }


    /**
     * 获取用户信息
     * @param token
     * @return
     */
    public static String getUserInfo(String token) {
        Key key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        try {
//            Claims claims = Jwts.parser()  //得到DefaultJwtParser
//                    .setSigningKey(key)                 //设置签名的秘钥
//                    .parseClaimsJws(token).getBody();     //设置需要解析的jwt
            //得到DefaultJwtParser  //设置签名的秘钥  //设置需要解析的jwt
            Claims claims =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (Exception e){
        }
        return null;
    }


    /**
     * 获取用户ID
     * @param token
     * @return
     */
    public static long getUserId(String token) {
        Key key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        //得到DefaultJwtParser  //设置签名的秘钥  //设置需要解析的jwt
        Claims claims =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        claims.getSubject();
        String obj = JwtUtil.getUserInfo(token);
        if (StringUtils.isBlank(obj)) { //查找不到直接返回错误值
            return -1;
        }
        JSONObject object = JSONObject.parseObject(obj);
        return object.getLong("id");
    }


    /**
     * 由字符串生成加密key
     * @return
     */
    private static Key generalKey() {
        byte[] bytes = JWT_SECRET.getBytes();
        //生成SHA密钥
        Key key = Keys.hmacShaKeyFor(bytes);
        return key;
    }

}
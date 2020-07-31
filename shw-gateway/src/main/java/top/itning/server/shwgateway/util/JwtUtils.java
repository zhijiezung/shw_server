package top.itning.server.shwgateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import top.itning.server.common.exception.CasException;
import top.itning.server.common.model.LoginUser;

import java.util.Date;

/**
 * Jwt 工具类
 */
public final class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    private static final String PRIVATE_KEY = "hxcshw";
    private static final String LOGIN_USER = "loginUser";
    private static final String DEFAULT_STR = "null";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JwtUtils() {

    }

    /**
     * 生成 token
     *
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static String buildJwt(Object o) throws JsonProcessingException {
        return Jwts.builder()
                //SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                //expTime是过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .setExpiration(new Date(Long.MAX_VALUE))
                .claim(LOGIN_USER, MAPPER.writeValueAsString(o))
                //令牌的发行者
                .setIssuer("itning")
                .compact();
    }

    /**
     * 验证 token，解析登录用户信息
     *
     * @param jwt
     * @return
     */
    public static LoginUser getLoginUser(String jwt) {
        if (DEFAULT_STR.equals(jwt)) {
            throw new CasException("请先登陆", HttpStatus.UNAUTHORIZED);
        }
        try {
            //解析JWT字符串中的数据，并进行最基础的验证
            Claims claims = Jwts.parser()
                    //SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
                    .setSigningKey(PRIVATE_KEY)
                    //jwt是JWT字符串
                    .parseClaimsJws(jwt)
                    .getBody();
            //获取自定义字段key
            String loginUserJson = claims.get(LOGIN_USER, String.class);
            LoginUser loginUser = MAPPER.readValue(loginUserJson, LoginUser.class);
            //判断自定义字段是否正确
            if (loginUser == null) {
                throw new CasException("登陆失败", HttpStatus.UNAUTHORIZED);
            } else {
                LOGGER.info("登录用户信息：{}", loginUser);
                return loginUser;
            }
            //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
        } catch (ExpiredJwtException e) {
            throw new CasException("登陆超时", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            throw new CasException("凭据错误", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new CasException("登陆失败", HttpStatus.UNAUTHORIZED);
        }
    }
}

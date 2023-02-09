package top.keyle.universal_tool;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author TMJIE5200
 * TODO Jwt工具类
 */
public class JwtUtils {
    /**
     * 过期时间，单位s
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * 密钥
     */
    public static final String APP_SECRET = "kfc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.APP_SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

 /**
  * 根据Id和名称获取JwtToken
  * @param id
  * @param nickname
  * @return
  */
 public static String getJwtToken(String id, String nickname) {
     SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
     SecretKey secretKey = generalKey();
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("onlineVideo_token")
                .setIssuedAt(new Date())
                .setIssuer("onlineVideo_token")     // 签发者
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("nickname", nickname)
                .signWith(signatureAlgorithm, secretKey)
                .compact();
        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        SecretKey secretKey = generalKey();
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     */
    public static Claims checkTokenReClaims(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if (StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     *
     * @param request
     * @return
     */
        public static String getMemberIdByJwtToken(HttpServletRequest request) {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return "没有获取到token";
            }
            SecretKey secretKey = generalKey();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            return (String)claims.get("id");
        }

}

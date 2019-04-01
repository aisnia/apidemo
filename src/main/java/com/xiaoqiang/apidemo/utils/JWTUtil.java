package com.xiaoqiang.apidemo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/28-20:43
 */
public class JWTUtil {
    /**
     * 设置过期时间为 三分钟
     */
    private static final long ACCESS_EXPIRE_TIME = 60 * 1000 * 3;
    /**
     * AccessToken密匙
     */
    public static final String ACCESS_SECRET = "access token secret";
    /**
     * RefreshToken 密匙
     */
    public static final String REFRESH_SECRET = "refresh token secret";
    /**
     *RefreshToken的过期时间10分钟
     * */
    private static final long REFRESH_EXPIRE_TIME = 60 * 1000 * 10;

/**
 *  生成AccessToken并放入用户的id
 * @param userId
 * @return
 * */
public static String createAccessToken(int userId) {
    return createToken(userId, ACCESS_SECRET, ACCESS_EXPIRE_TIME);
}
/**
 * 生成RefreshToken 放入用户的id
 * */
public static String createRefreshToken(int userId) {
        return createToken(userId,REFRESH_SECRET,REFRESH_EXPIRE_TIME);
    }
    /**
     * 生成token
     * @param userId 用户id
     * @param secret token密钥
     * @param expireTime 过期时间
     * @return
     */
    private static String createToken(int userId,String secret,long expireTime){
//        将id 转换成 String
        String userIdStr = String.valueOf(userId);
//        当前时间 加上过期时间
        Date date = new Date(System.currentTimeMillis() + expireTime);
//        加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //在refreshtoken中放入用户的id
        return JWT.create()
                .withClaim("userId",userIdStr)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证accessToken是否有效
     * @param accessToken
     * @return
     */
    public static Map<String, Claim> verifyAccessToken(String accessToken) {
        return getStringClaimMap(accessToken, ACCESS_SECRET);
    }
    /**
     * 验证refreshToken 是否有效
     * @param refreshToken
     * @return
     */
    public static Map<String,Claim> verifyRefreshToken(String refreshToken) {
        return getStringClaimMap(refreshToken, REFRESH_SECRET);
    }
    private static Map<String, Claim> getStringClaimMap(String refreshToken, String refreshSecret) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(refreshSecret)).build();
        DecodedJWT jwt = verifier.verify(refreshToken);
        Map<String,Claim> claims = jwt.getClaims();
        return claims;
    }
}

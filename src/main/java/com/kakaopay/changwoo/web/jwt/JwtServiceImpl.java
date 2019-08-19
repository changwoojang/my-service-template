package com.kakaopay.changwoo.web.jwt;

import com.kakaopay.changwoo.common.error.ErrorCodeType;
import com.kakaopay.changwoo.util.AppException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService{

    private static final String SALT =  "kakaopay";

    @Override
    public String get(String key) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt.replaceAll("Bearer ",""));
        } catch (Exception e) {
            if(log.isInfoEnabled()){
                e.printStackTrace();
            }else{
                log.error(e.getMessage());
            }
            throw new AppException(ErrorCodeType.JWT_TOKEN_INVALID);
        }
        return (String)claims.getBody().get(key);
    }

    @Override
    @Transactional
    public String create(String key, String id) {
        return  Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .claim(key, id)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
    }

    private byte[] generateKey(){
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Making JWT Key Error ::: {}", e.getMessage());
        }
        return key;
    }

    @Override
    @Transactional
    public boolean isValid(String jwt) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt.replace("Bearer ", ""));
            return true;
        }catch (Exception e) {
            log.info("ERROR : {}", ErrorCodeType.TOKEN_INVALID);
            throw new AppException(ErrorCodeType.TOKEN_INVALID);
        }
    }
}

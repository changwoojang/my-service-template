package com.kakaopay.changwoo.web.login;

import com.kakaopay.changwoo.util.SecurityUtil;
import com.kakaopay.changwoo.web.jwt.JwtService;
import com.kakaopay.changwoo.web.login.dto.LoginResult;
import com.kakaopay.changwoo.web.login.dto.SigninResult;
import com.kakaopay.changwoo.web.login.dto.TokenResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
@RestController
@Slf4j
@RequestMapping("/v1/bank/member")
public class LoginController {

    private static final String HEADER_AUTH = "Authorization";

    @Autowired
    LoginService loginService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SigninResult signup(@RequestParam("memberId") String id,
                              @RequestParam("password") String password,
                               HttpServletResponse response){

        SigninResult signinResult = loginService.createMember(id, SecurityUtil.encrypt(password));
        response.setHeader("Authorization", signinResult.getToken());

        log.info("signup() - SigninResult : {}", signinResult);

        return signinResult;
    }

    @PostMapping(value="/login")
    public LoginResult login(@RequestParam("memberId")String memberId,
                              @RequestParam("password")String encryptedPassword,
                              HttpServletResponse response){

        LoginResult loginResult = loginService.loginMember(memberId, encryptedPassword);

        log.info("login() - LoginResult : {}", loginResult);

        return loginResult;
    }

    @GetMapping (value="/token-refresh", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TokenResult tokenRefresh(HttpServletResponse response){

        String memberId = jwtService.get("memberId");

        TokenResult refreshToken = loginService.tokenRefresh(memberId);
        response.setHeader("tokenRefresh() - {}", refreshToken.toString());

        log.info("tokenRefresh() - TokenResult : {}", refreshToken);

        return refreshToken;
    }
}

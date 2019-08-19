package com.kakaopay.changwoo.web.login;

import com.kakaopay.changwoo.common.error.ErrorCodeType;
import com.kakaopay.changwoo.domain.model.login.MemberEntity;
import com.kakaopay.changwoo.domain.model.login.MemberRepository;
import com.kakaopay.changwoo.util.AppException;
import com.kakaopay.changwoo.web.jwt.JwtService;
import com.kakaopay.changwoo.web.login.dto.LoginResult;
import com.kakaopay.changwoo.web.login.dto.SigninResult;
import com.kakaopay.changwoo.web.login.dto.TokenResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public SigninResult createMember(String id, String password){

        MemberEntity checkMemberId = memberRepository.findByMemberId(id);

        if(!ObjectUtils.isEmpty(checkMemberId)){
            throw new AppException(ErrorCodeType.MEMBER_ID_INVALID);
        }

        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(id)
                .password(password)
                .build();
        memberRepository.save(memberEntity);

        String token = jwtService.create("memberId",id);
        SigninResult tokenResult = SigninResult.builder()
                .memberId(id).token(token).build();

        return tokenResult;
    }

    private boolean isValidPassword(String memberEncryptedPassword,String encryptedPassword){
        if(memberEncryptedPassword.equals(encryptedPassword)){
            return true;
        }
        return false;
    }

    @Transactional
    public LoginResult loginMember(String memberId, String encryptedPassword){


        MemberEntity memberEntity = memberRepository.findByMemberId(memberId);

        if( !isValidPassword(memberEntity.getPassword(), encryptedPassword)){
            throw new AppException(ErrorCodeType.PASSWORD_INVALID);
        }

        //private claim으로 memeberId를 저장
        String token = jwtService.create("memberId", memberId);

        return LoginResult.builder()
                .memberId(memberId)
                .token(token)
                .build();
    }

    @Transactional
    public TokenResult tokenRefresh(String memberId){

        MemberEntity memberEntity = memberRepository.findByMemberId(memberId);

        //Token에 해당 아이다가 포함되어 있는지 확인
        if(ObjectUtils.isEmpty(memberEntity) || !memberEntity.getMemberId().equals(memberId)){
            throw new AppException(ErrorCodeType.JWT_TOKEN_INVALID);
        }

        String token = jwtService.create("memberId",memberId);

        TokenResult tokenResult = TokenResult.builder()
                .memberId(memberId)
                .token(token)
                .build();

        return tokenResult;
    }

}

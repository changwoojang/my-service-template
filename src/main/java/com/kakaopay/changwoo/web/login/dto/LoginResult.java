package com.kakaopay.changwoo.web.login.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
@Builder
@Data
public class LoginResult {
    String memberId;
    String token;
}

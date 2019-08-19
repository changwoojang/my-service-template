package com.kakaopay.changwoo.web.jwt;

import java.util.Map;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
public interface JwtService {

    String create(String key, String id);

    boolean isValid(String jwtToken);

    String get(String key);

}

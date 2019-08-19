package com.kakaopay.changwoo.common.interceptor;


import com.kakaopay.changwoo.common.error.ErrorCodeType;
import com.kakaopay.changwoo.util.AppException;
import com.kakaopay.changwoo.web.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);

		if(token != null && jwtService.isValid(token)){
			return true;
		}else{
			throw new AppException(ErrorCodeType.JWT_TOKEN_INVALID);
		}

	}
}

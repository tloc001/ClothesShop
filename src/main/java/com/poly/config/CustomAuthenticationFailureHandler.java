package com.poly.config;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMessage = null ;
		if (exception.getMessage().contains("User is disabled")) {
			errorMessage = "Tài khoản của bạn đã bị vô hiệu hóa";
		}else if(exception.getMessage().contains("Bad credentials")) {
			errorMessage = "Sai thông tin tài khoản hoặc mật khẩu";
			request.setAttribute("message", "Sai thông tin tài khoản hoặc mật khẩu");
		}else {
            errorMessage = "Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin.";
		}
		System.err.println(exception.getMessage());
		response.sendRedirect("/public/login?error="+URLEncoder.encode(errorMessage,"UTF-8"));
	}

}

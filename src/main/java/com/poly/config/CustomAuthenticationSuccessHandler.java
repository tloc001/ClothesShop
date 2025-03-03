package com.poly.config;

import java.io.IOException;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {
//	RequestCache là nơi Spring Security lưu trữ các yêu cầu (request) 

//	trước đó khi người dùng chưa được xác thực (ví dụ: khi người dùng bị chặn tại một trang yêu cầu đăng nhập).
	private RequestCache requestCache = new HttpSessionRequestCache();
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        nơi lưu request trước đó của nười dùng
		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		lấy từ tên ứng dụng của người dùng vd : /ASM/admin/
		String redirectURL = request.getContextPath();
		if (savedRequest != null) {
			redirectURL = savedRequest.getRedirectUrl();
		}else {
			if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
	            redirectURL = "/admin/";
	        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
	            redirectURL = "/public/shop";
	        }
		}
		
        response.sendRedirect(redirectURL);
    }
}

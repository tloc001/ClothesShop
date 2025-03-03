package com.poly.controllers.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.bind.annotation.GetMapping;


import com.poly.models.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
@GetMapping("/")
public String getForm(Model model) {
	
//	lấy thông tin user
	 Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
	 CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	model.addAttribute("fullname", customUserDetails.getFullname());	 
    return "/admin/index";
}

}

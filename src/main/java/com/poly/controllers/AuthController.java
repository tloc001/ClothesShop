package com.poly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.models.User;
import com.poly.services.SessionCartService;
import com.poly.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/public")
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;
@GetMapping("/login")
public String getMethodName() {
    return "login";
}
@GetMapping("/register")
public String register() {
    return "register";
}
@PostMapping("/register")
public String postMethodName(Model model, @RequestParam("username") String username,
		@RequestParam("password") String password,
		@RequestParam("fullname") String fullname) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setFullname(fullname);
	if (userService.register(user)) {
		model.addAttribute("message","Đăng kí thành công !");
	}else {
		model.addAttribute("message","Username đã tồn tại !");
	}
	return "register";
    
}


}

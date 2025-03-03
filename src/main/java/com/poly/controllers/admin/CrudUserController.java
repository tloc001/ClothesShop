package com.poly.controllers.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.models.User;
import com.poly.services.OrderService;
import com.poly.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/admin")
public class CrudUserController {
	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;
	
//	get list user
	@GetMapping("/users")
	public String findAll(Model model, @ModelAttribute("user") User user,@ModelAttribute("editUser") User editUser) {
		model.addAttribute("userList",userService.findAll());
		return "/admin/user-manager";
	}
	@PostMapping("/users/create")
	public String createUser(RedirectAttributes redirect,@ModelAttribute("user") User user) {
		
		if (userService.register(user)) {
			redirect.addFlashAttribute("message","Tạo user thành công !");
		}else {
			redirect.addFlashAttribute("message","Username " + user.getUsername() + " đã tồn tại !");
			redirect.addFlashAttribute("user",user);
		}
		
		return "redirect:/admin/users";
	}
	@PostMapping("/users/update")
	public String UpdateUser(RedirectAttributes redirect,@ModelAttribute("editUser") User editUser) {
		Optional<User> user = userService.findByUsername(editUser.getUsername());
		editUser.setCreatedAt(user.get().getCreatedAt());
		userService.UpdateUser(editUser);
		redirect.addFlashAttribute("message","Cập nhật user thành công !");
		return "redirect:/admin/users";
	}
	@GetMapping("/users/statistics")
	public String getMethodName(Model model) {
		model.addAttribute("sumTotalBillByUser", orderService.sumTotalBillByUser());
		return "/admin/user-statistics";
	}
	
	
	
	
}

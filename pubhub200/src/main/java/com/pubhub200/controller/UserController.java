package com.pubhub200.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pubhub200.dao.UserDAO;
import com.pubhub200.model.User;



@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDAO userDAO;
	@GetMapping("/login")
	public String login(){
		return"login";
	}
	@GetMapping("/welcome")
	public String welcome(){
		return "welcome";
	}
	
	@PostMapping("/validate")
	public String showLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap map) throws SQLException {
		if (userDAO.login(email, password) == true) {
			return  "redirect:../users/list";
		} else {
			return  "redirect:../users/list";
		}

	}


	@GetMapping("/list")
	public String list(ModelMap modelMap) {

		System.out.println("UserController -> list");
		List<User> userList = userDAO.findAll();
		modelMap.addAttribute("USER_LIST", userList);

		return "user_list";
	}

	@GetMapping("/create")
	public String create() {
		return "add_user";
	}

	@PostMapping("/save")
	public String save(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, ModelMap map) {

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		System.out.println(user);

		userDAO.save(user);

		return "redirect:/users/list";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Integer id, ModelMap model) {

		User user = userDAO.findOne(id);
		model.addAttribute("USER_DETAIL", user);
		return "update_user";
	}


	
	@PostMapping("/update")
	public String update(@RequestParam("id")Integer id, @RequestParam("name") String name, @RequestParam("email")String email,
			@RequestParam("password") String password ){
		
		User user = userDAO.findOne(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		
		userDAO.update(user);
		
		return "redirect:/users/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id")Integer id ){		
		userDAO.delete(id);		
		return "redirect:/users/list";
	}
}

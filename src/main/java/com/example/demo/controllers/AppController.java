package com.example.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserServices;

@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private UserServices userServices;
     
    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        userServices.register(user, getSiteURL(request));       
        return "register_success";
    }
     
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }   
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
         
        return "users";
    }
    
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userServices.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
    
    @GetMapping("/login")
	public String showLoginForm() {
		
		return "users";
	}
}
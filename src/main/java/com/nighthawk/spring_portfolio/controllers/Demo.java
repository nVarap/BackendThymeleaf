package com.nighthawk.spring_portfolio.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nighthawk.hacks.classDataStruct.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.jwt.JwtApiController;
import com.nighthawk.spring_portfolio.mvc.jwt.JwtTokenUtil;

@Controller
public class Demo {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired
	private PersonDetailsService personDetailsService;

    @GetMapping("/demo")
    public String showLoginPage() {
        return "demo";
    }

    @PostMapping("/demologin")
    public String processLogin(
    @RequestParam(name = "username", required = false, defaultValue = "defaultUser") String username,
    @RequestParam(name = "password", required = false, defaultValue = "defaultPassword") String password,
    Model model) {
        // Prepare the authentication request
        try{
        final UserDetails userDetails = personDetailsService
				.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);
		final ResponseCookie tokenCookie = ResponseCookie.from("jwt", token)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(3600)
			.sameSite("None; Secure")
			// .domain("example.com") // Set to backend domain
			.build();

            ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).build();

            
        }
        catch(Exception e){
            return "demo";
        }

        return "redirect:home";
    }
}


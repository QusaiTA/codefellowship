package com.example.codefellowship.codefellowship.Controller;


import com.example.codefellowship.codefellowship.models.ApplicationUser;
import com.example.codefellowship.codefellowship.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MainContorller {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/signup")
    public RedirectView attemptSignUp(@RequestParam String username, @RequestParam String password, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String dateOfbirth, @RequestParam String bio) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password),firstname,lastname,dateOfbirth,bio);
        applicationUserRepository.save(newUser);

        Authentication authentication= new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/login");
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model , Principal principal) {
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserByUsername(principal.getName());
        model.addAttribute("username",applicationUser);
        return "profile";
    }

    @GetMapping(value = "users/{id}")
    public String getUserData(@PathVariable Long id,Model model) {
        ApplicationUser user = applicationUserRepository.findApplicationUserById(id);
        model.addAttribute("user",user);

        return "/users";
    }








}

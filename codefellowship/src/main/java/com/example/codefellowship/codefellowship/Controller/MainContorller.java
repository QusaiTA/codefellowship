package com.example.codefellowship.codefellowship.Controller;


import com.example.codefellowship.codefellowship.models.ApplicationUser;
import com.example.codefellowship.codefellowship.models.Post;
import com.example.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.example.codefellowship.codefellowship.repository.PostRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class MainContorller {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepsitory postRepsitory;
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

        return new RedirectView("/profile");
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model , Principal principal) {
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserByUsername(principal.getName());
        model.addAttribute("username",applicationUser);
        return "profile";
    }

    @GetMapping("users/{id}")
    public String getUserData(@PathVariable Long id,Model model) {
        ApplicationUser user = applicationUserRepository.findApplicationUserById(id);
        model.addAttribute("username",user);

        return "profile";
    }
   @GetMapping("/addPost")
   public String createNewPost(){
        return "addPost";
   }

    @PostMapping("/addPost")
    public RedirectView newPost(Model model, Principal principal, String body){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        ApplicationUser newPost = applicationUserRepository.findApplicationUserByUsername(principal.getName());
        Post post = new Post(body,date,newPost);
        postRepsitory.save(post);
        model.addAttribute("posts", newPost.getPosts());
        return new RedirectView("/profile");
    }

    @PostMapping("/follow/{id}")
    public RedirectView follow(Principal p,@PathVariable (value = "id")Long id){
        ApplicationUser myFollower=applicationUserRepository.findApplicationUserByUsername(p.getName());
        ApplicationUser following=applicationUserRepository.findById(id).get();

        myFollower.getFollowing().add(following);
        following.getFollowers().add(myFollower);
        applicationUserRepository.save(myFollower);
        applicationUserRepository.save(following);
        return new RedirectView("/feed");

    }

//    @GetMapping ("/feed" , RequestMethod.GET)
    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String feed(Principal principal,Model model){
        ApplicationUser myfeed=applicationUserRepository.findApplicationUserByUsername(principal.getName());
        Set<ApplicationUser> following=myfeed.getFollowing();
        model.addAttribute("following",following);
        model.addAttribute("username",myfeed.getUsername());
        return "feed";
    }
    @GetMapping("/findfollowers")
    public String findfollowers(Model model,Principal principal){
        List<ApplicationUser> allUsers=applicationUserRepository.findAll();
        ApplicationUser myfeed=applicationUserRepository.findApplicationUserByUsername(principal.getName());
        Set<ApplicationUser> following=myfeed.getFollowing();
        model.addAttribute("actual",applicationUserRepository.findApplicationUserByUsername(principal.getName()).getId());
        model.addAttribute("users",allUsers);
        model.addAttribute("myFollowers",following);
        model.addAttribute("username",myfeed);


        return "searchUsers";
    }







}

package com.week4.login.week4.controller;
import com.week4.login.week4.entity.Login;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class  LoginController {

    @GetMapping("/login") //to check if the user is already logged in and to prevent showing the login screen again.
    public String showLogin(HttpSession session, @CookieValue(value = "session", defaultValue = "null") String sessionCookie, HttpServletResponse response){

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");


        if (session.getAttribute("username") != null && !sessionCookie.equals("null")) {
            System.out.println("ALREADY LOGGED IN");
            return "redirect:/home";
        }

        return "login-page";
    }

    //check credentials
    @PostMapping("/login") //checking credentials
    public String login( Login login, Model model, HttpSession session, HttpServletResponse response){
        String uname = login.getUsername();
        String pass = login.getPassword();

        if(uname.equals("sachin") && pass.equals("Mpik|@27c(")){
            session.setAttribute("username", login.getUsername());
            Cookie cookie = new Cookie("session", login.getUsername());
            cookie.setMaxAge(60);

            response.addCookie(cookie);

            System.out.println("LOGGED IN");

            return "redirect:/home";
        }

        System.out.println("WRONG USERNAME/PASSWORD");
        model.addAttribute("error", "Incorrect credentials");
        return "login-page";

    }


    @GetMapping("/home")
    public String home(Model model, HttpSession session, HttpServletResponse response, @CookieValue(value = "session", defaultValue = "null") String cookie) {
        // Check if user is logged in
        System.out.println("Cookie is " + cookie);
        System.out.println("Session is " + session.getAttribute("username"));
        if (session.getAttribute("username") == null || cookie.equals("null")) {
            return "redirect:/login";
        }

        // If user is logged in, display the home page
        String username = (String) session.getAttribute("username");
        model.addAttribute("uname", username);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return "home-page";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {

        // Invalidate the session
        session.invalidate();

        // Delete the session cookie by setting its max age to 0
        Cookie sessionCookie = new Cookie("session", "");
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
        System.out.println("LOGGED OUT");
        return "redirect:/login";
    }

    @GetMapping("/random")
    public String showRandom(HttpSession session, HttpServletResponse response, @CookieValue(value = "session", defaultValue = "null") String sessionCookie){
        if (session.getAttribute("username") == null || sessionCookie.equals("null")) {
            System.out.println("RANDOM logged out");
            return "redirect:/login";
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        System.out.println("RANDOM");
        return "random";
    }
}

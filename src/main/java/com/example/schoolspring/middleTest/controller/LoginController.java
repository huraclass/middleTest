package com.example.schoolspring.middleTest.controller;

import com.example.schoolspring.middleTest.domain.LoginForm;
import com.example.schoolspring.middleTest.domain.Member;
import com.example.schoolspring.middleTest.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private String macAddr;

    private final LoginService loginService;

    @PostConstruct
    private void getMacAddr() {
        String result = "";
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            result = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


        this.macAddr =  result;
    }

    @GetMapping("/middleindex")
    public String middleIndex(Model model) {
        model.addAttribute("macAddr", macAddr);
        return "login/middleIndex";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpServletResponse response) {
        Member loginMember = loginService.login(loginForm);
        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        return "redirect:/boards";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute LoginForm loginForm) {
        loginService.signUp(loginForm);
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "index";
    }
}

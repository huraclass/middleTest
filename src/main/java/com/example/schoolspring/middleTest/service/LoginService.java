package com.example.schoolspring.middleTest.service;


import com.example.schoolspring.middleTest.domain.LoginForm;
import com.example.schoolspring.middleTest.domain.Member;

public interface LoginService {

    void signUp(LoginForm loginForm);

    Member login(LoginForm loginForm);

}

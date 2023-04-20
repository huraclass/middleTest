package com.example.schoolspring.middleTest.service;

import com.example.schoolspring.mapper.LoginMapper;
import com.example.schoolspring.middleTest.domain.LoginForm;
import com.example.schoolspring.middleTest.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginMapper mapper;

    @Override
    public void signUp(LoginForm loginForm) {
        mapper.signUp(new Member(loginForm.getLoginId(), loginForm.getName(), loginForm.getPassword()));
    }

    @Override
    public Member login(LoginForm loginForm) {
        return mapper.login(loginForm);
    }
}

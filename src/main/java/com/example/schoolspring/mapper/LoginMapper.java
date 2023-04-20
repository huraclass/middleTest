package com.example.schoolspring.mapper;

import com.example.schoolspring.middleTest.domain.LoginForm;
import com.example.schoolspring.middleTest.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    void signUp(Member loginForm);

    Member login(LoginForm loginForm);
}

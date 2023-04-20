package com.example.schoolspring.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class LoginVO {
    private String seq;
    private String id;
    private String pw;
    private String admin;
    private String level;
}

package com.example.schoolspring.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder(builderMethodName = "builder")
public class ErrorResponse {

    private Integer result;
    private String resultDesc;
    private HttpStatus httpStatus;
    private String resDate;
    private String reqNo;
}

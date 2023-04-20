package com.example.schoolspring.middleTest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputForm {
    private long boardNumber;
    private String title;
    private String content;
    private MultipartFile multipartFile;
}

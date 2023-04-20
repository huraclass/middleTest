package com.example.schoolspring.middleTest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileInfo {
    private String realFileName;
    private String serverSaveFileName;

}

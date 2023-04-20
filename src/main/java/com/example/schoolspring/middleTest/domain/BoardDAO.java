package com.example.schoolspring.middleTest.domain;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDAO {
    private Long boardNumber;
    private Long memberId;
    private String title;
    private String content;
    private String serverSaveFileName;
    private String realFileName;

    public BoardDAO transformInputDataToBoardDAO(FileInfo info){
        return null;
    }
}

package com.example.schoolspring.middleTest.domain;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDAO {
    private Long board_number;
    private Long member_id;
    private String title;
    private String content;
    private String server_save_file_name;
    private String real_file_name;

    public BoardDAO transformInputDataToBoardDAO(FileInfo info){
        return null;
    }
}

package com.example.schoolspring.middleTest.service;


import com.example.schoolspring.mapper.BoardMapper;
import com.example.schoolspring.middleTest.domain.BoardDAO;
import com.example.schoolspring.middleTest.domain.FileInfo;
import com.example.schoolspring.middleTest.domain.InputForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardMapper mapper;
    private final FileSave fileStore;

    @Override
    public BoardDAO saveFileBoard(InputForm input, Long memberId) throws IOException {
        //파일저장 및 저장된 파일의 정보 추출
        FileInfo uploadFile = fileStore.saveFile(input.getMultipartFile());
        //데이터 엑세스에 사용되는 DAO객체 생성
        BoardDAO board = BoardDAO.builder()
                .member_id(memberId)
                .title(input.getTitle())
                .content(input.getContent())
                .real_file_name(uploadFile.getRealFileName())
                .server_save_file_name(uploadFile.getServerSaveFileName())
                .build();
        log.info("board : {}",board);
        mapper.saveBoard(board);

        return board;
    }

    @Override
    public BoardDAO saveTextBoard(InputForm input,Long memberId) throws IOException {
        BoardDAO board = BoardDAO.builder()
                .member_id(memberId)
                .title(input.getTitle())
                .content(input.getContent())
                .build();
        log.info("board : {}", board);
        mapper.saveBoard(board);
        return board;
    }


    @Override
    public List<BoardDAO> getAllBoard() {
        return mapper.getAllBoard();
    }

    @Override
    public BoardDAO getBoardById(long id) {
        return mapper.findByBoardId(id);
    }

    @Override
    public void updateBoard(InputForm input,Long memberId) {
        BoardDAO boardDAO = BoardDAO.builder()
                .board_number(input.getBoardNumber())
                .title(input.getTitle())
                .content(input.getContent())
                .member_id(memberId)
                .build();
        log.info("board : {}", boardDAO);
        mapper.updateBoard(boardDAO);
    }

    @Override
    public void deleteBoard(long boardId) {
        mapper.deleteByBoardId(boardId);
    }

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }
}

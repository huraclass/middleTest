package com.example.schoolspring.service;

import com.example.schoolspring.domain.BoardFileDomain;
import com.example.schoolspring.domain.BoardListDomain;
import com.example.schoolspring.vo.FileListVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UploadService {
    public int fileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);

    public List<BoardListDomain> boardList();

    // 하나 삭제
    public void bdContentRemove(HashMap<String, Object> map);

    // 하나 삭제
    public void bdFileRemove(BoardFileDomain boardFileDomain);

    public BoardListDomain boardSelectOne(HashMap<String, Object> map);
    // 하나 파일 리스트 조회
    public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map);

}

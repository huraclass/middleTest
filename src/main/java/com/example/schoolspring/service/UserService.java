package com.example.schoolspring.service;

import com.example.schoolspring.domain.BoardFileDomain;
import com.example.schoolspring.domain.LoginDomain;
import com.example.schoolspring.vo.FileListVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    public LoginDomain mbSelectList(Map<String, String> map);

    // selectAll
    public List<LoginDomain> mbAllList(Map<String, Integer> map);

    // selectAll Conut
    public int mbGetAll();

    //신규
    public void mbCreate(LoginDomain loginDomain);

    //getMbIdCheck
    public LoginDomain mbGetId(Map<String, String> map);

    //mbDuplicationCheck
    public int mbDuplicationCheck(Map<String, String> map);

    //update
    public void mbUpdate(LoginDomain loginDomain);

    //delete
    public void mbRemove(Map<String, String> map);
}

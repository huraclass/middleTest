package com.example.schoolspring.mapper;

import com.example.schoolspring.domain.LoginDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    public LoginDomain mbSelectList(Map<String, String> map);

    //신규 저장
    public void mbCreate(LoginDomain loginDomain);

    //전체데이터
    public List<LoginDomain> mbAllList(Map<String, Integer> map);

    // 전체갯수
    public int mbGetAll();

    //id 정보 가져오기
    public LoginDomain mbGetId(Map<String, String> map);

    //중복체크
    public int mbDuplicationCheck(Map<String, String> map);

    //업데이트
    public void mbUpdate(LoginDomain loginDomain);

    //삭제
    public void mbRemove(Map<String, String> map);
}

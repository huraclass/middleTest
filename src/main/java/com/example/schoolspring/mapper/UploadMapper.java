package com.example.schoolspring.mapper;

import com.example.schoolspring.domain.BoardContentDomain;
import com.example.schoolspring.domain.BoardFileDomain;
import com.example.schoolspring.domain.BoardListDomain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UploadMapper {
    @Select("SELECT bd_seq, mb_id, bd_title, bd_content, bd_create_at, bd_update_at\n" +
            "        FROM board order by bd_update_at desc")
    public List<BoardListDomain> boardList();

    public void contentUpload(BoardContentDomain boardContentDomain);

    @Insert("insert into files(bd_seq,mb_id,up_original_file_name,up_new_file_name,up_file_path,up_file_size)\n" +
            "        values (#{bdSeq},#{mbId},#{upOriginalFileName},#{upNewFileName},#{upFilePath},#{upFileSize});")
    public void fileUpload(BoardFileDomain boardFileDomain);

    public void bdContentUpdate(BoardContentDomain boardContentDomain);

    public void bdFileUpdate(BoardFileDomain boardFileDomain);

    public void bdContentRemove(HashMap<String, Object> map);

    public void bdFileRemove(BoardFileDomain boardFileDomain);

    //select one
    public BoardListDomain boardSelectOne(HashMap<String, Object> map);

    //select one file
    public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map);
}

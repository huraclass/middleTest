package com.example.schoolspring.service;

import com.example.schoolspring.code.Code;
import com.example.schoolspring.domain.BoardContentDomain;
import com.example.schoolspring.domain.BoardFileDomain;
import com.example.schoolspring.domain.BoardListDomain;
import com.example.schoolspring.mapper.UploadMapper;
import com.example.schoolspring.util.CommonUtils;
import com.example.schoolspring.vo.FileListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.schoolspring.exception.RequestException.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadServiceImple implements UploadService{

    private final UploadMapper uploadMapper;

    @Override
    public List<BoardListDomain> boardList() {
        // TODO Auto-generated method stub
        return uploadMapper.boardList();
    }

    @Override
    public int fileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) {
        HttpSession session = httpReq.getSession();
//        log.info("listVO : {}",fileListVO.getSeq());
        //content domain 생성
        BoardContentDomain boardContentDomain = BoardContentDomain.builder()
                .mbId(session.getAttribute("id").toString())
                .bdTitle(fileListVO.getTitle())
                .bdContent(fileListVO.getContent())
                .build();

        if (fileListVO.getIsEdit() != null) {
            boardContentDomain.setBdSeq(Integer.parseInt(fileListVO.getSeq()));
            uploadMapper.bdContentUpdate(boardContentDomain);
        } else {
            uploadMapper.contentUpload(boardContentDomain);
        }
        int bdSeq = boardContentDomain.getBdSeq();
        String mbId = boardContentDomain.getMbId();

        List<MultipartFile> multipartFiles = request.getFiles("files");

        // 게시글 수정시 파일관련 물리저장 파일, db 데이터 삭제
        if (fileListVO.getIsEdit() != null) { // 수정시


            List<BoardFileDomain> fileList = null;


            for (MultipartFile multipartFile : multipartFiles) {


                if (!multipartFile.isEmpty()) {   // 수정시 새로 파일 첨부될때 세션에 담긴 파일 지우기


                    if (session.getAttribute("files") != null) {

                        fileList = (List<BoardFileDomain>) session.getAttribute("files");

                        fileList.forEach(list -> {
                            list.getUpFilePath();
                            Path filePath = Paths.get(list.getUpFilePath());
                            try {

                                // 파일 삭제
                                Files.deleteIfExists(filePath);
                                //삭제
                                bdFileRemove(list);

                            } catch (DirectoryNotEmptyException e) {
                                throw fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    }

                }

            }
        }
        log.info("path");
        Path rootPath = Paths.get(new File("/Users/java/").toString(),"upload", File.separator).toAbsolutePath().normalize();
        File pathCheck = new File(rootPath.toString());
        log.info("path: {}",rootPath.toString());

        //경로가 확인되지 않으면 경로 생성
        if(isNonCheckedPath(pathCheck)) pathCheck.mkdirs();


        for (MultipartFile multipartFile : multipartFiles) {

            if(!multipartFile.isEmpty()) {  // 파일 있을때

                //확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();
                String origFilename = multipartFile.getOriginalFilename();

                if(ObjectUtils.isEmpty(contentType)){
                    break;
                }else { // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if(contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    }else if(contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    }else {
                        break;
                    }
                }

                //파일명을 업로드한 날짜로 변환하여 저장
                String uuid = UUID.randomUUID().toString();
                String current = CommonUtils.currentTime();
                String newFileName = uuid + current + originalFileExtension;

                //최종경로까지 지정
                Path targetPath = rootPath.resolve(newFileName);

                File file = new File(targetPath.toString());

                try {
                    //파일복사저장
                    multipartFile.transferTo(file);
                    // 파일 권한 설정(쓰기, 읽기)
                    file.setWritable(true);
                    file.setReadable(true);


                    //파일 domain 생성
                    BoardFileDomain boardFileDomain = BoardFileDomain.builder()
                            .bdSeq(bdSeq)
                            .mbId(mbId)
                            .upOriginalFileName(origFilename)
                            .upNewFileName("resources/upload/"+newFileName) // WebConfig에 동적 이미지 폴더 생성 했기때문
                            .upFilePath(targetPath.toString())
                            .upFileSize((int)multipartFile.getSize())
                            .build();
                    log.info("fileup");
                    log.info("domain : {}", boardFileDomain);
                    // db 인서트
                    uploadMapper.fileUpload(boardFileDomain);
                    log.info("upload done");

                } catch (IOException e) {
                    throw fire(Code.E404, "잘못된 업로드 파일", HttpStatus.NOT_FOUND);
                }
            }

        }


        return bdSeq; // 저장된 게시판 번호
    }

    private boolean isNonCheckedPath(File pathCheck) {
        return !pathCheck.exists();
    }

    @Override
    public void bdContentRemove(HashMap<String, Object> map) {
        uploadMapper.bdContentRemove(map);
    }

    @Override
    public void bdFileRemove(BoardFileDomain boardFileDomain) {
        uploadMapper.bdFileRemove(boardFileDomain);
    }

    @Override
    public BoardListDomain boardSelectOne(HashMap<String, Object> map) {
        return uploadMapper.boardSelectOne(map);
    }

    // 하나 게시글 파일만 가져오기
    @Override
    public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map) {
        return uploadMapper.boardSelectOneFile(map);
    }
}

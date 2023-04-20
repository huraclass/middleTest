package com.example.schoolspring.middleTest.service;

import com.example.schoolspring.middleTest.domain.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileSave {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        log.info("fileName : {}",fileName);
        return fileDir + fileName;
    }

    public FileInfo saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String serverSaveFileName = createFileName(originalFilename);
        File file = new File(getFullPath(serverSaveFileName));
        Path path = Paths.get(getFullPath(serverSaveFileName)).toAbsolutePath();
        multipartFile.transferTo(path.toFile());
        return new FileInfo(originalFilename, serverSaveFileName);
    }

    private String createFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}

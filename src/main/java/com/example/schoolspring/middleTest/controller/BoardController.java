package com.example.schoolspring.middleTest.controller;

import com.example.schoolspring.middleTest.domain.BoardDAO;
import com.example.schoolspring.middleTest.domain.InputForm;
import com.example.schoolspring.middleTest.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;
    private String macAddr;

    @PostConstruct
    private void getMacAddr() {
        String result = "";
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
                result = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        }

        this.macAddr =  result;
    }
    @GetMapping("/addForm")
    public String addForm(Model model) {
        log.info("addForm");
        model.addAttribute(new InputForm());
        model.addAttribute("macAddr", macAddr);
        return "middleTestBoard/addForm";
    }

    @PostMapping("/addForm")
    public String addFormResult(@ModelAttribute InputForm inputForm, @CookieValue(name = "memberId",required = false) Long memberId) throws IOException {
        log.info("form : {}", inputForm);
        log.info("member : {}",memberId);
        if (inputForm.getMultipartFile().isEmpty()) {
            service.saveTextBoard(inputForm,memberId);
        }
        else{
            service.saveFileBoard(inputForm,memberId);
        }
        return "redirect:/boards";
    }

    @GetMapping("/middleTestBoard/{boardId}")
    public String showDetailBoard(@PathVariable long boardId, Model model) {
        BoardDAO findBoard = service.getBoardById(boardId);
        log.info("board, {}", findBoard);
        model.addAttribute("board",findBoard);
        model.addAttribute("macAddr", macAddr);
        return "middleTestBoard/ShowItem";
    }

    @GetMapping("/boards")
    public String showAllBoards(Model model) {
        List<BoardDAO> boards = service.getAllBoard();
        boards.forEach(boardDAO -> log.info("board : {}",boardDAO));
        model.addAttribute("boards", boards);
        model.addAttribute("macAddr", macAddr);
        return "middleTestBoard/BoardList";
    }

    @GetMapping("/middleTestBoard/update/{boardNumber}")
    public String boardUpdate(@PathVariable Long boardNumber,Model model){
        model.addAttribute("boardNumber", boardNumber);
        return "middleTestBoard/editForm";
    }

    @PostMapping("/middleTestBoard/update/{boardNumber}")
    public String boardPostUpdate(@ModelAttribute InputForm inputForm,@CookieValue(name = "memberId")Long memberId,@PathVariable Long boardNumber, Model model) {
        BoardDAO findBoard = service.getBoardById(boardNumber);
        if (findBoard.getMember_id() != memberId) {
            return "redirect:/boards";
        }
        service.updateBoard(inputForm,memberId);
        return "redirect:/boards";
    }

    @GetMapping("/middleTestBoard/remove/{boardNumber}")
    public String boardRemove(@PathVariable Long boardNumber){
        service.deleteBoard(boardNumber);
        return "redirect:/boards";
    }
    @GetMapping("/attach/{boardNumber}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long boardNumber) throws MalformedURLException {

        log.info("rest 진입");
        BoardDAO item = service.getBoardById(boardNumber);
        String storeFileName = item.getServer_save_file_name();
        String uploadFileName = item.getReal_file_name();

        UrlResource resource = new UrlResource("file:" + getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        log.info("path : {}", contentDisposition);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }


    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        log.info("fileName : {}",fileName);
        return fileDir + fileName;
    }

}

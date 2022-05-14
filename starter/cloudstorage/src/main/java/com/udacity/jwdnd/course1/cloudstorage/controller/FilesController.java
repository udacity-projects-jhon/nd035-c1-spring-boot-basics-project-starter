package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.contract.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FilesController {
    private final FilesService filesService;

    @GetMapping(value = "{fileid}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getById(Authentication authentication, @PathVariable int fileid) {
        var userId = (int) authentication.getPrincipal();
        var file = filesService.getById(userId, fileid);
        return file.getFiledata();
    }

    @PostMapping
    public String create(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile) {
        var userId = (int) authentication.getPrincipal();
        filesService.create(multipartFile, userId);
        return "redirect:/home";
    }

    @DeleteMapping("{fileid}")
    public String delete(Authentication authentication, @PathVariable int fileid) {
        var userId = (int) authentication.getPrincipal();
        filesService.delete(userId, fileid);
        return "redirect:/home";
    }

}

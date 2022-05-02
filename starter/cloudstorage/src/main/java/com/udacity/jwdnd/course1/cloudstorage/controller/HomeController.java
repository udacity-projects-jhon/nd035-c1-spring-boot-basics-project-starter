package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FilesService filesService;

    @GetMapping
    public String getHome(Authentication authentication, @ModelAttribute("note") Note note, Model model) {
        var userId = (int) authentication.getPrincipal();
        model.addAttribute("notes", noteService.getByUserId(userId));
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        model.addAttribute("files", filesService.getFiles(userId));
        return "home";
    }
}

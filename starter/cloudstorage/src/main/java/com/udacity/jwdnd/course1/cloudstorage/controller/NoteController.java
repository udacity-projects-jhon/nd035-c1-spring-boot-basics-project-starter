package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public String create(Authentication authentication, @ModelAttribute("note") Note note) {
        noteService.create((int) authentication.getPrincipal(), note);
        return "redirect:/home";
    }
    @DeleteMapping( "{noteId}")
    public String delete(Authentication authentication, @PathVariable int noteId) {
        var userId = (int) authentication.getPrincipal();
        noteService.delete(userId, noteId);
        return "redirect:/home";
    }
}

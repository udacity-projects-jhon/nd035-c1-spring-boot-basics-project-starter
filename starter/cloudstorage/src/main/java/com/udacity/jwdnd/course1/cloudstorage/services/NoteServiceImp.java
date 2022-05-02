package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImp implements NoteService {
    private final NotesMapper notesMapper;

    @Override
    public List<Note> getByUserId(int userId) {
        return notesMapper.getNotes(userId);
    }


    public int create(int userId, Note note) {
        note.setUserId(userId);
        return notesMapper.createNote(note);
    }
}

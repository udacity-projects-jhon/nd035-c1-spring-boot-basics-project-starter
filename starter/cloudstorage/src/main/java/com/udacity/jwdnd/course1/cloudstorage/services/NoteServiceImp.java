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

    @Override
    public int create(int userId, Note note) {
        note.setUserId(userId);
        return notesMapper.createNote(note);
    }

    @Override
    public Note getByIds(int userId, int noteId) {
        return notesMapper.getNote(userId, noteId);
    }

    @Override
    public void update(Note note) {
        notesMapper.updateNote(note);
    }

    @Override
    public void delete(int userId, int noteId) {
        notesMapper.deleteNote(userId, noteId);
    }

}

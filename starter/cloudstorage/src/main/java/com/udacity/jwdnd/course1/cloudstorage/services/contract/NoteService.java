package com.udacity.jwdnd.course1.cloudstorage.services.contract;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getByUserId(int userId);

    int create(int userId, Note note);
}

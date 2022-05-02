package com.udacity.jwdnd.course1.cloudstorage.services.contract;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;

public interface FilesService {
    List<File> getFiles(int userId);
}

package com.udacity.jwdnd.course1.cloudstorage.services.contract;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FilesService {
    List<File> getFiles(int userId);

    int create(MultipartFile multipartFile, int userId);

    void delete(int userId, int fileid);

    File getById(int userId, int fileid);
}

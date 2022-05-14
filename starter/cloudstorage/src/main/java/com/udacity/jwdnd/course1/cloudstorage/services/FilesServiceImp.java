package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.FilesService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilesServiceImp implements FilesService {
    private final FilesMapper filesMapper;

    @Override
    public List<File> getFiles(int userId) {
        return filesMapper.getFiles(userId);
    }
    @Override
    @SneakyThrows
    public int create(MultipartFile multipartFile, int userId) {
        var file = new File();
        file.setFilename(multipartFile.getName());
        file.setContenttype(multipartFile.getContentType());
        file.setFilesize(Long.toString(multipartFile.getSize()));
        file.setFiledata(multipartFile.getBytes());
        file.setUserid(userId);

        return filesMapper.create(file);
    }

    @Override
    public void delete(int userId, int fileid) {
        filesMapper.delete(userId, fileid);
    }

    @Override
    public File getById(int userId, int fileid) {
        return filesMapper.getById(userId, fileid);
    }
}

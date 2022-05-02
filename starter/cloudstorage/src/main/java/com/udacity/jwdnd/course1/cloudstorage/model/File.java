package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class File {
    private Integer fileid;
    private String fileName;
    private String contenttype;
    private String filesize;
    private String filedata;
}

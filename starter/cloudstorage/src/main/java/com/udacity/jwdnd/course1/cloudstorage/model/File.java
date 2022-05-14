package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class File {

    private Integer fileid;

    private String filename;

    private String contenttype;

    private String filesize;

    private byte[] filedata;

    private Integer userid;
}

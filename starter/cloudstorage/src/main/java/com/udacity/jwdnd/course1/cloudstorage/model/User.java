package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer userId;

    private String username;

    private String firstName;

    private String lastName;

    private String salt;

    private String password;
}

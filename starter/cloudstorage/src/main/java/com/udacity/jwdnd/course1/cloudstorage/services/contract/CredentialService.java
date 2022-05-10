package com.udacity.jwdnd.course1.cloudstorage.services.contract;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

public interface CredentialService {

    List<Credential> getCredentials(int userId);

    int create(int userId, Credential credential);

    Credential getByIds(int userId, int credentialId);

    void delete(int userId, int credentialId);

    void update(Credential credential);
}

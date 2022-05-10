package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialServiceImp implements CredentialService {
    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    @Override
    public List<Credential> getCredentials(int userId) {
        return credentialsMapper.getCredentials(userId);
    }

    @Override
    public int create(int userId, Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        Credential newCredential = new Credential();
        newCredential.setUrl(credential.getUrl());
        newCredential.setUsername(credential.getUsername());
        newCredential.setKey(encodedSalt);
        newCredential.setPassword(hashedPassword);
        newCredential.setUserid(userId);

        return credentialsMapper.create(newCredential);
    }

    @Override
    public Credential getByIds(int userId, int credentialId) {
        var credential = credentialsMapper.getCredential(userId, credentialId);
        var password = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(password);
        return credential;
    }

    @Override
    public void delete(int userId, int noteId) {
        credentialsMapper.delete(userId, noteId);
    }

    @Override
    public void update(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);
        credential.setPassword(hashedPassword);
        credential.setKey(encodedSalt);
        credentialsMapper.updateCredential(credential);
    }
}

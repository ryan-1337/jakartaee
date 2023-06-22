package org.eclipse.jakarta.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.jakarta.dao.AuthDao;

import java.security.SecureRandom;
import java.util.Base64;

@RequestScoped
public class AuthService {
    @Inject
    AuthDao authDao;
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    public boolean authenticate(String username, String password) {
        return authDao.isValidUser(username, password);
    }

    public String generateToken(String username) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

package com.aceleradev.studyrats.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Value("${security.pepper}")
    private String pepper;

    private final Argon2 argon2 = Argon2Factory.create();

    public String hashPassword(String password){

        String peppered = password + pepper;

        return argon2.hash(
                4,
                65536,
                1,
                peppered.toCharArray()
        );
    }

    public boolean verify(String password, String hash){

        String peppered = password + pepper;

        return argon2.verify(hash, peppered.toCharArray());
    }
}

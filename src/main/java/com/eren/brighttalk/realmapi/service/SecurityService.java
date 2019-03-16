package com.eren.brighttalk.realmapi.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    public String genereateNewKey(String name) {
        //Genereate new random encrypt key for user name.
        return UUID.randomUUID().toString();
    }
}

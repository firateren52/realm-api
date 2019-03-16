package com.eren.brighttalk.realmapi.exception;

import org.springframework.http.HttpStatus;

public class RealmNotFoundException extends AbscratRealmException {
    @Override
    public String getCode() {
        return "RealmNotFound";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

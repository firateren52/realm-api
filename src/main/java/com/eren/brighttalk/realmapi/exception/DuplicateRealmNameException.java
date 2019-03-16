package com.eren.brighttalk.realmapi.exception;

import org.springframework.http.HttpStatus;

public class DuplicateRealmNameException extends AbscratRealmException {
    @Override
    public String getCode() {
        return "DuplicateRealmName";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

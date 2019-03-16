package com.eren.brighttalk.realmapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidRealmNameException extends AbscratRealmException {

    @Override
    public String getCode() {
        return "InvalidRealmName";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

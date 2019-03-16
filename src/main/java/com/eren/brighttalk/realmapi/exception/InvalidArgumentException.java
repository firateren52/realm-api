package com.eren.brighttalk.realmapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends AbscratRealmException {
    @Override
    public String getCode() {
        return "InvalidArgument";
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

package com.eren.brighttalk.realmapi.exception;

import org.springframework.http.HttpStatus;

public abstract class AbscratRealmException extends RuntimeException {
    public abstract String getCode();
    public abstract HttpStatus getHttpStatus();
}

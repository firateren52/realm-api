package com.eren.brighttalk.realmapi.exception;

import lombok.Data;

@Data
public class Error {
    private final String code;

    public Error(String code) {
        this.code = code;
    }
}

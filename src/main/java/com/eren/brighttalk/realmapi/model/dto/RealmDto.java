package com.eren.brighttalk.realmapi.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class RealmDto {
    private String name;
    private String description;

    public RealmDto() {
    }

    public RealmDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

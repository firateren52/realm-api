package com.eren.brighttalk.realmapi.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_REALM", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "uniqueNameConstraint")})
@Data
@EqualsAndHashCode
@ToString
public class Realm {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String key;

    public Realm() {
    }

    public Realm(String name, String description, String key) {
        this.name = name;
        this.description = description;
        this.key = key;
    }
}

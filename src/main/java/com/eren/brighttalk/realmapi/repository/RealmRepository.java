package com.eren.brighttalk.realmapi.repository;

import com.eren.brighttalk.realmapi.model.entity.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {
}

package com.eren.brighttalk.realmapi.service;

import com.eren.brighttalk.realmapi.exception.*;
import com.eren.brighttalk.realmapi.model.dto.RealmDto;
import com.eren.brighttalk.realmapi.model.entity.Realm;
import com.eren.brighttalk.realmapi.repository.RealmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class RealmService {
    private final int DESCRIPTION_MAX_SIZE = 255;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private RealmRepository realmRepository;

    public Realm get(final String id) {
        try {
            Long idNumber = Long.valueOf(id);
            return realmRepository.findById(idNumber).orElseThrow(() -> new RealmNotFoundException());
        } catch (NumberFormatException ex) {
            log.error("invalid realm id: " + id, ex);
            throw new InvalidArgumentException();
        }
    }

    public Realm save(final RealmDto realmDto) {
        if (StringUtils.isEmpty(realmDto.getName())) {
            throw new InvalidRealmNameException();
        }
        if (StringUtils.isEmpty(realmDto.getDescription()) || realmDto.getDescription().length() > DESCRIPTION_MAX_SIZE) {
            throw new IllegalArgumentException("description is not valid description: '" + realmDto.getDescription() + "'");
        }

        String key = securityService.genereateNewKey(realmDto.getName());
        Realm realm = new Realm(realmDto.getName(), realmDto.getDescription(), key);

        try {
            return realmRepository.save(realm);
        } catch (DataIntegrityViolationException ex) {
            log.error("duplicate realm name: " + realmDto.getName(), ex);
            throw new DuplicateRealmNameException();
        }
    }
}
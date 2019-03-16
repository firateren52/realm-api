package com.eren.brighttalk.realmapi.service;

import com.eren.brighttalk.realmapi.RealmApiApplication;
import com.eren.brighttalk.realmapi.exception.DuplicateRealmNameException;
import com.eren.brighttalk.realmapi.exception.InvalidArgumentException;
import com.eren.brighttalk.realmapi.exception.InvalidRealmNameException;
import com.eren.brighttalk.realmapi.exception.RealmNotFoundException;
import com.eren.brighttalk.realmapi.model.dto.RealmDto;
import com.eren.brighttalk.realmapi.model.entity.Realm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RealmApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RealmServiceTest {

    @Autowired
    private RealmService realmService;

    @Test
    public void get_givenIdIsValid_thenReturnRealm() {
        // create new realm
        String id = "45";
        RealmDto realmDto = new RealmDto("name_" + id, "description_" + id);
        Realm realm = saveAndAssertRealm(realmDto, true);

        // when
        Realm savedRealm = realmService.get(realm.getId().toString());

        // then
        assertThat(savedRealm).isEqualToComparingFieldByFieldRecursively(realm);
    }

    @Test(expected = InvalidArgumentException.class)
    public void get_givenIdIsNotValid_thenThrowInvalidArgumentException() {
        String id = "invalid_number";

        // when
        Realm savedRealm = realmService.get(id);
    }

    @Test(expected = RealmNotFoundException.class)
    public void get_givenIdIsNotValid_thenThrowRealmNotFoundException() {
        String id = "353423432";

        // when
        Realm savedRealm = realmService.get(id);
    }

    @Test
    public void save_givenValidParams_thenSaveAndReturnRealm() {
        // given
        String id = "343";
        RealmDto realmDto = new RealmDto("name_" + id, "description_" + id);
        saveAndAssertRealm(realmDto, true);
    }

    @Test(expected = InvalidRealmNameException.class)
    public void save_givenNullName_thenThrowInvalidRealmNameException() {
        // given
        RealmDto realmDto = new RealmDto(null, "description");
        saveAndAssertRealm(realmDto, false);
    }

    @Test(expected = DuplicateRealmNameException.class)
    public void save_givenNullName_thenThrowDuplicateRealmNameException() {
        // given
        String id = "645";
        RealmDto realmDto = new RealmDto("name_" + id, "description_" + id);
        saveAndAssertRealm(realmDto, true);
        saveAndAssertRealm(realmDto, true);
    }

    private Realm saveAndAssertRealm(RealmDto realmDto, boolean doAssert) {
        // when
        Realm realm = realmService.save(realmDto);

        // then
        if(doAssert) {
            assertThat(realm.getId()).isNotNull().isGreaterThan(0L);
            assertThat(realm.getName()).isEqualTo(realmDto.getName());
            assertThat(realm.getDescription()).isEqualTo(realmDto.getDescription());
            assertThat(realm.getKey()).isNotEmpty();
        }
        return  realm;
    }

}
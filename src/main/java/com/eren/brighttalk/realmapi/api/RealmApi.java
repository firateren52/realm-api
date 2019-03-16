package com.eren.brighttalk.realmapi.api;


import com.eren.brighttalk.realmapi.exception.DuplicateRealmNameException;
import com.eren.brighttalk.realmapi.exception.InvalidArgumentException;
import com.eren.brighttalk.realmapi.exception.InvalidRealmNameException;
import com.eren.brighttalk.realmapi.exception.RealmNotFoundException;
import com.eren.brighttalk.realmapi.model.dto.RealmDto;
import com.eren.brighttalk.realmapi.model.entity.Realm;
import com.eren.brighttalk.realmapi.service.RealmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/user/realm")
public class RealmApi {
    @Autowired
    private RealmService realmService;

    @GetMapping("/{id}")
    @ResponseStatus( HttpStatus.OK )
    public Realm get(@PathVariable("id") String id) throws RealmNotFoundException, InvalidArgumentException {
        return realmService.get(id);
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public Realm save(@RequestBody RealmDto realmDto) throws DuplicateRealmNameException, InvalidRealmNameException {
        return realmService.save(realmDto);
    }
}

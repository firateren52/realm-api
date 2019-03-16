package com.eren.brighttalk.realmapi.api;

import com.eren.brighttalk.realmapi.exception.DuplicateRealmNameException;
import com.eren.brighttalk.realmapi.exception.InvalidArgumentException;
import com.eren.brighttalk.realmapi.exception.InvalidRealmNameException;
import com.eren.brighttalk.realmapi.exception.RealmNotFoundException;
import com.eren.brighttalk.realmapi.model.dto.RealmDto;
import com.eren.brighttalk.realmapi.model.entity.Realm;
import com.eren.brighttalk.realmapi.service.RealmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(Parameterized.class)
@WebMvcTest
@Slf4j
public class RealmApiUnitTest {

    private final String REALM_API_URL = "/service/user/realm/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RealmService realmService;

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    private MediaType mediaType;

    public RealmApiUnitTest(MediaType mediaType) {
        log.info("RealmApiUnitTest run with mediaType: " + mediaType);
        this.mediaType = mediaType;
    }

    @Parameterized.Parameters
    public static List<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {MediaType.APPLICATION_JSON},
                {MediaType.parseMediaType("application/xml; charset=utf-8")},
        });
    }

    @Test
    public void get_givenIdIsValid_thenReturnRealm() throws Exception {
        // given
        String id = "3210";
        Realm mockRealm = createRealm(id);
        given(realmService.get(id)).willReturn(mockRealm);

        // when
        String response = mockMvc.perform(get(REALM_API_URL + id)
                .contentType(mediaType)
                .accept(mediaType))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains(mockRealm.getId().toString()).contains(mockRealm.getName())
                .contains(mockRealm.getDescription()).contains(mockRealm.getKey());
    }

    @Test
    public void get_givenIdIsValid_thenReturnRealm2() throws Exception {
        // given
        String id = "3211";
        Realm mockRealm = createRealm(id);
        given(realmService.get(id)).willReturn(mockRealm);

        // when
        String response = mockMvc.perform(get(REALM_API_URL + id)
                .contentType(mediaType)
                .accept(mediaType))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains(mockRealm.getId().toString()).contains(mockRealm.getName())
                .contains(mockRealm.getDescription()).contains(mockRealm.getKey());
    }

    @Test
    public void get_givenIdIsNotValid_thenReturnInvalidArgumentError() throws Exception {
        // given
        String id = "3220";
        given(realmService.get(id)).willThrow(InvalidArgumentException.class);

        // when
        String response = mockMvc.perform(get(REALM_API_URL + id)
                .contentType(mediaType)
                .accept(mediaType))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("InvalidArgument");
    }

    @Test
    public void get_givenIdIsNotValid_thenReturnRealmNotFoundError() throws Exception {
        // given
        String id = "3221";
        given(realmService.get(id)).willThrow(RealmNotFoundException.class);

        // when
        String response = mockMvc.perform(get(REALM_API_URL + id)
                .contentType(mediaType)
                .accept(mediaType))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("RealmNotFound");
    }

    @Test
    public void save_givenValidParams_thenReturnRealm() throws Exception {
        // given
        String id = "3212";
        RealmDto realmDto = createRealmDto(id);
        Realm mockRealm = createRealm(id);
        given(realmService.save(realmDto)).willReturn(mockRealm);

        // when
        String response = mockMvc.perform(post(REALM_API_URL)
                .content(createRequestBody(realmDto))
                .accept(mediaType)
                .contentType(mediaType))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains(mockRealm.getId().toString()).contains(mockRealm.getName())
                .contains(mockRealm.getDescription()).contains(mockRealm.getKey());
    }

    @Test
    public void save_givenNullName_thenReturnInvalidRealmNameError() throws Exception {
        // given
        String id = "3213";
        RealmDto realmDto = new RealmDto(null, "description_" + id);
        given(realmService.save(realmDto)).willThrow(InvalidRealmNameException.class);

        // when
        String response = mockMvc.perform(post(REALM_API_URL)
                .content(createRequestBody(realmDto))
                .accept(mediaType)
                .contentType(mediaType))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("InvalidRealmName");
    }

    @Test
    public void save_givenNullName_thenReturnDuplicateRealmNameError() throws Exception {
        // given
        String id = "3214";
        RealmDto realmDto = createRealmDto(id);
        given(realmService.save(realmDto)).willThrow(DuplicateRealmNameException.class);

        // when
        String response = mockMvc.perform(post(REALM_API_URL)
                .content(createRequestBody(realmDto))
                .accept(mediaType)
                .contentType(mediaType))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("DuplicateRealmName");
    }

    @Test
    public void save_givenInvalidDescription_thenReturnDefaultError() throws Exception {
        // given
        String id = "3213";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a sollicitudin dui. Phasellus " +
                "nec elementum lorem. Vestibulum ullamcorper ligula et nibh ultricies, at tempus magna accumsan. Praesent " +
                "imperdiet nibh eget tellus efficitur, volutpat sodales nisl vehicula. Sed felis nisi, ultricies ullamcorper " +
                "eleifend in, finibus a turpis. Donec et eros eget mauris consectetur viverra. Sed eget vestibulum justo. " +
                "Sed at hendrerit massa, a volutpat velit. Nulla mi orci, porttitor nec porttitor non, interdum quis nibh. " +
                "Suspendisse potent";
        RealmDto realmDto = new RealmDto(null, description);
        given(realmService.save(realmDto)).willThrow(IllegalArgumentException.class);

        // when
        mockMvc.perform(post(REALM_API_URL)
                .content(createRequestBody(realmDto))
                .accept(mediaType)
                .contentType(mediaType))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();
    }

    private RealmDto createRealmDto(String id) {
        return new RealmDto("name_" + id, "description_" + id);
    }

    private Realm createRealm(String id) {
        Realm realm = new Realm("name_" + id, "description_" + id, "key_" + id);
        realm.setId(Long.valueOf(id));
        return realm;
    }

    private String createRequestBody(final Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper getObjectMapper() {
        if (MediaType.APPLICATION_JSON.equals(mediaType)) {
            return new ObjectMapper();
        } else {
            return new XmlMapper();
        }
    }

}

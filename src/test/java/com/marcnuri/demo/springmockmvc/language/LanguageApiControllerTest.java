/*
 * SpringMockMvcApiControllerTest.java
 *
 * Created on 2018-05-15, 7:39
 */
package com.marcnuri.demo.springmockmvc.language;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marcnuri.demo.springmockmvc.SpringMockMvcException;
import com.marcnuri.demo.springmockmvc.language.LanguageApiControllerTest.Config;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;


/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    LanguageApiController.class
})
@Import(Config.class)
public class LanguageApiControllerTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  @Autowired
  private LanguageService languageService;

  @Autowired
  private LanguageApiController languageApiController;

  private MockMvc mockMvc;

//**************************************************************************************************
//  Test preparation
//**************************************************************************************************
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(languageApiController)
        .build();
  }

  @After
  public void tearDown() {
    mockMvc = null;
  }

//**************************************************************************************************
//  Tests
//**************************************************************************************************
  @Test
  public void getLanguages_null_shouldReturnOk() throws Exception {
    // Given
    final Language mockedEsoteric = new Language("Arnoldc", "Lauri Hartikka");
    final List<Language> mockedLanguages = Stream.concat(
        LanguageRepository.LANGUAGES.stream(),
        Stream.of(mockedEsoteric)).collect(Collectors.toList());
    doReturn(mockedLanguages).when(languageService).getLanguages(null);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages")
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.length()").value(mockedLanguages.size()));
    result.andExpect(jsonPath("$[?(@.name === 'Arnoldc')]").exists());
  }

  @Test
  public void getLanguages_invalidInjectParam_shouldReturnBadRequest() throws Exception {
    // Given
    final String containsParam = "' or 1=1;/*";
    final HttpStatus mockedStatus = HttpStatus.BAD_REQUEST;
    doThrow(new SpringMockMvcException(mockedStatus, "Welcome to the new century"))
        .when(languageService).getLanguages(containsParam);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages").param("contains", containsParam)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().is(mockedStatus.value()));
  }

  @Test
  public void getLanguages_invalidAcceptHeader_shouldReturnNotAcceptable() throws Exception {
    // Given
    final String invalidAcceptMimeType = MimeTypeUtils.APPLICATION_XML_VALUE;
    doReturn(LanguageRepository.LANGUAGES).when(languageService).getLanguages(null);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages")
            .accept(invalidAcceptMimeType));

    // Then
    result.andExpect(status().isNotAcceptable());
  }

  @Test
  public void getLanguage_existingLanguage_shouldReturnOk() throws Exception {
    // Given
    final String mockedLanguageName = "Arnoldc";
    final Language mockedEsoteric = new Language(mockedLanguageName, "Lauri Hartikka");
    doReturn(Optional.of(mockedEsoteric)).when(languageService).getLanguage(mockedLanguageName);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages/".concat(mockedLanguageName))
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.name", is(mockedLanguageName)));
  }

  @Test
  public void getLanguage_nonExistingLanguage_shouldReturnNotFound() throws Exception {
    // Given
    final String mockedLanguageName = "Arnoldc";
    doReturn(Optional.empty()).when(languageService).getLanguage(mockedLanguageName);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages/".concat(mockedLanguageName))
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().isNotFound());
  }

  @TestConfiguration
  protected static class Config {

    @Bean
    public LanguageService languageService() {
      return Mockito.mock(LanguageService.class);
    }

  }

}

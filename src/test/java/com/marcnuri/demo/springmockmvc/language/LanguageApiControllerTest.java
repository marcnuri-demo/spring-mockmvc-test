/*
 * SpringMockMvcApiControllerTest.java
 *
 * Created on 2018-05-15, 7:39
 */
package com.marcnuri.demo.springmockmvc.language;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marcnuri.demo.springmockmvc.SpringMockMvcException;
import com.marcnuri.demo.springmockmvc.language.LanguageApiControllerTest.SpringMockMvcApiControllerTestConfiguration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;


/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
    LanguageApiController.class
})
@Import(SpringMockMvcApiControllerTestConfiguration.class)
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
    final String mockedEsoteric = "Arnoldc";
    final List<String> mockedLanguages = Stream.concat(
        LanguageRepository.LANGUAGES.stream(),
        Stream.of(mockedEsoteric)).collect(Collectors.toList());
    Mockito.when(languageService.getLanguages(null)).thenReturn(mockedLanguages);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages")
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.length()").value(mockedLanguages.size()));
    result.andExpect(jsonPath("$", hasItem(mockedEsoteric)));
  }

  @Test
  public void getLanguages_invalidInjectParam_shouldReturnException() throws Exception {
    // Given
    final String containsParam = "' or 1=1;/*";
    final HttpStatus mockedStatus = HttpStatus.BAD_REQUEST;
    Mockito.when(languageService.getLanguages(containsParam))
        .thenThrow(new SpringMockMvcException(mockedStatus, "Welcome to the new century"));

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages").param("contains", containsParam)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().is(mockedStatus.value()));
  }

  @Test
  public void getLanguages_invalidAcceptHeader_shouldReturnException() throws Exception {
    // Given
    final String invalidAcceptMimeType = MimeTypeUtils.APPLICATION_XML_VALUE;
    Mockito.when(languageService.getLanguages(null)).thenReturn(LanguageRepository.LANGUAGES);

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages")
            .accept(invalidAcceptMimeType));

    // Then
    result.andExpect(status().isNotAcceptable());
  }

  @Configuration
  protected static class SpringMockMvcApiControllerTestConfiguration {

    @Bean
    public LanguageService springMockMvcService() {
      return Mockito.mock(LanguageService.class);
    }

  }

}

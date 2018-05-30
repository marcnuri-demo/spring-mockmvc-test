/*
 * LanguageApiControllerStrictUnitTest.java
 *
 * Created on 2018-05-29, 6:46
 */
package com.marcnuri.demo.springmockmvc.language;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-29.
 */
public class LanguageApiControllerStrictUnitTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  private LanguageService languageService;
  private LanguageApiController languageApiController;


//**************************************************************************************************
//  Test preparation
//**************************************************************************************************
  @Before
  public void setUp() {
    languageService = Mockito.mock(LanguageService.class);
    languageApiController = new LanguageApiController(languageService);
  }

  @After
  public void tearDown() {
    languageApiController = null;
    languageService = null;
  }

//**************************************************************************************************
//  Tests
//**************************************************************************************************
  @Test
  public void getLanguages_null_shouldReturnListOfStrings() throws Exception {
    // Given
    final Language mockedEsoteric = new Language("Arnoldc", "Lauri Hartikka");
    final List<Language> mockedLanguages = Stream.concat(
        LanguageRepository.LANGUAGES.stream(),
        Stream.of(mockedEsoteric)).collect(Collectors.toList());
    doReturn(mockedLanguages).when(languageService).getLanguages(null);

    // When
    final ResponseEntity<List<Language>> result = languageApiController.getLanguages(null);

    // Then
    assertThat(result.getBody(), hasSize(mockedLanguages.size()));
    assertThat(result.getBody(), hasItem(mockedEsoteric));
  }
}

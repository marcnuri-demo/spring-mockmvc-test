/*
 * LanguageServiceTest.java
 *
 * Created on 2018-06-04, 7:06
 */
package com.marcnuri.demo.springmockmvc.language;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-06-04.
 */
@RunWith(JUnit4.class)
public class LanguageServiceTest {

  @Test
  public void getLanguages_null_shouldReturnList() {
    // Given
    final LanguageRepository mockLanguageRepository = Mockito.mock(LanguageRepository.class);
    final Language mockedLanguage = new Language("Arnoldc", "Lauri Hartikka");
    final List<Language> mockedLanguages = Arrays.asList(mockedLanguage);
    doReturn(mockedLanguages).when(mockLanguageRepository).findAll();

    final LanguageService languageService = new LanguageService(mockLanguageRepository);

    // When
    final List<Language> results = languageService.getLanguages(null);

    // Then
    assertThat(results, contains(mockedLanguage));
  }

  @Test
  public void getLanguages_nonExistingLanguageName_shouldReturnEmptyList() {
    // Given
    final LanguageRepository mockLanguageRepository = Mockito.mock(LanguageRepository.class);
    final Language mockedLanguage = new Language("Arnoldc", "Lauri Hartikka");
    final List<Language> mockedLanguages = Arrays.asList(mockedLanguage);
    doReturn(mockedLanguages).when(mockLanguageRepository).findAll();

    final LanguageService languageService = new LanguageService(mockLanguageRepository);

    // When
    final List<Language> results = languageService.getLanguages("ARN");

    // Then
    assertThat(results, Matchers.empty());
  }
}

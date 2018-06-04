/*
 * LanguageRepositoryTest.java
 *
 * Created on 2018-06-04, 6:51
 */
package com.marcnuri.demo.springmockmvc.language;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-06-04.
 */
@RunWith(JUnit4.class)
public class LanguageRepositoryTest {

  @Test
  public void findAll_na_shouldReturnList() {
    // Given
    final LanguageRepository languageRepository = new LanguageRepository();

    // When
    final List<Language> results = languageRepository.findAll();

    // Then
    final int expectedSize = 8;
    final String validLanguageName = "Java";
    assertThat(results, hasSize(expectedSize));
    assertThat(results, hasItem(hasProperty("name", equalTo(validLanguageName))));
  }

  @Test
  public void findByName_existingLanguageName_shouldReturnList() {
    // Given
    final String validLanguageName = "Java";
    final LanguageRepository languageRepository = new LanguageRepository();

    // When
    final Optional<Language> result = languageRepository.findByName(validLanguageName);

    // Then
    assertThat(result.get(), hasProperty("name", equalTo(validLanguageName)));
  }

  @Test(expected = NoSuchElementException.class)
  public void findByName_nonExistingLanguageName_shouldReturnList() {
    // Given
    final String invalidLanguageName = "java";
    final LanguageRepository languageRepository = new LanguageRepository();

    // When
    final Optional<Language> result = languageRepository.findByName(invalidLanguageName);
    result.get();

    // Then
    fail();
  }
}

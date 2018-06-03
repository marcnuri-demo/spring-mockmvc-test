/*
 * LanguageApiControllerWebMvcTest.java
 *
 * Created on 2018-06-03, 23:10
 */
package com.marcnuri.demo.springmockmvc.language;

import static com.marcnuri.demo.springmockmvc.language.LanguageRepository.LANGUAGES;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-06-03.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LanguageApiController.class)
public class LanguageApiControllerWebMvcTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  @Autowired
  private MockMvc mockMvc;

//**************************************************************************************************
//  Tests
//**************************************************************************************************
  @Test
  public void getLanguages_null_shouldReturnOk() throws Exception {
    // Given
    // Real application context

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/languages")
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    final int expectedSize = LANGUAGES.size();
    final String[] expectedLanguageNames = LANGUAGES.stream().map(Language::getName)
        .collect(Collectors.toList()).toArray(new String[LANGUAGES.size()]);
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.length()").value(expectedSize));
    result.andExpect(jsonPath("$[*].name", containsInAnyOrder(expectedLanguageNames)));
  }
}

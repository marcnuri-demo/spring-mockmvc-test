/*
 * LanguageApiControllerApplicationIntegrationTest.java
 *
 * Created on 2018-05-31, 6:50
 */
package com.marcnuri.demo.springmockmvc.language;

import static com.marcnuri.demo.springmockmvc.language.LanguageRepository.LANGUAGES;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marcnuri.demo.springmockmvc.SpringMockMvcConfiguration;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
    SpringMockMvcConfiguration.class
})
public class LanguageApiControllerApplicationIntegrationTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

//**************************************************************************************************
//  Test preparation
//**************************************************************************************************
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
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

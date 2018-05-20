/*
 * SpringMockMvcWebControllerTest.java
 *
 * Created on 2018-05-16, 6:42
 */
package com.marcnuri.demo.springmockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {
    SpringMockMvcWebController.class
})
public class SpringMockMvcWebControllerTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  @Autowired
  private SpringMockMvcWebController springMockMvcWebController;

  private MockMvc mockMvc;

//**************************************************************************************************
//  Test preparation
//**************************************************************************************************
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(springMockMvcWebController)
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
  public void forwardUrl_validUrl_shouldReturnOk() throws Exception {
    // Given

    // When
    final ResultActions result = mockMvc.perform(
        get("/")
            .accept(MimeTypeUtils.TEXT_HTML_VALUE));

    // Then
    result.andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
  }

  @Test
  public void forwardUrl_invalidUrl_shouldReturnNotFound() throws Exception {
    // Given

    // When
    final ResultActions result = mockMvc.perform(
        get("/invalidUrl")
            .accept(MimeTypeUtils.TEXT_HTML_VALUE));

    // Then
    result.andExpect(status().isNotFound());
  }

  @Test
  public void forwardUrl_invalidAccept_shouldReturnOk() throws Exception {
    // Given

    // When
    final ResultActions result = mockMvc.perform(
        get("/")
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().isNotAcceptable());
  }
}

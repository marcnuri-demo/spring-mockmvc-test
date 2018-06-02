/*
 * SpringMockMvcConfigurationIntegrationTest.java
 *
 * Created on 2018-05-16, 7:26
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
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
    SpringMockMvcConfiguration.class
})
public class SpringMockMvcConfigurationIntegrationTest {

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
  public void forwardUrl_validUrl_shouldReturnOk() throws Exception {
    // Given

    // When
    final ResultActions result = mockMvc.perform(
        get("/")
            .accept(MimeTypeUtils.TEXT_HTML_VALUE));

    // Then
    result.andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
  }

}

/*
 * CoffeeApiControllerWithContextTest.java
 *
 * Created on 2018-05-26, 8:36
 */
package com.marcnuri.demo.springmockmvc.coffee;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marcnuri.demo.springmockmvc.coffee.CoffeeApiControllerWithContextTest.CoffeeApiControllerWithContextTestConfiguration;
import com.marcnuri.spring.test.mock.beans.MockBeanConfiguration;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
    CoffeeApiController.class
})
@Import(CoffeeApiControllerWithContextTestConfiguration.class)
public class CoffeeApiControllerWithContextTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  @Autowired
  private CoffeeService coffeeService;

  @Autowired
  private CoffeeApiController coffeeApiController;

  private MockMvc mockMvc;

//**************************************************************************************************
//  Test preparation
//**************************************************************************************************
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(coffeeApiController)
        .build();
  }

  @After
  public void tearDown() {
    coffeeService = null;
  }

//**************************************************************************************************
//  Tests
//**************************************************************************************************
  @Test
  public void getCoffees_na_shouldReturnOk() throws Exception {
    // Given
    final String mockedCoffee = "Kopi luwak";
    final List<String> mockedCoffees = Stream.concat(
        CoffeeRepository.COFFEES.stream(),
        Stream.of(mockedCoffee)).collect(Collectors.toList());
    doReturn(mockedCoffees).when(coffeeService).getCoffees();

    // When
    final ResultActions result = mockMvc.perform(
        get("/api/coffees")
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.length()").value(mockedCoffees.size()));
    result.andExpect(jsonPath("$", hasItem(mockedCoffee)));
  }

  @Configuration
  @Import(MockBeanConfiguration.class)
  protected static class CoffeeApiControllerWithContextTestConfiguration {

    @Bean
    public CoffeeService coffeeService() {
      return Mockito.mock(CoffeeService.class);
    }

  }
}

/*
 * CoffeeApiControllerTest.java
 *
 * Created on 2018-05-20, 19:28
 */
package com.marcnuri.demo.springmockmvc.coffee;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-20.
 */
@RunWith(JUnit4.class)
public class CoffeeApiControllerTest {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  private CoffeeService coffeeService;

  private CoffeeApiController coffeeApiController;

  private MockMvc mockMvc;

//**************************************************************************************************
//  Test preparation
//**************************************************************************************************
  @Before
  public void setUp() {
    coffeeService = Mockito.mock(CoffeeService.class);
    coffeeApiController = new CoffeeApiController(coffeeService);
    mockMvc = MockMvcBuilders
        .standaloneSetup(coffeeApiController)
        .build();
  }

  @After
  public void tearDown() {
    mockMvc = null;
    coffeeApiController = null;
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

}

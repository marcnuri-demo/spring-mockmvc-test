/*
 * CoffeeServiceTest.java
 *
 * Created on 2018-06-03, 23:25
 */
package com.marcnuri.demo.springmockmvc.coffee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.doReturn;

import com.marcnuri.demo.springmockmvc.coffee.CoffeeApiControllerWithContextTest.Config;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-06-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    CoffeeService.class
})
@Import(Config.class)
public class CoffeeServiceTest {

  @Autowired
  private CoffeeRepository coffeeRepository;

  @Autowired
  private CoffeeService coffeeService;

  @Test
  public void getCoffees_na_shouldReturnList() throws Exception {
    // Given
    final String mockedCoffee = "Kopi luwak";
    final List<String> mockedCoffees = Arrays.asList(mockedCoffee);
    doReturn(mockedCoffees).when(coffeeRepository).findAll();

    // When
    final List<String> results = coffeeService.getCoffees();

    // Then
    assertThat(results, contains(mockedCoffee));
  }

  @Configuration
  protected static class Config {

    @Bean
    public CoffeeRepository coffeeRepository() {
      return Mockito.mock(CoffeeRepository.class);
    }

  }
}

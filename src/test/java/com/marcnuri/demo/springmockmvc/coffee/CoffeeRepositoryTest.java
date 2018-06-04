/*
 * CoffeeRepositoryTest.java
 *
 * Created on 2018-06-03, 23:16
 */
package com.marcnuri.demo.springmockmvc.coffee;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-06-03.
 */
@RunWith(JUnit4.class)
public class CoffeeRepositoryTest {

  @Test
  public void findAll_na_shouldReturnList() {
    // Given
    final CoffeeRepository coffeeRepository = new CoffeeRepository();

    // When
    final List<String> results = coffeeRepository.findAll();

    // Then
    final int expectedSize = 10;
    final String validCoffee = "Java";
    assertThat(results, hasSize(expectedSize));
    assertThat(results, hasItem(equalTo(validCoffee)));
  }

}

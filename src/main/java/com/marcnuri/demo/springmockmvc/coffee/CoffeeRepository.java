/*
 * CoffeeRepository.java
 *
 * Created on 2018-05-20, 8:21
 */
package com.marcnuri.demo.springmockmvc.coffee;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-20.
 */
@Repository
public class CoffeeRepository {

protected static final List<String> COFFEES = Arrays.asList("Arusha", "Blue Mountain", "Colombian",
    "Gesha", "Java", "Mocha", "Santos", "S795", "SL34", "Uganda");

  public List<String> findAll() {
    return  COFFEES;
  }

}

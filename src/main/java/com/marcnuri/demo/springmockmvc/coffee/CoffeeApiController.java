/*
 * CoffeeApiController.java
 *
 * Created on 2018-05-20, 8:27
 */
package com.marcnuri.demo.springmockmvc.coffee;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-20.
 */
@Controller
@RequestMapping(value = "/api", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class CoffeeApiController {

  //**************************************************************************************************
//  Fields
//**************************************************************************************************
  private final CoffeeService coffeeService;

  //**************************************************************************************************
//  Constructors
//**************************************************************************************************
  @Autowired
  public CoffeeApiController(CoffeeService coffeeService) {
    this.coffeeService = coffeeService;
  }

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************
  @RequestMapping(value = "/coffees", method = GET)
  public ResponseEntity<List<String>> getCoffees() {
    return ResponseEntity.ok(coffeeService.getCoffees());
  }

}

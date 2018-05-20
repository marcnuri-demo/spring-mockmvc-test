/*
 * SpringMockMvcApiController.java
 *
 * Created on 2018-05-15, 7:32
 */
package com.marcnuri.demo.springmockmvc.language;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.marcnuri.demo.springmockmvc.SpringMockMvcException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-15.
 */
@Controller
@RequestMapping(value = "/api", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class LanguageApiController {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  private final LanguageService languageService;

//**************************************************************************************************
//  Constructors
//**************************************************************************************************
  @Autowired
  public LanguageApiController(
      LanguageService languageService) {
    this.languageService = languageService;
  }

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************
  @RequestMapping(value = "/languages", method = GET)
  public ResponseEntity getLanguages(@RequestParam(value = "contains", required = false) String contains) {
    return ResponseEntity.ok(languageService.getLanguages(contains));
  }


//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************
  @ExceptionHandler(SpringMockMvcException.class)
  public ResponseEntity<String> onSpringMockMvcException(HttpServletRequest request, SpringMockMvcException ex) {
    return ResponseEntity.status(ex.getHttpStatus()).body(String.format("%s - %s",
        ex.getHttpStatus().value(), ex.getMessage()));
  }

}

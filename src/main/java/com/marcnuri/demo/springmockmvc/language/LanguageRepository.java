/*
 * SpringMockLanguagesRepository.java
 *
 * Created on 2018-05-20, 7:18
 */
package com.marcnuri.demo.springmockmvc.language;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-20.
 */
@Repository
public class LanguageRepository {

  protected static final List<String> LANGUAGES = Arrays.asList("C", "C++", "Java", "JavaScript", "C#",
      "Perl", "Python", "Ruby");

  public List<String> findAll() {
    return  LANGUAGES;
  }

}

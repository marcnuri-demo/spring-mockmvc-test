/*
 * SpringMockLanguagesRepository.java
 *
 * Created on 2018-05-20, 7:18
 */
package com.marcnuri.demo.springmockmvc.language;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-20.
 */
@Repository
public class LanguageRepository {

  protected static final List<Language> LANGUAGES = Arrays.asList(
      of("C", "Dennis Ritchie"), of("C++", "Bjarne Stroustrup"),
      of("Java", "James Gosling"), of("JavaScript", "Brendan Eich"),
      of("C#", "Microsoft"), of("Perl", "Larry Wall"),
      of("Python", "Guido van Rossum"), of("Ruby", "Yukihiro Matsumoto"));

  public List<Language> findAll() {
    return  LANGUAGES;
  }

  public Optional<Language> findByName(String name) {
    return LANGUAGES.stream().filter(l -> l.getName().equals(name)).findFirst();
  }

  private static final Language of(String name, String designer) {
    return new Language(name, designer);
  }
}

/*
 * SpringMockMvcService.java
 *
 * Created on 2018-05-18, 6:47
 */
package com.marcnuri.demo.springmockmvc.language;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-18.
 */
@Service
public class LanguageService {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
  private final LanguageRepository languageRepository;

//**************************************************************************************************
//  Constructors
//**************************************************************************************************
  @Autowired
  public LanguageService(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

//**************************************************************************************************
//  Abstract Methods
//**************************************************************************************************

//**************************************************************************************************
//  Overridden Methods
//**************************************************************************************************

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************

//**************************************************************************************************
//  Getter/Setter Methods
//**************************************************************************************************
  public List<Language> getLanguages(String contains) {
    final List<Language> ret;
    final List<Language> allLanguages = languageRepository.findAll();
    if (contains != null && !contains.isEmpty()) {
      ret = allLanguages.stream().filter(l -> l.getName().contains(contains)).collect(Collectors.toList());
    } else {
      ret = allLanguages;
    }
    return ret;
  }

  public Optional<Language> getLanguage(String name) {
    return languageRepository.findByName(name);
  }

//**************************************************************************************************
//  Static Methods
//**************************************************************************************************

//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************

}

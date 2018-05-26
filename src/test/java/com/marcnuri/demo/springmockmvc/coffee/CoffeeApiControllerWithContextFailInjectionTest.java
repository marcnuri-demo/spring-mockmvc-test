/*
 * CoffeeApiControllerWithContextFailInjectionTest.java
 *
 * Created on 2018-05-26, 7:35
 */
package com.marcnuri.demo.springmockmvc.coffee;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.marcnuri.demo.springmockmvc.coffee.CoffeeApiControllerWithContextFailInjectionTest.CoffeeApiControllerWithContextFailInjectionTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Integration test to validate that Beans with mocked dependencies that contain {@link Autowired}
 * injected <b>fields</b> will throw an {@link UnsatisfiedDependencyException} even though the
 * containing class is mocked.
 *
 * Created by Marc Nuri <marc@marcnuri.com> on 2018-05-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(
    listeners = CoffeeApiControllerWithContextFailInjectionTest.class,
    inheritListeners = false
)
@WebAppConfiguration
@ContextConfiguration(classes = {
    CoffeeApiController.class
})
@Import(CoffeeApiControllerWithContextFailInjectionTestConfiguration.class)
public class CoffeeApiControllerWithContextFailInjectionTest extends DependencyInjectionTestExecutionListener {

  @Autowired
  private CoffeeService coffeeService;

  @Autowired
  private CoffeeApiController coffeeApiController;

  /**
   * Prevent Test preparation from failing when Spring injects autowired beans.
   *
   * @param testContext
   * @throws Exception
   */
  @Override
  protected void injectDependencies(TestContext testContext) throws Exception {
    Exception unsatisfiedDependencyException = null;
    try {
      super.injectDependencies(testContext);
    } catch(IllegalStateException ex){
      if (ex != null && ex.getCause() instanceof UnsatisfiedDependencyException)
        unsatisfiedDependencyException = ex;
    }
    assertThat(unsatisfiedDependencyException, is(notNullValue()));
  }

  @Test
  public void injection_withThirdPartyAutowiredField_fails() {
    assertThat(coffeeService, is(nullValue()));
    assertThat(coffeeApiController, is(nullValue()));
  }

  @Configuration
  protected static class CoffeeApiControllerWithContextFailInjectionTestConfiguration {

    @Bean
    public CoffeeService coffeeService() {
      return Mockito.mock(CoffeeService.class);
    }

  }
}

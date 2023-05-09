package com.gsdd.earthquake;

import com.sun.faces.config.ConfigureListener;
import javax.faces.webapp.FacesServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EarthquakeApp {

  public static void main(String[] args) {
    SpringApplication.run(EarthquakeApp.class, args);
  }

  @Bean
  ServletRegistrationBean<FacesServlet> facesServletRegistration() {
    ServletRegistrationBean<FacesServlet> registration =
        new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
    registration.setLoadOnStartup(1);
    registration.addUrlMappings("*.gg");
    return registration;
  }

  @Bean
  ServletContextInitializer servletContextInitializer() {
    return servletContext -> {
      servletContext
          .setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
      servletContext.setInitParameter("primefaces.THEME", "sunny");
    };
  }

  @Bean
  ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
    return new ServletListenerRegistrationBean<>(new ConfigureListener());
  }
}

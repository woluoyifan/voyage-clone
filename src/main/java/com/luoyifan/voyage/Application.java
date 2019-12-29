package com.luoyifan.voyage;

import com.luoyifan.voyage.property.VoyageProperty;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author EvanLuo
  */
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({VoyageProperty.class})
@EnableJpaAuditing
@EnableCaching
public class Application {
    public static void main(String[] args) {
        run();
    }

    private static void run(){
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run();
    }

}

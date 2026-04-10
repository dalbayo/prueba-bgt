package com.btg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// Excluimos la configuraciÃƒÂ³n de seguridad automÃƒÂ¡tica aquÃƒÂ­
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = "com.btg")
public class BgtApplication {
    public static void main(String[] args) {
        SpringApplication.run(BgtApplication.class, args);
    }
}

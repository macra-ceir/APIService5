package com.gl.mdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication(scanBasePackages= {"com.gl.mdr"})
@EnableEncryptableProperties
@EnableCaching
public class MdrApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MdrApplication.class, args);
	}

}